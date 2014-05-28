package raytracer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Anton on 21.05.2014.
 */
public class Renderer {
    // number of secondary rays
    private static final int MAX_RAY_RECURSION_LEVEL = 1;
    private static final double THRESHOLD_RAY_INTENSITY = 0.1;

    public static BufferedImage render(RenderContext renderContext) {

        final int width = renderContext.getImageWidth();
        final int height = renderContext.getImageHeight();
        final double imageAspectRatio = width * 1. / height;

        final double focus = renderContext.getCamera().getProjPlaneDist();
        final double angle = Math.tan(Math.PI / 4);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < renderContext.getImageWidth(); i++) {
            for (int j = 0; j < renderContext.getImageHeight(); j++) {
                final double x = (2 * (i + 0.5) / width - 1) * angle * imageAspectRatio;
                final double y = (1 - 2 * (j + 0.5) / height) * angle;

                final Vector3D direction = new Vector3D(1, x, y).mul(focus);
                    if (i == 120 && j == 120) {
                        System.out.println("!!!");
                    }
                final Color col = trace(direction, renderContext);

                bufferedImage.setRGB(i, j, col.getRGB());
            }
        }
        return bufferedImage;
    }

    public static Color trace(Vector3D r, RenderContext renderContext) {
        Camera camera = renderContext.getCamera();
        r = Vector3D.normalize(Vector3D.rotateVectorZ(r, camera.getSinAlX(), camera.getCosAlX()));

        // r = Vector3D.rotateVectorY(r, camera.getSinAlY(), camera.getCosAlY());
        //r = Vector3D.rotateVectorZ(r, camera.getSinAlZ(), camera.getCosAlZ());

        Ray ray = new Ray(renderContext.getCamera().getWorldPosition(), r, 1.0);

        return traceRecursively(ray,
                renderContext,
                0);
    }

    public static Color traceRecursively(Ray r,
                                         RenderContext renderContext,
                                         int recursionLevel) {
        SceneObject nearestObject = null;
        double nearestTime = Double.MAX_VALUE;

        for (SceneObject sceneObject : renderContext.getScene().getObjects()) {
            if (sceneObject.intersect(r)) {
                if (r.getLastIntersectTime() < nearestTime && r.getLastIntersectTime() > 0) {
                    nearestTime = r.getLastIntersectTime();
                    nearestObject = sceneObject;
                }
            }
        }
        if (nearestObject != null) {
            r.setLastIntersectTime(nearestTime);
            return calculateColor(renderContext, nearestObject, r, recursionLevel);
        }
        return renderContext.getBackgroundColor();
    }

    private static Color calculateColor(RenderContext renderContext, SceneObject sceneObject, Ray r, int recursionLevel) {

        Color resultColor = new Color(0, 0, 0);

        Material material = sceneObject.getMaterial();

        Color ambient;
        Color diffuse;
        Color reflected;

        // Ambient
        if (material.getKa() > 0) {
            ambient = mixColors(renderContext.getBackgroundColor(),
                    sceneObject.getColor());

            resultColor = addColors(resultColor,
                    mulColors(ambient, material.getKa()));
        }

        // Diffuse
        if (material.getKd() > 0) {
            diffuse = sceneObject.getColor();

            if (renderContext.getScene().getLightSource3Ds().size() > 0) {
                Color light = getLightingColor(r, sceneObject.getNormal(), renderContext);
                diffuse = mixColors(diffuse, light);
            }
            resultColor = addColors(resultColor,
                    mulColors(diffuse, material.getKd()));
        }

        // Reflect
        if (material.getKr() > 0) {

            if ((r.getIntensity() > THRESHOLD_RAY_INTENSITY)
                    && (recursionLevel < MAX_RAY_RECURSION_LEVEL)) {

                Ray reflectedRay = new Ray();

                reflectedRay.setOrigin(r.getLastIntersectPoint());
                reflectedRay.setDirection(reflectDirection(r.getDirection(), sceneObject.getNormal()));
                reflectedRay.setIntensity(r.getIntensity() * material.getKr());

                reflected = traceRecursively(reflectedRay, renderContext, recursionLevel + 1);

            } else {
                reflected = renderContext.getBackgroundColor();
            }
            resultColor = addColors(resultColor,
                    mulColors(reflected, material.getKr()));
        }

        return resultColor;
    }

    private static Color getLightingColor(Ray r, Vector3D normal, RenderContext renderContext) {
        Color lightColor = new Color(0, 0, 0);
        for (LightSource3D lightSource : renderContext.getScene().getLightSource3Ds()) {
            if (isViewable(lightSource.getOrigin(), r.getLastIntersectPoint(), renderContext)) {
                Vector3D distance = r.getLastIntersectPoint().minus(lightSource.getOrigin());
                lightColor = addColors(lightColor, mulColors(lightSource.getColor(), 1000 / Vector3D.dot(distance, distance)));
            }
        }
        return lightColor;
    }

    private static double cosVectors(Vector3D normal, Vector3D v_ls) {
        return Vector3D.dot(normal, v_ls) / (Vector3D.norm(v_ls) * Vector3D.norm(normal));
    }

    private static boolean isViewable(Vector3D origin, Vector3D target, RenderContext renderContext) {
        Vector3D nearestPoint = null;
        double nearestTime = Double.MAX_VALUE;

        Ray r = new Ray(origin, target.minus(origin), 0);

        for (SceneObject sceneObject : renderContext.getScene().getObjects()) {
            if (sceneObject.intersect(r)) {
                if (r.getLastIntersectTime() < nearestTime && r.getLastIntersectTime() > 0) {
                    nearestTime = r.getLastIntersectTime();
                    nearestPoint = r.getLastIntersectPoint();
                }
            }
        }
        if (target.equals(nearestPoint)) {
            return true;
        }

        return false;
    }

    private static Color mixColors(Color backgroundColor, Color color) {
        return normColor((backgroundColor.getRed() * color.getRed()) >> 8,
                (backgroundColor.getGreen() * color.getGreen()) >> 8,
                (backgroundColor.getBlue() * color.getBlue()) >> 8);
    }

    private static Color addColors(Color resultColor, Color color) {
        return normColor(resultColor.getRed() + color.getRed(),
                resultColor.getGreen() + color.getGreen(),
                resultColor.getBlue() + color.getBlue());
    }

    private static Color mulColors(Color reflected, double kr) {
        return normColor((int) (reflected.getRed() * kr), (int) (reflected.getGreen() * kr), (int) (reflected.getBlue() * kr));
    }

    private static Vector3D reflectDirection(Vector3D incidentRay, Vector3D normal) {
        final double k = 2 * Vector3D.dot(incidentRay, normal) / Vector3D.norm(normal);

        final double x = incidentRay.x - normal.x * k;
        final double y = incidentRay.y - normal.y * k;
        final double z = incidentRay.z - normal.z * k;

        return new Vector3D(x, y, z);

    }

    /**
     * make color component valid
     *
     * @param colorComponent
     * @return valid component
     */
    static int normalize(double colorComponent) {
        if (colorComponent > 255) {
            return 255;
        }
        if (colorComponent < 0) {
            return 0;
        }
        return (int) colorComponent;
    }

    /**
     * make color valid
     *
     * @param r - not necessary valid color component
     * @param g - not necessary valid color component
     * @param b - not necessary valid color component
     * @return valid color
     */
    static Color normColor(double r, double g, double b) {
        return new Color(normalize(r), normalize(g), normalize(b));
    }
}

package raytracer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Anton on 21.05.2014.
 */
public class Renderer {
    private static final int MAX_RAY_RECURSION_LEVEL = 3;
    private static final double THRESHOLD_RAY_INTENSITY = 0.1;

    public static BufferedImage render(RenderContext renderContext) {

        final int w = renderContext.getImageWidth();
        final int h = renderContext.getImageHeight();
        final double dx = w / 2.0;
        final double dy = h / 2.0;
        final double focus = renderContext.getCamera().getProjPlaneDist();

        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < renderContext.getImageWidth(); i++) {
            for (int j = 0; j < renderContext.getImageHeight(); j++) {
                final double x = i - dx;
                final double y = j - dy;
                final Vector3D direction = new Vector3D(x, y, focus);

                final Color col = trace(direction, renderContext);

                bufferedImage.setRGB(i, j, col.getRGB());
            }
        }
        return bufferedImage;
    }

    public static Color trace(Vector3D r, RenderContext renderContext) {
        Camera camera = renderContext.getCamera();
        r = Vector3D.rotateVectorX(r, camera.getSinAlX(), camera.getCosAlX());
        r = Vector3D.rotateVectorX(r, camera.getSinAlY(), camera.getCosAlY());
        r = Vector3D.rotateVectorX(r, camera.getSinAlZ(), camera.getCosAlZ());

        Ray ray = new Ray(renderContext.getCamera().getOrigin(), r, 1.0);

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
                if (r.getLastIntersectTime() < nearestTime) {
                    nearestTime = r.getLastIntersectTime();
                    nearestObject = sceneObject;
                }
            }
        }
        if (nearestObject != null) {
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
        Color specular;


        // Ambient
        if (material.getKa() > 0) {
            ambient = mixColors(renderContext.getBackgroundColor(),
                    sceneObject.getColor());
            resultColor = addColors(resultColor,
                    mulColors(ambient, material.getKa()));
        }

        // Diffuse
//        if(material.getKd() > 0) {
//            diffuse = sceneObject.getColor();
//
//            if(renderContext.getScene().getLightSource3Ds().size() > 0) {
//                Color light = get_lighting_color(point, norm, scene);
//                diffuse = mix_colors(diffuse, light);
//            }
//            resultColor = addColors(resultColor,
//                    mulColors(diffuse_color, material.Kd));
//        }

        // Specular
//        if(material.getKs() > 0) {
//            specular = renderContext.getBackgroundColor();
//
//            if(renderContext.getScene().getLightSource3Ds().size() > 0) {
//                specular = get_specular_color(point, reflectedRay, scene, material.getP());
//            }
//            resultColor = addColors(resultColor,
//                    mulColors(specular_color, material.Ks));
//        }


        // Reflect
        if (material.getKr() > 0) {
            // Avoid deep recursion by tracing rays, which have intensity is greather than threshold
            // and avoid infinite recursion by limiting number of recursive calls
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

    private static Color mixColors(Color backgroundColor, Color color) {
        return new Color(backgroundColor.getRed() * color.getRed() >> 8,
                backgroundColor.getGreen() * color.getGreen() >> 8,
                backgroundColor.getBlue() * color.getBlue() >> 8);
    }

    private static Color addColors(Color resultColor, Color color) {
        return new Color(resultColor.getRed() + color.getRed(),
                resultColor.getGreen() + color.getGreen(),
                resultColor.getBlue() + color.getBlue());
    }

    private static Color mulColors(Color reflected, double kr) {
        return new Color((int) (reflected.getRed() * kr), (int) (reflected.getGreen() * kr), (int) (reflected.getBlue() * kr));
    }

    private static Vector3D reflectDirection(Vector3D incidentRay, Vector3D normal) {
        final double k = 2 * Vector3D.dot(incidentRay, normal) / Vector3D.norm(normal);

        final double x = incidentRay.x - normal.x * k;
        final double y = incidentRay.y - normal.y * k;
        final double z = incidentRay.z - normal.z * k;

        return new Vector3D(x, y, z);

    }
}

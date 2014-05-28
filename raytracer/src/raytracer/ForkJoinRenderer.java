package raytracer;

/**
 * Created by Anton on 29.05.2014.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class ForkJoinRenderer extends RecursiveAction {
    // number of secondary rays
    private static final int MAX_RAY_RECURSION_LEVEL = 1;
    private static final double THRESHOLD_RAY_INTENSITY = 0.1;

    private static final int THRESHOLD = 500;

    private RenderContext renderContext;

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage image;
    private int start;
    private int length;

    public ForkJoinRenderer(RenderContext renderContext, BufferedImage image, int start, int length) {
        this.renderContext = renderContext;
        this.image = image;
        this.start = start;
        this.length = length;
    }

    @Override
    protected void compute() {
        if (length < THRESHOLD) {
            computeDirectly();
            return;
        }

        int split = length / 2;

        invokeAll(new ForkJoinRenderer(renderContext, image, start, split),
                new ForkJoinRenderer(renderContext, image, start + split, length - split));
    }

    private void computeDirectly() {
        final int width = renderContext.getImageWidth();
        final int height = renderContext.getImageHeight();
        final double imageAspectRatio = width * 1. / height;

        final double focus = renderContext.getCamera().getProjPlaneDist();
        final double angle = Math.tan(Math.PI / 4);

        // anneal work space with start & length parameters
        for (int k = start; k < start + length; k++) {

            int i = k / width;
            int j = k % width;

            final double x = (2 * (i + 0.5) / width - 1) * angle * imageAspectRatio;
            final double y = (1 - 2 * (j + 0.5) / height) * angle;

            final Vector3D direction = new Vector3D(1, x, y).mul(focus);

            final Color col = trace(direction, renderContext);

            image.setRGB(i, j, col.getRGB());

        }

    }

    public static BufferedImage render(RenderContext renderContext) {


        BufferedImage bufferedImage = new BufferedImage(renderContext.getImageWidth(),
                renderContext.getImageHeight(), BufferedImage.TYPE_INT_RGB);

        ForkJoinRenderer forkJoinRenderer = new ForkJoinRenderer(renderContext, bufferedImage, 0,
                renderContext.getImageHeight() * renderContext.getImageWidth());

        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(forkJoinRenderer);

        return bufferedImage;
    }

    public static Color trace(Vector3D r, RenderContext renderContext) {
        Camera camera = renderContext.getCamera();
        r = Vector3D.normalize(Vector3D.rotateVectorZ(r, camera.getSinAlX(), camera.getCosAlX()));

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

                resultColor = addColors(resultColor,
                        mulColors(reflected, material.getKr()));
            } else {
                //reflected = renderContext.getBackgroundColor();
            }

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

package raytracer;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public class RenderContext {
    private int imageWidth;
    private int imageHeight;

    private Camera camera;
    private Scene scene;
    private Color backgroundColor;

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Camera getCamera() {
        return camera;
    }

    public Scene getScene() {
        return scene;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public SceneObject intersect(Ray ray, Color color) {
        //float tClosest = ray.tmax;
        SceneObject hitObject = null;
        // TODO scene getObjectList;
//        for (int i = 0; i < objects.size(); i++) {
//            Ray r = new Ray(ray);
//                if (scene.getObjects().get(i).intersect(ray, isectDataCurrent)) {
//                    if (isectDataCurrent.t < tClosest && isectDataCurrent.t > ray.tmin) {
//                        isectData = isectDataCurrent;
//                        hitObject = rc->objects[i];
//                        tClosest = isectDataCurrent.t;
//                    }
//                }
//            }

        return hitObject;
    }
}

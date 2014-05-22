package raytracer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anton on 21.05.2014.
 */
public class Scene {
    private ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
    private ArrayList<LightSource3D> lightSource3Ds = new ArrayList<LightSource3D>();

    public ArrayList<SceneObject> getObjects() {
        return objects;
    }
//private KdTree kdTree;

    public ArrayList<LightSource3D> getLightSource3Ds() {
        return lightSource3Ds;
    }

    public void addSceneObject(SceneObject sceneObject) {
        objects.add(sceneObject);
    }

    public void addLightSource(LightSource3D lightSource3D) {
        lightSource3Ds.add(lightSource3D);
    }

//    public void prepareScene() {
//        kdTree.rebuild(objects);
//    }
}

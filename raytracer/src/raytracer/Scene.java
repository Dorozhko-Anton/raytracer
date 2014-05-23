package raytracer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anton on 21.05.2014.
 */
public class Scene {
    private ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
    private ArrayList<LightSource3D> lightSource3Ds = new ArrayList<LightSource3D>();

    private static double LENGTH_WALL = 25;
    private static int SOURCE_1_COLOR = 255;
    private static int SOURCE_2_COLOR = 65280;

    public Scene()
    {
        generateObjects();
        generateLightSources();
    }

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

    private void generateObjects()
    {
        //wall's squares

        Vector3D normal = new Vector3D(0, LENGTH_WALL, 0);
        Square3D square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 0, LENGTH_WALL), new Vector3D(0, 0, LENGTH_WALL),
                new Vector3D(LENGTH_WALL, 0, 0), new Vector3D(0, 0, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, LENGTH_WALL), new Vector3D(LENGTH_WALL, LENGTH_WALL, LENGTH_WALL),
                new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, LENGTH_WALL), new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, LENGTH_WALL),
                new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, 0), new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, LENGTH_WALL, LENGTH_WALL), new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, LENGTH_WALL),
                new Vector3D(4 * LENGTH_WALL, LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        normal = new Vector3D(0, - LENGTH_WALL, 0);

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 3 * LENGTH_WALL, LENGTH_WALL), new Vector3D(0, 3 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(0, 3 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL), new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL), new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, 3 * LENGTH_WALL, LENGTH_WALL), new Vector3D(3 * LENGTH_WALL, 3 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(4 * LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, 3 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        normal = new Vector3D(LENGTH_WALL, 0, 0);

        square = new Square3D(normal,
                new Vector3D(0, 0, LENGTH_WALL), new Vector3D(0, LENGTH_WALL, LENGTH_WALL),
                new Vector3D(0, 0, 0), new Vector3D(0, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(0, LENGTH_WALL, LENGTH_WALL), new Vector3D(0, 2 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(0, LENGTH_WALL, 0), new Vector3D(0, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(0, LENGTH_WALL * 2, LENGTH_WALL), new Vector3D(0, 3 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(0, LENGTH_WALL * 2, 0), new Vector3D(0, 3 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL), new Vector3D(3 * LENGTH_WALL, 3 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, 3 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        normal = new Vector3D(-LENGTH_WALL, 0, 0);

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 0, LENGTH_WALL), new Vector3D(LENGTH_WALL, LENGTH_WALL, LENGTH_WALL),
                new Vector3D(LENGTH_WALL, 0, 0), new Vector3D(LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL), new Vector3D(LENGTH_WALL, 3 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, 3 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, LENGTH_WALL, LENGTH_WALL), new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(4 * LENGTH_WALL, LENGTH_WALL, 0), new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, LENGTH_WALL), new Vector3D(4 * LENGTH_WALL, 3 * LENGTH_WALL, LENGTH_WALL),
                new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(4 * LENGTH_WALL, 3 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        //floor's squares

        normal = new Vector3D(0, 0, LENGTH_WALL);
        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, 0, 0),
                new Vector3D(0, LENGTH_WALL, 0), new Vector3D(0, 0, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(0, 2 * LENGTH_WALL, 0), new Vector3D(0, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0),
                new Vector3D(0, 3 * LENGTH_WALL, 0), new Vector3D(0, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(4 * LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, 0),
                new Vector3D(3 * LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }


        //roof's squares

        normal = new Vector3D(0, 0, -LENGTH_WALL);
        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, 0, 0),
                new Vector3D(0, LENGTH_WALL, 0), new Vector3D(0, 0, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(0, 2 * LENGTH_WALL, 0), new Vector3D(0, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0),
                new Vector3D(0, 3 * LENGTH_WALL, 0), new Vector3D(0, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(2 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(2 * LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(4 * LENGTH_WALL, LENGTH_WALL, 0),
                new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }

        square = new Square3D(normal,
                new Vector3D(4 * LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(4 * LENGTH_WALL, 2 * LENGTH_WALL, 0),
                new Vector3D(3 * LENGTH_WALL, 3 * LENGTH_WALL, 0), new Vector3D(3 * LENGTH_WALL, 2 * LENGTH_WALL, 0));
        for (SceneObject polygon : square.getPolygons())
        {
            objects.add(polygon);
        }
    }


    private void generateLightSources()
    {
        LightSource3D lightSource = new LightSource3D(new Vector3D(LENGTH_WALL / 2, LENGTH_WALL / 2, LENGTH_WALL), new Color(SOURCE_1_COLOR));
        addLightSource(lightSource);
        lightSource = new LightSource3D(new Vector3D(3.5 * LENGTH_WALL, 2.5 * LENGTH_WALL, LENGTH_WALL), new Color(SOURCE_2_COLOR));
        addLightSource(lightSource);
    }
}

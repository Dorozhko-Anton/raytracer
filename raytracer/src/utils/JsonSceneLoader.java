package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import raytracer.*;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
* Created by shellariandra on 27.05.14.
*/
public class JsonSceneLoader
{
    private static String filename = "raytracer/res/SceneDescription";

    public static void getSceneDescription(Scene scene) throws IOException, ParseException
    {
        ArrayList<SceneObject> sceneObjects = new ArrayList<SceneObject>();
        ArrayList<LightSource3D> sceneLightSources = new ArrayList<LightSource3D>();

        FileReader reader = new FileReader(filename);
        JSONParser parser = new JSONParser();

        JSONObject description = (JSONObject) parser.parse(reader);

        Integer numberOfObjects = Integer.parseInt(description.get("numberOfObjects").toString());
        Integer numberOfLightSourses = Integer.parseInt(description.get("numberOfLightSourses").toString());

        JSONArray objects = (JSONArray) description.get("sceneObjects");
        JSONArray lightSourses = (JSONArray) description.get("lightSourses");

        for (int i = 0; i < numberOfObjects; ++i)
        {
            JSONObject sceneObject = (JSONObject) objects.get(i);
            ArrayList<SceneObject> lst = parseObject(sceneObject.get("type").toString(), (JSONArray) sceneObject.get("vectors"), sceneObject.get("color").toString(), (JSONObject) sceneObject.get("material"));
            for (SceneObject object : lst)
            {
                sceneObjects.add(object);
            }
        }

        for (int i = 0; i < numberOfLightSourses; ++i)
        {
            JSONObject light = (JSONObject) lightSourses.get(i);
            Vector3D position = new Vector3D(Double.parseDouble(light.get("x").toString()),
                    Double.parseDouble(light.get("y").toString()),
                    Double.parseDouble(light.get("z").toString()));
            sceneLightSources.add(new LightSource3D(position, new Color(Integer.parseInt(light.get("color").toString(), 16))));
        }


        scene.setSceneObjects(sceneObjects);
        scene.setLightSources(sceneLightSources);
    }

    private static ArrayList<SceneObject> parseObject(String type, JSONArray array, String color, JSONObject material)
    {
        Material material1 = parseMaterial(material);
        Color color1 = new Color(Integer.parseInt(color, 16));

        ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
        if (type.equals("Square3D"))
        {
            JSONObject vector = (JSONObject) array.get(0);
            Vector3D point1 = new Vector3D(Double.parseDouble(vector.get("x").toString()),
                    Double.parseDouble(vector.get("y").toString()),
                    Double.parseDouble(vector.get("z").toString()));

            vector = (JSONObject) array.get(1);
            Vector3D point2 = new Vector3D(Double.parseDouble(vector.get("x").toString()),
                    Double.parseDouble(vector.get("y").toString()),
                    Double.parseDouble(vector.get("z").toString()));

            vector = (JSONObject) array.get(2);
            Vector3D point3 = new Vector3D(Double.parseDouble(vector.get("x").toString()),
                    Double.parseDouble(vector.get("y").toString()),
                    Double.parseDouble(vector.get("z").toString()));

            vector = (JSONObject) array.get(3);
            Vector3D point4 = new Vector3D(Double.parseDouble(vector.get("x").toString()),
                    Double.parseDouble(vector.get("y").toString()),
                    Double.parseDouble(vector.get("z").toString()));

            vector = (JSONObject) array.get(4);
            Vector3D normal = new Vector3D(Double.parseDouble(vector.get("x").toString()),
                    Double.parseDouble(vector.get("y").toString()),
                    Double.parseDouble(vector.get("z").toString()));

            Square3D square = new Square3D(normal, point1, point2, point3, point4, color1, material1);

            for (SceneObject ob : square.getPolygons())
            {
                objects.add(ob);
            }
        }

        return objects;
    }

    private static Material parseMaterial(JSONObject material)
    {
        return new Material(Double.parseDouble(material.get("ambient").toString()),
                Double.parseDouble(material.get("diffuse").toString()),
                Double.parseDouble(material.get("reflection").toString()));
    }
}

package raytracer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anton on 21.05.2014.
 */
public interface SceneObject
{
    boolean intersect(Ray r);

    Material getMaterial();

    Vector3D getNormal();

    Color getColor();

    ArrayList<SceneObject> getPolygons();

}

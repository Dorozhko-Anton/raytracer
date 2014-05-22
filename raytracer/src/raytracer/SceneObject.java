package raytracer;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public interface SceneObject {
    boolean intersect(Ray r);

    Material getMaterial();

    Vector3D getNormal();

    Color getColor();

}

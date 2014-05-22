package raytracer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by shellariandra on 22.05.14.
 */
public class Square3D implements SceneObject
{
    private Color color;
    private Material material;
    private Vector3D normal;
    private ArrayList<SceneObject> polygons;

    public Square3D(Vector3D normal, Vector3D rightUpperAngle, Vector3D leftUpperAngle, Vector3D rightUnderAngle, Vector3D leftUnderAngle)
    {
        this.normal = normal;
        polygons = new ArrayList<SceneObject>();
        createPolygons(normal, rightUpperAngle, leftUpperAngle, rightUnderAngle, leftUnderAngle);
    }

    public ArrayList<SceneObject> getPolygons()
    {
        return polygons;
    }

    @Override
    public boolean intersect(Ray r)
    {
        return false;
    }

    @Override
    public Material getMaterial()
    {
        return material;
    }

    @Override
    public Vector3D getNormal()
    {
        return normal;
    }

    @Override
    public Color getColor()
    {
        return color;
    }

    private void createPolygons(Vector3D normal, Vector3D rightUpperAngle, Vector3D leftUpperAngle, Vector3D rightUnderAngle, Vector3D leftUnderAngle)
    {
       polygons.add(new Triangle3D(rightUpperAngle, rightUnderAngle, leftUnderAngle));
       polygons.add(new Triangle3D(rightUpperAngle, leftUnderAngle, leftUpperAngle));
    }
}

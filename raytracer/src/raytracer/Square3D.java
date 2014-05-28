package raytracer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by shellariandra on 22.05.14.
 */
public class Square3D implements SceneObject {
    private Color color = Color.white;
    private Material material = new Material(0.2, 0.5, 0.3);
    private Vector3D normal;
    private ArrayList<SceneObject> polygons;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double minZ;
    private double maxZ;

    public Square3D(Vector3D normal, Vector3D rightUpperAngle, Vector3D leftUpperAngle, Vector3D rightUnderAngle, Vector3D leftUnderAngle)
    {
        this.normal = normal;
        polygons = new ArrayList<SceneObject>();
        createPolygons(normal, rightUpperAngle, leftUpperAngle, rightUnderAngle, leftUnderAngle);

        double minUpperX = (rightUpperAngle.getX() < leftUpperAngle.getX()) ? rightUpperAngle.getX() : leftUpperAngle.getX();
        double minUnderX = (rightUnderAngle.getX() < leftUnderAngle.getX()) ? rightUnderAngle.getX() : leftUnderAngle.getX();
        minX = (minUnderX < minUpperX) ? minUnderX : minUpperX;

        double maxUpperX = (rightUpperAngle.getX() > leftUpperAngle.getX()) ? rightUpperAngle.getX() : leftUpperAngle.getX();
        double maxUnderX = (rightUnderAngle.getX() > leftUnderAngle.getX()) ? rightUnderAngle.getX() : leftUnderAngle.getX();
        maxX = (maxUpperX > maxUnderX) ? maxUpperX : maxUnderX;

        double minUpperY = (rightUpperAngle.getY() < leftUpperAngle.getY()) ? rightUpperAngle.getY() : leftUpperAngle.getY();
        double minUnderY = (rightUnderAngle.getY() < leftUnderAngle.getY()) ? rightUnderAngle.getY() : leftUnderAngle.getY();
        minY = (minUnderY < minUpperY) ? minUnderY : minUpperY;

        double maxUpperY = (rightUpperAngle.getY() > leftUpperAngle.getY()) ? rightUpperAngle.getY() : leftUpperAngle.getY();
        double maxUnderY = (rightUnderAngle.getY() > leftUnderAngle.getY()) ? rightUnderAngle.getY() : leftUnderAngle.getY();
        maxY = (maxUpperY > maxUnderY) ? maxUpperY : maxUnderY;

        double minUpperZ = (rightUpperAngle.getZ() < leftUpperAngle.getZ()) ? rightUpperAngle.getZ() : leftUpperAngle.getZ();
        double minUnderZ = (rightUnderAngle.getZ() < leftUnderAngle.getZ()) ? rightUnderAngle.getZ() : leftUnderAngle.getZ();
        minZ = (minUnderZ < minUpperZ) ? minUnderZ : minUpperZ;

        double maxUpperZ = (rightUpperAngle.getZ() > leftUpperAngle.getZ()) ? rightUpperAngle.getZ() : leftUpperAngle.getZ();
        double maxUnderZ = (rightUnderAngle.getZ() > leftUnderAngle.getZ()) ? rightUnderAngle.getZ() : leftUnderAngle.getZ();
        maxZ = (maxUpperZ > maxUnderZ) ? maxUpperZ : maxUnderZ;
    }

    public ArrayList<SceneObject> getPolygons()
    {
        return polygons;
    }

    @Override
    public double getMinX()
    {
        return minX;
    }

    @Override
    public double getMaxX()
    {
        return maxX;
    }

    @Override
    public double getMinY()
    {
        return minY;
    }

    @Override
    public double getMaxY()
    {
        return maxY;
    }

    @Override
    public double getMinZ()
    {
        return minZ;
    }

    @Override
    public double getMaxZ()
    {
        return maxZ;
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

    private void createPolygons(Vector3D normal, Vector3D rightUpperAngle, Vector3D leftUpperAngle, Vector3D rightUnderAngle, Vector3D leftUnderAngle) {
        polygons.add(new Triangle3D(rightUpperAngle, rightUnderAngle, leftUnderAngle, color, material, normal));
        polygons.add(new Triangle3D(rightUpperAngle, leftUnderAngle, leftUpperAngle, color, material, normal));
    }
}

package raytracer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anton on 21.05.2014.
 */
public class Triangle3D implements SceneObject {
    private static final double EPSILON = 10e-4;
    private Vector3D v0;
    private Vector3D v1;
    private Vector3D v2;
    private Vector3D normal;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double minZ;
    private double maxZ;

    private Color color;
    private Material material;

    public Triangle3D(Vector3D v0, Vector3D v1, Vector3D v2, Vector3D normal) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.normal = Vector3D.normalize(normal);

        rearrangeVecticies();
        createBounds();
    }

    public Triangle3D(Vector3D v0, Vector3D v1, Vector3D v2, Color color, Material material, Vector3D normal) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.color = color;
        this.material = material;
        this.normal = Vector3D.normalize(normal);

        rearrangeVecticies();
        createBounds();

        //normal = Vector3D.cross(v1.minus(v0), v2.minus(v0));
//        d = -(v0.x * normal.x + v0.y * normal.y + v0.z * normal.z);
    }

    private void rearrangeVecticies() {

        if (normal.equals(Vector3D.normalize(Vector3D.cross(v2.minus(v0), v1.minus(v0))))) {
            Vector3D tmp = v1;
            v1 = v2;
            v2 = tmp;
            // v0 = v0;
            return;
        }

        if (normal.equals(Vector3D.normalize(Vector3D.cross(v0.minus(v1), v2.minus(v1))))) {
            Vector3D tmp = v1;
            v1 = v0;
            //v2 = v2;
            v0 = tmp;
            return;
        }
        if (normal.equals(Vector3D.normalize(Vector3D.cross(v2.minus(v1), v0.minus(v1))))) {
            Vector3D tmp = v1;
            v1 = v2;
            v2 = v0;
            v0 = tmp;
            return;
        }
        if (normal.equals(Vector3D.normalize(Vector3D.cross(v0.minus(v2), v1.minus(v2))))) {
            Vector3D tmp = v2;
            v1 = v0;
            v2 = v1;
            v0 = tmp;
            return;
        }

        if (normal.equals(Vector3D.normalize(Vector3D.cross(v1.minus(v2), v0.minus(v2))))) {
            Vector3D tmp = v2;
            //v1 = v1;
            v2 = v0;
            v0 = tmp;
            return;
        }
    }


    @Override
    public boolean intersect(Ray r) {
        //Möller-Trumbore algorithm
        // appered as bad variant, cause our points not arrange to give needed normal
        Vector3D edge1 = v1.minus(v0);
        Vector3D edge2 = v2.minus(v0);
        Vector3D pvec = Vector3D.cross(r.getDirection(), edge2);
        double det = Vector3D.dot(edge1, pvec);
        if (det > -EPSILON && det < EPSILON) return false;
        //if (det < 0 ) return false;
        double invDet = 1 / det;
        Vector3D tvec = r.getOrigin().minus(v0);

        double u = Vector3D.dot(tvec, pvec) * invDet;
        if (u < 0 || u > 1) return false;
        Vector3D qvec = Vector3D.cross(tvec, edge1);
        double v = Vector3D.dot(r.getDirection(), qvec) * invDet;
        if (v < 0 || u + v > 1) return false;
        double t = Vector3D.dot(edge2, qvec) * invDet;


        r.setU(u);
        r.setV(v);
        r.setLastIntersectTime(t);

        return true;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Vector3D getNormal() {
        //return Vector3D.cross(v1.minus(v0), v2.minus(v0));

        if (normal != null) {
            return normal;
        }
        return Vector3D.cross(v1.minus(v0), v2.minus(v0));
    }

    public void setnormalormal(Vector3D normal) {
        this.normal = normal;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public ArrayList<SceneObject> getPolygons()
    {
        ArrayList<SceneObject> polygons = new ArrayList<SceneObject>();
        polygons.add(this);
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

    private void createBounds()
    {
        minX = (v0.getX() < v1.getX()) ? v0.getX() : v1.getX();
        minX = (minX < v2.getX()) ? minX : v2.getX();

        maxX = (v0.getX() > v1.getX()) ? v0.getX() : v1.getX();
        maxX = (maxX > v2.getX()) ? maxX : v2.getX();

        minY = (v0.getY() < v1.getY()) ? v0.getY() : v1.getY();
        minY = (minY < v2.getY()) ? minY : v2.getY();

        maxY = (v0.getY() > v1.getY()) ? v0.getY() : v1.getY();
        maxY = (maxY > v2.getY()) ? maxY : v2.getY();

        minZ = (v0.getZ() < v1.getZ()) ? v0.getZ() : v1.getZ();
        minZ = (minZ < v2.getZ()) ? minZ : v2.getZ();

        maxZ = (v0.getZ() > v1.getZ()) ? v0.getZ() : v1.getZ();
        maxZ = (maxZ > v2.getZ()) ? maxZ : v2.getZ();
    }
}

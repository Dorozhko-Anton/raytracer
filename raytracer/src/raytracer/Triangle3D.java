package raytracer;

import com.sun.javafx.geom.Vec3f;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public class Triangle3D implements SceneObject {
    private Vector3D v0;
    private Vector3D v1;
    private Vector3D v2;

    public Triangle3D(Vector3D v0, Vector3D v1, Vector3D v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public boolean intersect(Ray r) {
        Vector3D edge1 = v1.minus(v0);
        Vector3D edge2 = v2.minus(v0);
        Vector3D pvec = Vector3D.cross(r.getDirection(), edge2);
        double det = Vector3D.dot(edge1, pvec);
        if (det == 0) return false;
        double invDet = 1 / det;
        Vector3D tvec = r.getOrigin().minus(v0);

        // TODO: intersect data put in ray

        double u = Vector3D.dot(tvec, pvec) * invDet;
        if (u < 0 || u > 1) return false;
        Vector3D qvec = Vector3D.cross(tvec, edge1);
        double v = Vector3D.dot(r.getDirection(), qvec) * invDet;
        if (v < 0 || u + v > 1) return false;
        double t = Vector3D.dot(edge2, qvec) * invDet;

        return true;
    }
}

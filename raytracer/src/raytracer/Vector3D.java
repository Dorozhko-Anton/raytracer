package raytracer;

/**
 * Created by Anton on 21.05.2014.
 */
public class Vector3D {

    {
        x = 0;
        y = 0;
        z = 0;
    }

    private static final double EPSILON = 10e-4;

    double x;
    double y;

    ;
    double z;

    public Vector3D() {

    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public static Vector3D cross(Vector3D v1, Vector3D v2) {
        return new Vector3D(
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x
        );
    }

    public static double norm(Vector3D v) {
        return Vector3D.dot(v, v);
    }

    public static double dot(Vector3D v1, Vector3D v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    static Vector3D
    rotateVectorX(final Vector3D p,
                  final double sin_al,
                  final double cos_al) {

        final double y = p.y * cos_al - p.z * sin_al;
        final double z = p.y * sin_al + p.z * cos_al;

        return new Vector3D(p.x, y, z);
    }

    static Vector3D
    rotateVectorY(final Vector3D p,
                  final double sin_al,
                  final double cos_al) {

        final double x = p.x * cos_al - p.z * sin_al;
        final double z = p.x * sin_al + p.z * cos_al;

        return new Vector3D(x, p.y, z);
    }

    static Vector3D
    rotateVectorZ(final Vector3D p,
                  final double sin_al,
                  final double cos_al) {

        final double x = p.x * cos_al - p.y * sin_al;
        final double y = p.x * sin_al + p.y * cos_al;

        return new Vector3D(x, y, p.z);
    }

    public Vector3D minus(Vector3D v0) {
        return new Vector3D(
                x - v0.x,
                y - v0.y,
                z - v0.z
        );
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public Vector3D mul(double t) {
        return new Vector3D(x * t, y * t, z * t);
    }

    public Vector3D plus(Vector3D vector3D) {
        return new Vector3D(x + vector3D.x, y + vector3D.y, z + vector3D.z);
    }


    @Override
    public String toString() {
        return (x + ";" + y + ";" + z);
    }

    public boolean equals(Vector3D v) {
        if (v == null) {
            return false;
        }
        return ((x <= v.x + EPSILON) && (x >= v.x - EPSILON)) &&
                ((y <= v.y + EPSILON) && (y >= v.y - EPSILON)) &&
                ((z <= v.z + EPSILON) && (z >= v.z - EPSILON));
    }
}

package raytracer;

/**
 * Created by Anton on 21.05.2014.
 */
public class Vector3D {

    public static Vector3D cross(Vector3D v1, Vector3D v2) {
        return new Vector3D(new double[]{
                v1.v[1] * v2.v[2] - v1.v[2] * v2.v[1],
                v1.v[2] * v2.v[0] - v1.v[0] * v2.v[2],
                v1.v[0] * v2.v[1] - v1.v[1] * v2.v[0]
        });
    };

    private double v[] = new double[3];

    public double[] getV() {
        return v;
    }

    public Vector3D(double[] v) {
        this.v = v;
    }

    public Vector3D minus(Vector3D v0) {
        return new Vector3D(new double[]{
                v[0] - v0.v[0],
                v[1] - v0.v[1],
                v[2] - v0.v[2]
        });
    }


    public static double dot(Vector3D v1, Vector3D v2) {
        return v1.v[0]*v2.v[0] + v1.v[1]*v2.v[1] + v1.v[2]*v2.v[2];
    }
}

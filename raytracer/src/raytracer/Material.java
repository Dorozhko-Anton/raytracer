package raytracer;

/**
 * Created by Anton on 21.05.2014.
 */
public class Material {
    // Required:
    // Ka + Kd + Kr = 1.0

    // Ambient
    private double Ka;
    // Diffuse
    private double Kd;
    // Reflection
    private double Kr;


    public Material(double ka, double kd, double kr) {
        Ka = ka;
        Kd = kd;
        Kr = kr;
    }

    public double getKa() {
        return Ka;
    }

    public double getKd() {
        return Kd;
    }

    public double getKr() {
        return Kr;
    }

}

package raytracer;

/**
 * Created by Anton on 21.05.2014.
 */
public class Material {
    // Required:
    // Ka + Kd + Ks + Kr + Kt = 1.0

    // Ambient
    private double Ka;
    // Diffuse
    private double Kd;
    // Specular
    private double Ks;
    // Reflection
    private double Kr;
    // Transparency
    private double Kt;

    // Ks * light_source_color * ((cos(..))^p)
    private double p;

    public Material(double ka, double kd, double ks, double kr, double kt, double p) {
        Ka = ka;
        Kd = kd;
        Ks = ks;
        Kr = kr;
        Kt = kt;
        this.p = p;
    }

    public double getKa() {
        return Ka;
    }

    public double getKd() {
        return Kd;
    }

    public double getKs() {
        return Ks;
    }

    public double getKr() {
        return Kr;
    }

    public double getKt() {
        return Kt;
    }

    public double getP() {
        return p;
    }
}

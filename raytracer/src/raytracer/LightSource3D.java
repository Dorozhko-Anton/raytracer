package raytracer;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public class LightSource3D {
    private Vector3D origin;
    private Color color;

    public LightSource3D(Vector3D origin, Color color) {
        this.origin = origin;
        this.color = color;
    }


    public Vector3D getOrigin() {
        return origin;
    }

    public Color getColor() {
        return color;
    }
}

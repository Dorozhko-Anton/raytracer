package raytracer;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    private Color rayColor;
    private double lastIntersectTime;
    private Vector3D lastIntersectPoint;

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Color getRayColor() {
        return rayColor;
    }

    public double getLastIntersectTime() {
        return lastIntersectTime;
    }

    public Vector3D getLastIntersectPoint() {
        return lastIntersectPoint;
    }
}

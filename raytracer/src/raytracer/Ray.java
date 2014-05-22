package raytracer;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public class Ray {
    public Ray(Vector3D origin, Vector3D direction, double intensity) {
        this.origin = origin;
        this.direction = direction;
        this.intensity = intensity;
    }

    private Vector3D origin;
    private Vector3D direction;

    private double intensity;
    private Color rayColor;


    // intersect data
    private double lastIntersectTime;
    private Vector3D lastIntersectPoint;
    private double u;
    private double v;

    public Ray(Ray ray) {
        origin = ray.origin;
        direction = ray.direction;
        rayColor = ray.rayColor;
        lastIntersectPoint = ray.lastIntersectPoint;
        lastIntersectTime = ray.lastIntersectTime;
    }

    public Ray() {

    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    public Color getRayColor() {
        return rayColor;
    }

    public void setRayColor(Color rayColor) {
        this.rayColor = rayColor;
    }

    public double getLastIntersectTime() {

        return lastIntersectTime;
    }

    public void setLastIntersectTime(double lastIntersectTime) {
        this.lastIntersectTime = lastIntersectTime;
    }

    public Vector3D getLastIntersectPoint() {

        return origin.plus(direction.mul(lastIntersectTime));
    }

    public void setLastIntersectPoint(Vector3D lastIntersectPoint) {
        this.lastIntersectPoint = lastIntersectPoint;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}

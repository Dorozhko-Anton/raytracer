package raytracer;

/**
 * Created by Anton on 21.05.2014.
 */
public class Camera {
    private static double EPSILON = 10e-6;
    private Vector3D origin;
    private Vector3D worldPosition;
    private double alX;
    private double sinAlX;
    private double cosAlX;
    private double alY;
    private double sinAlY;
    private double cosAlY;
    private double alZ;
    private double sinAlZ;
    private double cosAlZ;
    private double projPlaneDist;

    public Camera(Vector3D origin, double alX, double projPlaneDist) {
        this.origin = origin;
        this.worldPosition = new Vector3D(origin);
        this.alX = alX;
        sinAlX = Math.sin(alX);
        cosAlX = Math.cos(alX);

        this.alY = alY;
        sinAlY = Math.sin(alY);
        cosAlY = Math.cos(alY);

        this.alZ = alZ;
        this.alY = alY;
        sinAlZ = Math.sin(alZ);
        cosAlZ = Math.cos(alZ);

        this.projPlaneDist = projPlaneDist;
    }

    public void rotateCamera(double rad_dx, double rad_dy, double rad_dz) {
        if (Math.abs(rad_dx) > EPSILON) {
            alX += rad_dx;
            sinAlX = Math.sin(alX);
            cosAlX = Math.cos(alX);
        }

        if (Math.abs(rad_dy) > EPSILON) {
            alY += rad_dy;
            sinAlY = Math.sin(alY);
            cosAlY = Math.cos(alY);
        }

        if (Math.abs(rad_dz) > EPSILON) {
            alZ += rad_dz;
            sinAlZ = Math.sin(alZ);
            cosAlZ = Math.cos(alZ);
        }
    }

    public void moveCamera(Vector3D vector) {
        Vector3D direction = Vector3D.rotateVectorZ(vector, sinAlX, cosAlX);
//        direction = Vector3D.rotateVectorY(direction, sinAlY, cosAlY);
//        direction = Vector3D.rotateVectorZ(direction, sinAlZ, cosAlZ);

        worldPosition = worldPosition.plus(direction);
    }

    public double getProjPlaneDist() {
        return projPlaneDist;
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public double getAlX() {
        return alX;
    }

    public void setAlX(double alX) {
        this.alX = alX;
    }

    public double getSinAlX() {
        return sinAlX;
    }

    public double getCosAlX() {
        return cosAlX;
    }

    public double getAlY() {
        return alY;
    }

    public void setAlY(double alY) {
        this.alY = alY;
    }

    public double getSinAlY() {
        return sinAlY;
    }

    public double getCosAlY() {
        return cosAlY;
    }

    public double getAlZ() {
        return alZ;
    }

    public void setAlZ(double alZ) {
        this.alZ = alZ;
    }

    public double getSinAlZ() {
        return sinAlZ;
    }

    public double getCosAlZ() {
        return cosAlZ;
    }

    public Vector3D getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(Vector3D worldPosition) {
        this.worldPosition = worldPosition;
    }
}

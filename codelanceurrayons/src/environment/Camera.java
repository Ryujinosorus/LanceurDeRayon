package environment;

import math.Point3D;
import math.Vector3D;

public class Camera {
    private Point3D position;
    private Vector3D lookAt;
    private Vector3D up;
    private double fov;

    public Point3D getPosition() {
        return position;
    }

    public Vector3D getLookAt() {
        return lookAt;
    }

    public Vector3D getUpperEye() {
        return up;
    }

    public double getFov() {
        return fov;
    }

    public void setPosition(Point3D p) {
        this.position = p;
    }

    void setLookAt(Vector3D v) {
        lookAt = v;
    }

    void setUp(Vector3D v) {
        up = v;
    }

    void setFov(double d) {
        this.fov = d;
    }
}

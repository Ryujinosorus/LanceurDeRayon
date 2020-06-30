package math;

public class Ray {
    private final Point3D origin;
    private final Vector3D direction;

    public Ray(Point3D p, Vector3D v) {
        this.origin = p;
        this.direction = v;
    }

    public Point3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction.normalize();
    }
}

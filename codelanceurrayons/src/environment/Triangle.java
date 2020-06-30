package environment;

import math.Color;
import math.Point3D;
import math.Ray;
import math.Vector3D;

public class Triangle extends BasicObject {
    private final Point3D a;
    private final Point3D b;
    private final Point3D c;

    Triangle(Point3D a, Point3D b, Point3D c, Color color, Color specular, int shininess) {
        this.diffuse = color;
        this.a = a;
        this.b = b;
        this.c = c;
        this.specular = specular;
        this.shininess = shininess;
    }

    @Override
    public Double hit(Ray ray) {
        Vector3D d = ray.getDirection();
        Point3D o = ray.getOrigin();

        Vector3D n = this.getNormal();

        double factor = d.dot(n);
        if (Double.compare(factor, 0) == 0) {
            return null;
        }
        double t = (this.a.sub(o)).dot(n) / factor;

        Point3D p = o.add(d.mul(t));

        if (this.b.sub(this.a).cross(p.sub(this.a)).dot(n) < 0) {
            return null;
        }
        if (this.c.sub(this.b).cross(p.sub(this.b)).dot(n) < 0) {
            return null;
        }
        if (this.a.sub(this.c).cross(p.sub(this.c)).dot(n) < 0) {
            return null;
        }

        if (t <= 0) {
            return null;
        }

        return t;
    }

    @Override
    public Vector3D getNormal(Point3D p) {
        return this.getNormal();
    }

    /**
     * A triangle uses his 3 points to get his normal
     *
     * @return Vector3D the normal
     */
    private Vector3D getNormal() {
        Vector3D ba = this.b.sub(this.a).normalize();
        Vector3D ca = this.c.sub(this.a).normalize();

        return ba.cross(ca).normalize();
    }

}

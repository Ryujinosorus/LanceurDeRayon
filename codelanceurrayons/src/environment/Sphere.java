package environment;

import math.Color;
import math.Point3D;
import math.Ray;
import math.Vector3D;

public class Sphere extends BasicObject {
    private final Point3D center;
    private final double radius;

    Sphere(Point3D ce, double r, Color diffuse, Color specular, int shininess) {
        this.center = ce;
        this.radius = r;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public Double hit(Ray ray) {
        Vector3D d = ray.getDirection();
        Point3D o = ray.getOrigin();

        double a = d.dot(d);
        double b = 2 * o.sub(center).dot(d);
        double c = o.sub(center).dot(o.sub(center)) - radius * radius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0)
            return null;
        else if (discriminant == 0) {
            return -b / (2 * a);
        } else {
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

            if (t2 >= 0) return t2;
            if (t1 >= 0) return t1;
            return null;
        }
    }

    @Override
    public Vector3D getNormal(Point3D p) {
        return p.sub(this.center).normalize();
    }
}

package environment;

import math.Color;
import math.Point3D;
import math.Ray;
import math.Vector3D;

public class Plane extends BasicObject {

    private final Point3D q;
    private final Vector3D n;

    Plane(Point3D q, Vector3D n, Color diffuse, Color specular, int shininess) {
        this.q = q;
        this.n = n;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    @Override
    public Double hit(Ray ray) {
        Vector3D d = ray.getDirection();
        Point3D o = ray.getOrigin();

        double factor = d.dot(this.n);

        if (Double.compare(factor, 0) == 0) {
            return null;
        }

        // formula : (q-o) * n / (d * n)
        double t = this.q.sub(o).dot(this.n) / factor;

        if (t <= 0) {
            return null;
        }

        return t;
    }

    @Override
    public Vector3D getNormal(Point3D p) {
        return this.n.normalize();
    }
}

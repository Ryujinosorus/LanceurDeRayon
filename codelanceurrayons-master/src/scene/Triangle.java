/*******************************************************************************
 *
 *  MODULE PROJET 2 - 2015, 2016 (c) Daniel Le Berre - Université d'Artois - tous droits réservés.
 *   
 *  Le code source de ce programme est distributé pour raison pédagogique
 *  aux étudiants de troisième année de licence d'informatique de l'université d'Artois 2015.
 *  Ces étudiants ont le droit de l'exécuter, l'étudier, le modifier pour leur usage personnel.
 *  La redistribution de ce programme, sous quelle que forme que ce soit (source, binaire,
 *  listing, etc) est strictement interdite. 
 *  
 *******************************************************************************/
package scene;

import java.util.Optional;

import math.Matrix4;
import math.MyColor;
import math.Point;
import math.Vec3;

/**
 * Implementation of a triangle, given three points.
 * 
 * @author leberre
 *
 */
public class Triangle extends SceneObject {

    private final Point a, b, c;
    private final Vec3 n, ba, ca;

    public Triangle(Point a, Point b, Point c, Matrix4 tranformation, Material diffuse, MyColor specular,
            double shininess) {
        super(diffuse, specular, shininess);
        this.a = tranformation.mul(a);
        this.b = tranformation.mul(b);
        this.c = tranformation.mul(c);
        this.ba = this.b.sub(this.a);
        this.ca = this.c.sub(this.a);
        this.n = this.ba.cross(this.ca).hat();
    }

    @Override
    public Vec3 normal(Point p) {
        return n;
    }

    @Override
    public Optional<IntensionPoint> intersect(Point origin, Vec3 ray) {
        Vec3 p, q, tvec;
        double det, invdet, beta, gamma;
        double t;

        // Begin calculating determinant - also used to calculate u parameter
        p = ray.cross(ca);
        // if determinant is near zero, ray lies in plane of triangle
        det = ba.dot(p);
        // NOT CULLING
        if (det > -EPSILON && det < EPSILON) {
            return Optional.empty();
        }
        invdet = 1d / det;

        // calculate distance from V0 to ray origin
        tvec = origin.sub(a);

        // Calculate u parameter and test bound
        beta = tvec.dot(p) * invdet;
        // The intersection lies outside of the triangle
        if (beta < 0d || beta > 1d) {
            return Optional.empty();
        }

        // Prepare to test v parameter
        q = tvec.cross(ba);

        // Calculate V parameter and test bound
        gamma = ray.dot(q) * invdet;
        // The intersection lies outside of the triangle
        if (gamma < 0d || beta + gamma > 1d) {
            return Optional.empty();
        }

        t = ca.dot(q) * invdet;

        if (t > EPSILON) {
            // ray intersection
            return Optional.of(new IntensionPoint(origin, ray, t));
        }

        // No hit, no win
        return Optional.empty();
    }

    @Override
    public Point projectAndNormalizeCoordinate(Point pos) {
        double width = ba.length();
        double height = ca.cross(ba.hat()).length();
        double x = (pos.x % width) / width;
        double z = (pos.z % height) / height;
        if (x < 0) {
            x += 1.0d;
        }
        if (z < 0) {
            z += 1.0d;
        }
        return new Point(x, 0, z);
    }
}

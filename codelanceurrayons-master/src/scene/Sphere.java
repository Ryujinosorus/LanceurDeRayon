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
import math.Vec4;

/**
 * Implementation of a sphere, given its origin and its radius.
 * 
 * @author leberre
 *
 */
public class Sphere extends SceneObject {

    private final Point center;

    private final double radius;

    private final Matrix4 transform;
    private final Matrix4 invtransform;

    public Sphere(Point center, double radius, Matrix4 transform, Material diffuse, MyColor specular,
            double shininess) {
        super(diffuse, specular, shininess);
        this.center = center;
        this.radius = radius;
        this.transform = transform;
        this.invtransform = transform.inv();
    }

    @Override
    public Vec3 normal(Point p) {
        Point pointr = invtransform.mul(p);
        Vec3 normal = pointr.sub(center);
        Vec3 transformed = new Vec4(normal).mul(invtransform).toVec3();
        return transformed.hat();
    }

    @Override
    public Optional<IntensionPoint> intersect(Point origin, Vec3 ray) {
        Vec3 invRay = invtransform.mul(ray).hat();
        Point invOrigin = invtransform.mul(origin);
        Optional<IntensionPoint> result = intersectEasy(invOrigin, invRay);
        if (result.isPresent()) {
            Point point = result.get().toPoint();
            Point newPoint = transform.mul(point);
            double t;
            Vec3 no = newPoint.sub(origin);
            if (Double.compare(ray.x, 0.0) != 0) {
                t = no.x / ray.x;
            } else if (Double.compare(ray.y, 0.0) != 0) {
                t = no.y / ray.y;
            } else {
                t = no.z / ray.z;
            }
            result = Optional.of(new IntensionPoint(origin, ray, t));
        }
        return result;
    }

    private Optional<IntensionPoint> intersectEasy(Point origin, Vec3 ray) {
        // solving a t**2 + b t + c = 0
        // double a = ray.mul(ray); a = 1 (Thanks M. Wallon)
        Vec3 oc = origin.sub(center);
        double b = 2 * ray.dot(oc);
        double c = oc.dot(oc) - radius * radius;
        double delta = b * b - 4 * c;
        if (delta < 0) {
            return Optional.empty();
        }

        double r1 = (-b + Math.sqrt(delta)) / 2;
        double r2 = (-b - Math.sqrt(delta)) / 2;

        // we know that r1>=r2
        if (r2 > EPSILON) {
            return Optional.of(new IntensionPoint(origin, ray, r2));
        }
        if (r1 > EPSILON) {
            return Optional.of(new IntensionPoint(origin, ray, r1));
        }

        return Optional.empty();
    }

    @Override
    public Point projectAndNormalizeCoordinate(Point pos) {
        double phi = Math.atan2(pos.x - center.x, pos.z - center.z);
        if (phi < 0) {
            phi += 2 * Math.PI;
        }
        double theta = Math.acos((pos.y - center.y) / radius);
        return new Point(phi / (2 * Math.PI), 0, theta / (Math.PI));
    }
}

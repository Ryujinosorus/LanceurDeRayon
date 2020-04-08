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

import math.MyColor;
import math.Point;
import math.Vec3;

/**
 * A simple plane implementation, defined by a point and the normal of that
 * plane.
 * 
 * @author leberre
 *
 */
public class Plane extends SceneObject {

    private final Point point;
    private final Vec3 normal;
    private final Scene scene;

    public Plane(Scene scene, Point point, Vec3 normal, Material diffuse, MyColor specular, double shininess) {
        super(diffuse, specular, shininess);
        this.scene = scene;
        this.point = point;
        this.normal = normal.hat();
    }

    @Override
    public Vec3 normal(Point p) {
        return normal;
    }

    @Override
    public Optional<IntensionPoint> intersect(Point origin, Vec3 ray) {
        double cosinus = normal.dot(ray);
        if (Math.abs(cosinus) <= EPSILON) {
            // plane is parallel to ray
            return Optional.empty();
        }
        double t = normal.dot(point.sub(origin)) / cosinus;
        if (t <= EPSILON) {
            return Optional.empty();
        }
        return Optional.of(new IntensionPoint(origin, ray, t));
    }

    @Override
    public Point projectAndNormalizeCoordinate(Point pos) {
        double x = (pos.x % scene.getHalfWidth()) / scene.getHalfWidth();
        double z = (pos.z % scene.getHalfHeight()) / scene.getHalfHeight();
        if (x < 0) {
            x += 1.0d;
        }
        if (z < 0) {
            z += 1.0d;
        }
        return new Point(x, 0, z);
    }
}

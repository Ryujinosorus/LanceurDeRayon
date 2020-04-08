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

import math.Point;
import math.Vec3;

/**
 * A utility class to represent an intersection point as an origin, a direction
 * and a distance, instead of directly as a 3D point.
 * 
 * @author leberre
 *
 */
public class IntensionPoint {

    public final Point origin;
    public final Vec3 direction;
    public final double length;

    /**
     * Create an intersection point.
     * 
     * @param origin
     *            its origin
     * @param direction
     *            its direction
     * @param length
     *            the distance to the origin.
     */
    public IntensionPoint(final Point origin, final Vec3 direction, final double length) {
        this.origin = origin;
        this.direction = direction;
        this.length = length;
    }

    /**
     * Evaluate the intersection point as a 3D coordinate.
     * 
     * @return the 3D coordinate of the intersection point.
     */
    public Point toPoint() {
        return origin.add(direction.mul(length));
    }

    @Override
    public String toString() {
        return "" + origin + " + " + direction + " * " + length;
    }
}

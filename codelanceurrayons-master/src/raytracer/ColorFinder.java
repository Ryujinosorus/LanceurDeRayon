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
package raytracer;

import math.MyColor;
import math.Vec3;
import scene.Intersection;
import scene.Scene;

/**
 * Strategy design pattern to compute the color of a given point.
 * 
 * @author leberre
 *
 */
public interface ColorFinder {

    double EPSILON = 0.00001d;

    Vec3 EPSILON_VEC = new Vec3(EPSILON, EPSILON, EPSILON);

    /**
     * Compute the color of the point at the given intersection.
     * 
     * @param scene
     *            the 3D scene information
     * @param intersection
     *            the intersection with an object
     * @return the color of the intersection point
     */
    default MyColor findColor(Scene scene, Intersection intersection) {
        return findColor(scene, intersection, 0);
    }

    /**
     * Compute the color of the point at the given intersection, for a given
     * depth.
     * 
     * @param scene
     *            the 3D scene information
     * @param intersection
     *            the intersection with an object
     * @param depth
     *            the depth of the recursive computation
     * @return the color of the intersection point
     */
    MyColor findColor(Scene scene, Intersection intersection, int depth);

    /**
     * Change the computation of shadows in the scene.
     * 
     * @param shadowEnabled
     *            must be true to enable shadow computation.
     */
    void setShadowEnabled(boolean shadowEnabled);

    /**
     * Check if the computation of the shadows must be performed in the scene.
     * 
     * @return true iff the shadows must be computed.
     */
    boolean isShadowEnabled();

}
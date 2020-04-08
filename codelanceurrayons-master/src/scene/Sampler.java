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

import java.util.Collection;

import math.Vec3;

/**
 * Strategy design pattern for implementing anti-aliasing.
 * 
 * @author leberre
 *
 */
@FunctionalInterface
public interface Sampler {

    /**
     * Compute a list of rays for a given pixel in the image.
     * 
     * The color of the pixel will be the mean of the colors of those rays.
     * 
     * @param x
     *            the x coordinate of the image
     * @param y
     *            the y coordinate of the image
     * @return a list of rays corresponding to a sampling for that coordinate.
     */
    Collection<Vec3> rayThroughPixel(int x, int y);

    /**
     * Allow the sampler to be configured by an integer parameter.
     * 
     * @param n
     *            an integer
     */
    default void setParameter(int n) {
        // does nothing by default
    }
}
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

import math.MyColor;
import math.Point;

/**
 * A material renders the diffuse color according to the coordinate of the point
 * in the scene.
 * 
 * @author leberre
 *
 */
@FunctionalInterface
public interface Material {

    /**
     * Provides the color associated to a specific point in the
     * 
     * @param sceneObject
     *            an object of the scene.
     * @param pos
     *            a specific point of <code>sceneObject</code>.
     * @return the color of <code>pos</code> in the scene.
     */
    MyColor getColor(SceneObject sceneObject, Point pos);
}

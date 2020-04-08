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
package colorfinders;

import math.MyColor;
import raytracer.ColorFinder;
import scene.Intersection;
import scene.Scene;

/**
 * Simply ask the object for its color. Useful for debugging intersection
 * computation.
 * 
 * @author leberre
 *
 */
public class BasicColorFinder implements ColorFinder {

    @Override
    public MyColor findColor(Scene scene, Intersection intersection, int depth) {
        return scene.getAmbient()
                .add(intersection.object.getDiffuse().getColor(intersection.object, intersection.point.toPoint()));
    }

    @Override
    public void setShadowEnabled(boolean shadowEnabled) {
        // does not make sense in this context
    }

    @Override
    public boolean isShadowEnabled() {
        return false;
    }

}

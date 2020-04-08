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
import math.Point;
import scene.Intersection;
import scene.Light;

/**
 * A Blinn-Phong illumination model.
 * 
 * @author leberre
 *
 */
public class PhongColorFinder extends AbstractMultilightColorFinder {

    @Override
    protected MyColor computeColorForLight(Intersection intersection, Point pos, Light light) {
        return light.lambert(intersection.object, pos).add(light.blinnPhong(intersection, pos));
    }
}

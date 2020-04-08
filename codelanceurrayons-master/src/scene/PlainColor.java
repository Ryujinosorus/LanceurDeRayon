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
 * Plain color material, where all points of the objects have the same diffuse
 * color.
 * 
 * @author leberre
 *
 */
public class PlainColor implements Material {

    /**
     * The unique diffuse color of the object.
     */
    private final MyColor color;

    /**
     * Create a single diffuse color material.
     * 
     * @param color
     *            the diffuse color of the object.
     */
    public PlainColor(MyColor color) {
        this.color = color;
    }

    @Override
    public MyColor getColor(SceneObject sceneObject, Point pos) {
        return color;
    }

}

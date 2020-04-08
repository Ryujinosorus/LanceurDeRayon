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
 * A simple checker implementation.
 * 
 * It only works for planes parallel to the (x,z) plane.
 * 
 * @author leberre
 *
 */
public class Checker implements Material {

    private final MyColor firstColor;
    private final MyColor secondColor;
    private final double squareSize;

    public Checker(MyColor color1, MyColor color2, double squareSize) {
        this.firstColor = color1;
        this.secondColor = color2;
        this.squareSize = squareSize;
    }

    @Override
    public MyColor getColor(SceneObject sceneObject, Point pos) {
        double twice = 2 * squareSize;
        double x = pos.x % twice;
        double z = pos.z % twice;
        if (x < 0) {
            x += twice;
        }
        if (z < 0) {
            z += twice;
        }
        if (x < squareSize) {
            if (z < squareSize) {
                return firstColor;
            } else {
                return secondColor;
            }
        } else {
            if (z < squareSize) {
                return secondColor;
            } else {
                return firstColor;
            }
        }
    }
}

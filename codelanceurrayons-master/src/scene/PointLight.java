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
import math.Vec3;

public class PointLight implements Light {

    private final MyColor color;
    private final Point position;

    private MyColor attenuation = new MyColor(1, 0, 0);

    public PointLight(Point pos, MyColor color) {
        this.position = pos;
        this.color = color;
    }

    @Override
    public MyColor getColor() {
        return color;
    }

    @Override
    public Vec3 getDirection(Point currentPos) {
        return position.sub(currentPos).hat();
    }

    public void setAttenuation(MyColor attenuation) {
        this.attenuation = attenuation;
    }

    @Override
    public MyColor getAttenuation() {
        return attenuation;
    }

    @Override
    public double getDistance(Point pos) {
        return position.sub(pos).length();
    }

}

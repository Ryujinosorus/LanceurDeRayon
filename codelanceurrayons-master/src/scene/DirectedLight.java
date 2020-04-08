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

/**
 * A simple directional light implementation.
 * 
 * @author leberre
 *
 */
public class DirectedLight implements Light {

    /**
     * The color of the light, including its contribution to the scene.
     */
    private final MyColor color;
    /**
     * The direction toward the light, to ease computation.
     */
    private final Vec3 direction;

    private static final MyColor DEFAULT_ATTENUATION = new MyColor(1, 0, 0);

    /**
     * Create a directional light given a direction to that light source and a
     * color (including its contribution).
     * 
     * @param direction
     *            the direction toward the light.
     * @param color
     *            the color of the light, including its contribution to the
     *            scene.
     */
    public DirectedLight(Vec3 direction, MyColor color) {
        this.direction = direction.hat();
        this.color = color;
    }

    @Override
    public MyColor getColor() {
        return color;
    }

    @Override
    public Vec3 getDirection(Point currentPos) {
        return direction;
    }

    @Override
    public MyColor getAttenuation() {
        return DEFAULT_ATTENUATION;
    }

    @Override
    public double getDistance(Point pos) {
        // need a big enough value.
        // too big values may produce an overflow in the raytracer.
        return 100000d;
    }
}

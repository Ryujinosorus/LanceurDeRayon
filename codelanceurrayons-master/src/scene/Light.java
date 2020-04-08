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
 * Represent the various ways to illuminate the objects in the scene.
 * 
 * The interface contains some code in "default methods" introduced in Java 8.
 * It is not clear at this point if it is really a nice feature. Take it as an
 * experiment with default methods rather than a really deep design choice.
 * 
 * @author leberre
 *
 */
public interface Light {

    /**
     * Compute the illumination using the Lambertian method.
     * 
     * @param object
     *            the illuminated object.
     * @param pos
     *            a point in the object.
     * @return the color of <code>pos</code> on <code>object</code> according to
     *         the Lambertian model.
     */
    default MyColor lambert(SceneObject object, Point pos) {
        double costheta = object.normal(pos).dot(getDirection(pos));
        if (costheta < 0.0d) {
            costheta = 0.0d;
        }
        return object.getDiffuse().getColor(object, pos).times(getColor()).mul(costheta);
    }

    /**
     * Compute the illumination using the Blinn-Phong method.
     * 
     * @param intersection
     *            the intersection point.
     * @param pos
     *            a 3D point in the object.
     * @return the color of <code>pos</code> on <code>object</code> according to
     *         the Blinn-Phong model.
     */
    default MyColor blinnPhong(Intersection intersection, Point pos) {
        Vec3 halfvec = getDirection(pos).add(intersection.point.origin.sub(pos).hat()).hat();
        double nDotH = intersection.object.normal(pos).dot(halfvec);
        if (nDotH < 0.0d) {
            nDotH = 0.0d;
        }
        return intersection.object.getSpecular().times(getColor())
                .mul(Math.pow(nDotH, intersection.object.shininess()));
    }

    /**
     * Compute the attenuation of the light. (not used this year).
     * 
     * @param pos
     *            a point in the scene
     * @return the attenuation at the point.
     */
    default double attenuation(Point pos) {
        MyColor latt = getAttenuation();
        double r = getDistance(pos);
        return 1 / (latt.x + r * latt.y + (r * r) * latt.z);
    }

    /**
     * Get the color of the light.
     * 
     * @return the color of the light.
     */
    MyColor getColor();

    /**
     * Get the direction toward the light.
     * 
     * @param currentPos
     *            a point in the scene
     * @return the vector going from <code>currentPos</code> to the source
     *         light.
     */
    Vec3 getDirection(Point currentPos);

    /**
     * Retrieve the attenuation of the light source (not implemented this year).
     * 
     * @return the attenuation of the light source.
     */
    MyColor getAttenuation();

    /**
     * Compute the distance of a light source.
     * 
     * @param pos
     *            a point in the scene
     * @return the distance between <code>pos</code> and the light source.
     */
    double getDistance(Point pos);
}
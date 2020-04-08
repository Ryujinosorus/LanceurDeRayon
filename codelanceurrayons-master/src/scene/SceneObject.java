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

import java.util.Optional;

import math.MyColor;
import math.Point;
import math.Vec3;

/**
 * The abstract representation of an object in the scene.
 * 
 * @author leberre
 *
 */
public abstract class SceneObject {
    public static final double EPSILON = 0.00001d;

    /**
     * The color provider for computing the diffuse color.
     */
    private final Material diffuse;

    /**
     * The color used for the reflection and the Blinn-Phong color model.
     */
    private final MyColor specular;

    /**
     * The shininess of the object.
     */
    private final double shininess;

    /**
     * Create an object in the scene with all the information needed for
     * computing its color.
     * 
     * @param diffuse
     *            the material (color provider) of that object.
     * @param specular
     *            the reflective color of the object.
     * @param shininess
     *            the shininess of the object.
     */
    protected SceneObject(Material diffuse, MyColor specular, double shininess) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    /**
     * Read-only access to the material of the object.
     * 
     * @return the material of the object.
     */
    public Material getDiffuse() {
        return diffuse;
    }

    /**
     * Read-only access to the specular color of the object.
     * 
     * @return the specular color of the object.
     */
    public MyColor getSpecular() {
        return this.specular;
    }

    /**
     * Read only access to the shininess of the object.
     * 
     * @return the shininess of the object.
     */
    public double shininess() {
        return shininess;
    }

    /**
     * Compute the normal at a given point.
     * 
     * A normal can be computed either at intersection type or on demand. We
     * chose the second option to avoid useless computation in scene with a huge
     * number of objects in the scene (such as the dragon).
     * 
     * @param p
     *            a point of the object, typically resulting from an
     *            intersection computation.
     * @return the normal of the object at that point.
     */
    public abstract Vec3 normal(Point p);

    /**
     * Compute if a given ray intersects that object.
     * 
     * That method uses Java 8 {@link Optional} class to provide a result. There
     * is an intersection iff {@link Optional#isPresent()} is <code>true</code>,
     * in which case the result is an {@link IntensionPoint} object, which
     * contains the ray and the distance of the intersection.
     * 
     * @param origin
     *            the origin of the ray
     * @param ray
     *            the direction of the ray
     * @return n Optional object containing an IntensionPoint if an intersection
     *         exists.
     */
    public abstract Optional<IntensionPoint> intersect(Point origin, Vec3 ray);

    /**
     * Project and normalize the coordinate of the 3D point (x,y,z)
     * <code>pos</code> into a 2D point in (x',z') such that
     * {@code 0 <= x',y'<=1}. That 2D point is returned as a 3D point with y=0,
     * i.e. (x',0,z').
     * 
     * That method is used to project a texture to an object.
     * 
     * @param pos
     *            a 3D point.
     * @return a 3D point (x',0,z') with {@code 0<=x,z<=1}.
     */
    public abstract Point projectAndNormalizeCoordinate(Point pos);
}

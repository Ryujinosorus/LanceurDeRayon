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

/**
 * Utility class to represent the intersection of a ray with an object in the
 * scene.
 * 
 * Here the fields are made public to access them more easily.
 * 
 * Note that we do not really break encapsulation because the field are
 * immutable.
 * 
 * @author leberre
 *
 */
public class Intersection {
    /**
     * The intersection point, given by its origin, its direction and its
     * length.
     */
    public final IntensionPoint point;

    /**
     * The object to which belong the point.
     */
    public final SceneObject object;

    public Intersection(final IntensionPoint point, final SceneObject object) {
        this.point = point;
        this.object = object;
    }
}
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
package math;

/**
 * Utility methods to build basic transformations.
 * 
 * Those methods may have been factories (static methods returning a Matrix4) in
 * the Matrix4 class.
 * 
 * @author leberre
 *
 */
public class Transformations {

    private Transformations() {
        // no constructor for utility classes.
    }

    public static Matrix4 rotate(final double degrees, final Vec3 axis) {
        double angle = Math.PI * degrees / 180;
        Matrix3 rot = new Matrix3(Math.cos(angle))
                .add(new Matrix3(axis.x * axis.x, axis.x * axis.y, axis.x * axis.z, axis.x * axis.y, axis.y * axis.y,
                        axis.y * axis.z, axis.x * axis.z, axis.y * axis.z, axis.z * axis.z).mul(1.0f - Math.cos(angle)))
                .add(new Matrix3(0, -axis.z, axis.y, axis.z, 0, -axis.x, -axis.y, axis.x, 0).mul(Math.sin(angle)));
        return new Matrix4(rot);
    }

    public static Matrix4 scale(final Vec3 sv) {
        return new Matrix4(sv.x, 0, 0, 0, 0, sv.y, 0, 0, 0, 0, sv.z, 0, 0, 0, 0, 1);

    }

    public static Matrix4 translate(final Vec3 tv) {
        return new Matrix4(1, 0, 0, tv.x, 0, 1, 0, tv.y, 0, 0, 1, tv.z, 0, 0, 0, 1);
    }
}

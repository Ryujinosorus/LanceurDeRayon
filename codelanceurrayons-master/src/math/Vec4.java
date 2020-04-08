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
 * A vector of dimension 4 to be used with transformation matrices.
 * 
 * @author leberre
 *
 */
public class Vec4 {

    public final double x, y, z, t;

    public Vec4(Vec3 v) {
        this(v.x, v.y, v.z, 0);
    }

    public Vec4(Point p) {
        this(p.x, p.y, p.z, 1);
    }

    public Vec4(double x, double y, double z, double t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public Vec4 add(Vec4 v) {
        return new Vec4(x + v.x, y + v.y, z + v.z, t + v.t);
    }

    public Vec4 minus(Vec4 v) {
        return new Vec4(x - v.x, y - v.y, z - v.z, t - v.t);
    }

    public Vec4 mul(Matrix4 m) {
        return new Vec4(x * m.get(0, 0) + y * m.get(1, 0) + z * m.get(2, 0) + t * m.get(3, 0),
                x * m.get(0, 1) + y * m.get(1, 1) + z * m.get(2, 1) + t * m.get(3, 1),
                x * m.get(0, 2) + y * m.get(1, 2) + z * m.get(2, 2) + t * m.get(3, 2),
                x * m.get(0, 3) + y * m.get(1, 3) + z * m.get(2, 3) + t * m.get(3, 3));
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    public Point toPoint() {
        return new Point(x, y, z);
    }

    public Vec4 normalize() {
        double length = length();
        return new Vec4(x / length, y / length, z / length, t / length);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z + t * t);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + "," + t + ")";
    }
}

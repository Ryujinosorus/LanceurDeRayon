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
 * A vector in a 3D space.
 * 
 * @author leberre
 *
 */
public class Vec3 extends Triplet {

    public Vec3(final double x, final double y, final double z) {
        super(x, y, z);
    }

    public Vec3 add(Vec3 v) {
        return new Vec3(x + v.x, y + v.y, z + v.z);
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y, z + p.z);
    }

    public Vec3 sub(Vec3 v) {
        return new Vec3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Multiplies a vector by a number.
     * 
     * @param d
     *            a real number.
     * @return a vector whose values are all multiplies by d.
     */
    public Vec3 mul(double d) {
        return new Vec3(x * d, y * d, z * d);
    }

    /**
     * This is the classical scalar (dot) product.
     * 
     * @param v
     *            a vector
     * @return the scalar corresponding to the dot product of this vector with
     *         v.
     */
    public double dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Computes the cross product of two vectors.
     * 
     * @param v
     *            a vector
     * @return a vector normal to the this vector and v.
     */
    public Vec3 cross(Vec3 v) {
        return new Vec3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    /**
     * Create a unit vector from this vector.
     * 
     * @return a vector v such that v.length()==1.
     */
    public Vec3 hat() {
        double length = length();
        return new Vec3(x / length, y / length, z / length);
    }

    /**
     * The length (or norm) of the vector.
     * 
     * @return the Euclidian norm of the vector.
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3 negate() {
        return new Vec3(-x, -y, -z);
    }

    @Override
    public String toString() {
        return "V " + x + " " + y + " " + z;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public Vec3 clone(double x, double y, double z) {
        return new Vec3(x, y, z);
    }
    
	@Override
    public boolean equals(Object obj) {
		if (obj instanceof Vec3) {
			return super.equals(obj);
		}
		return false;
	}
}

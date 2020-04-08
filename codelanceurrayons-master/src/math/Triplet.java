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
 * Triplets are meant to be multiplied by a matrix.
 * 
 * @author leberre
 *
 */
public abstract class Triplet {

    public final double x, y, z;

    protected Triplet(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Retrieve x coordinate.
     * 
     * @return x coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Retrieve y coordinate.
     * 
     * @return y coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Retrieve z coordinate.
     * 
     * @return z coordinate.
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Prototype design pattern.
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     * @param z
     *            coordinate
     * @return a triplet with the same type.
     */
    public abstract Triplet clone(double x, double y, double z);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Triplet other = (Triplet) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);    
    }
}

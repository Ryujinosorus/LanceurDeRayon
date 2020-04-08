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
 * A 3D coordinate.
 * 
 * @author leberre
 *
 */
public class Point extends Triplet {

    private Vec3 vectorForm = null;

    public Point(final double x, final double y, final double z) {
        super(x, y, z);
    }

    public Point add(Vec3 v) {
        return new Point(x + v.x, y + v.y, z + v.z);
    }

    public Vec3 asVector() {
        if (vectorForm == null) {
            vectorForm = new Vec3(x, y, z);
        }
        return vectorForm;
    }

    public Vec3 sub(Point p) {
        return new Vec3(x - p.x, y - p.y, z - p.z);
    }

    /**
     * Multiplies a point by a number.
     * 
     * @param d
     *            a real number.
     * @return a vector whose values are all multiplies by d.
     */
    public Point mul(double d) {
        return new Point(x * d, y * d, z * d);
    }

    @Override
    public String toString() {
        return "P " + x + " " + y + " " + z;
    }

    @Override
    public Point clone(double x, double y, double z) {
        return new Point(x, y, z);
    }
    
	@Override
    public boolean equals(Object obj) {
		if (obj instanceof Point) {
			return super.equals(obj);
		}
		return false;
	}
}

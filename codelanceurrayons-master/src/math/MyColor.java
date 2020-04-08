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

import java.awt.Color;

/**
 * A personal implementation of an RGB color.
 * 
 * @author leberre
 *
 */
public class MyColor extends Triplet {

    public static final MyColor BLACK = new MyColor(0, 0, 0);
    public static final MyColor WHITE = new MyColor(1, 1, 1);

    public MyColor(int rgb) {
        this(new Color(rgb));
    }

    public MyColor(Color c) {
    	this(c.getRed() / 255.0d,c.getGreen() / 255.0d,c.getBlue() / 255.0d);
    }
    
    public MyColor(final double r, final double g, final double b) {
       super(r,g,b);
    }

    public MyColor add(final MyColor c) {
        return new MyColor(x + c.x, this.y + c.y, this.z + c.z);
    }

    /**
     * Schur multiplication.
     * 
     * @param c
     *            a color
     * @return the componentwise multiplication of this color with c.
     */
    public MyColor times(final MyColor c) {
        return new MyColor(this.x * c.x, this.y * c.y, this.z * c.z);
    }

    public MyColor mul(final double f) {
        return new MyColor(x * f, this.y * f, this.z * f);
    }

    public MyColor div(final double f) {
        if (Double.compare(f, 0.0) == 0) {
            throw new IllegalArgumentException();
        }
        return new MyColor(x / f, this.y / f, this.z / f);
    }

    public Color toColor() {
        return new Color((float) Math.min(x, 1.0), (float) Math.min(y, 1.0), (float) Math.min(z, 1.0));
    }

    @Override
    public String toString() {
        return "C " + x + " " + y + " " + z;
    }

	@Override
	public Triplet clone(double x, double y, double z) {
		return new MyColor(x,y,z);
	}
	
	@Override
    public boolean equals(Object obj) {
		if (obj instanceof MyColor) {
			return super.equals(obj);
		}
		return false;
	}
}

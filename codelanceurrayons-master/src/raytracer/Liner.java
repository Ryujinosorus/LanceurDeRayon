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
package raytracer;

import math.MyColor;
import math.Point;
import math.Vec3;

/**
 * Line Analyzer.
 * 
 * The aim of that class is to simplify the retrieval of information from a line
 * in the scene file.
 * 
 * Basic checks are performed (e.g. color component cannot be great than 1.0).
 * 
 * @author leberre
 *
 */
public class Liner {

    private static final String NO_ENOUGH_ELEMENT_TO_READ = "No enough element to read";

    private String[] pieces;
    private int index;

    public Liner(final String line) {
        pieces = line.split("\\s+");
        if (pieces.length > 1) {
            index = 1;
        } else {
            index = 0;
        }
    }

    private int numberOfElementToRead() {
        return pieces.length - index + 1;
    }

    public Point readPoint() {
        if (numberOfElementToRead() < 3) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        double x = Double.parseDouble(pieces[index++]);
        double y = Double.parseDouble(pieces[index++]);
        double z = Double.parseDouble(pieces[index++]);
        return new Point(x, y, z);
    }

    public Vec3 readVector() {
        if (numberOfElementToRead() < 3) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        double x = Double.parseDouble(pieces[index++]);
        double y = Double.parseDouble(pieces[index++]);
        double z = Double.parseDouble(pieces[index++]);
        return new Vec3(x, y, z);
    }

    public MyColor readColor() {
        if (numberOfElementToRead() < 3) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        double r = readColorComponent();
        double g = readColorComponent();
        double b = readColorComponent();
        return new MyColor(r, g, b);
    }

    private double readColorComponent() {
        double d = Double.parseDouble(pieces[index++]);
        if (d > 1.0d) {
            throw new IllegalStateException("Read color value greater than 1");
        }
        return d;
    }

    public String getCommand() {
        return pieces[0];
    }

    public int readInt() {
        if (numberOfElementToRead() < 1) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        return Integer.parseInt(pieces[index++]);
    }

    public String readString() {
        if (numberOfElementToRead() < 1) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        return pieces[index++];
    }

    public double readDouble() {
        if (numberOfElementToRead() < 1) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        return Double.parseDouble(pieces[index++]);
    }

    public boolean getBoolean() {
        if (numberOfElementToRead() < 1) {
            throw new IllegalStateException(NO_ENOUGH_ELEMENT_TO_READ);
        }
        return Boolean.parseBoolean(pieces[index++]);
    }
}

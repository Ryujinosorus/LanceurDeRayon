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
package tools;

import math.MyColor;
import math.Point;
import math.Vec3;

public class CheckTriplet {

    private CheckTriplet() {
        // not meant to be instantiated
    }

    public static Object buildObject(String str) {
        String[] data = str.split(" ");
        if (data.length == 1) {
            return Double.parseDouble(data[0]);
        }
        if (data.length != 4) {
            throw new IllegalArgumentException("Wrong object string" + str);
        }
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        Object toReturn;

        switch (data[0]) {
        case "P":
            toReturn = new Point(x, y, z);
            break;
        case "V":
            toReturn = new Vec3(x, y, z);
            break;
        case "C":
            toReturn = new MyColor(x, y, z);
            break;
        default:
            throw new IllegalArgumentException("Unkown type " + data[0]);
        }
        return toReturn;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: checker \"TEST STRING\"");
            return;
        }
        String[] data = args[0].split(",");
        if (data.length != 3) {
            throw new IllegalArgumentException("Wrong test string");
        }
        String operation = data[1];
        Object o1 = buildObject(data[0]);
        Object o2 = buildObject(data[2]);
        try {
            Class<?> clazz2 = o2.getClass() == Double.class ? double.class : o2.getClass();
            Object o3 = o1.getClass().getMethod(operation, clazz2).invoke(o1, o2);
            System.out.println(o3);
        } catch (Exception e) {
            System.out.println("Interdit");
        }

    }
}
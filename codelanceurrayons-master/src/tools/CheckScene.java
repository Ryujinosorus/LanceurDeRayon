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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import raytracer.Parser;
import scene.Scene;

public class CheckScene {

    private CheckScene() {
        // not meant to be instantiated
    }

    public static void main(String[] args) throws IOException {
        Scene scene = new Parser(new BufferedReader(new FileReader(args[0]))).parse();
        System.out.println(scene.getOutputFileName());
        System.out.println(scene.getWidth() * scene.getHeight());
        System.out.println(scene.getObjects().size());
        System.out.println(scene.getLights().size());
    }
}
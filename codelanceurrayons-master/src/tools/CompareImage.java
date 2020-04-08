package tools;
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
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Le but de cette classe est de comparer deux images pixel par pixel, et de
 * créer un image représentant les différences entre deux images.
 * 
 * @author leberre
 *
 */
public final class CompareImage {

    private CompareImage() {
        // not meant to be instanciated
    }

    private static int compare(BufferedImage image1, BufferedImage image2, BufferedImage diff) {
        assert image1.getHeight() == image2.getHeight();
        assert image1.getWidth() == image2.getWidth();
        int nbdiff = 0;

        int colordiff, dr, dg, db, rgb1, rgb2;
        Color color1, color2;
        for (int i = 0; i < image1.getHeight(); i++) {
            for (int j = 0; j < image1.getWidth(); j++) {
                rgb1 = image1.getRGB(j, i);
                rgb2 = image2.getRGB(j, i);
                if ((rgb1 - rgb2) == 0) {
                    colordiff = 0;
                } else {
                    color1 = new Color(rgb1);
                    color2 = new Color(rgb2);
                    dr = Math.abs(color1.getRed() - color2.getRed());
                    dg = Math.abs(color1.getGreen() - color2.getGreen());
                    db = Math.abs(color1.getBlue() - color2.getBlue());
                    colordiff = new Color(dr, dg, db).getRGB();
                    nbdiff++;
                }
                diff.setRGB(j, i, colordiff);
            }
        }
        return nbdiff;
    }

    public static int compare(String image1File, String image2File, String diffFileName) throws IOException {
    	BufferedImage image1 = ImageIO.read(new File("image2.png"));
        BufferedImage image2 = ImageIO.read(new File("image1.png"));
        if (image1.getHeight() != image2.getHeight() || image1.getWidth() != image2.getWidth()) {
            System.err.println("The two images should be of the same size !");
            System.exit(20);
        }
        BufferedImage diff = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_RGB);
        int difference = compare(image1, image2, diff);
        if (difference > 0) {
            ImageIO.write(diff, "png", new File(diffFileName));
        }
        return difference;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: compare <image1> <image2>");
            System.exit(10);
        }
        int difference = compare(args[0], args[1], "diff.png");
        if (difference < 1000) {
            System.out.println("OK");
        } else {
            System.out.println("KO");
        }
        System.out.printf("%d%n", difference);
    }
}

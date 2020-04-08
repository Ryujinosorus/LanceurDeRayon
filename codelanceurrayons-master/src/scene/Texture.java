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
package scene;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import math.MyColor;
import math.Point;

/**
 * Render a bitmap image in RGB on the scene object.
 * 
 * @author leberre
 *
 */
public class Texture implements Material {

    /**
     * The texture to render on the object.
     */
    private final BufferedImage textureImage;

    /**
     * Create a texture based-material.
     * 
     * @param textureFileName
     *            the filename of the texture image.
     * @throws IOException
     *             if the image cannot be found or accessed.
     */
    public Texture(String textureFileName) throws IOException {
        this.textureImage = ImageIO.read(new File(textureFileName));
    }

    @Override
    public MyColor getColor(SceneObject sceneObject, Point pos) {
        Point p = sceneObject.projectAndNormalizeCoordinate(pos);
        assert Double.compare(p.y, 0.0) == 0;
        return new MyColor(textureImage.getRGB((int) (p.x * textureImage.getWidth() - 0.5),
                (int) (p.z * textureImage.getHeight() - 0.5)));
    }

}

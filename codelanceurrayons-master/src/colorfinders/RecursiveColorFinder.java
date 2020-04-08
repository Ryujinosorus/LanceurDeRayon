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
package colorfinders;

import java.util.Optional;

import math.MyColor;
import math.Point;
import math.Vec3;
import scene.Intersection;
import scene.Scene;


/**
 * Recursive color illumination model for indirect light.
 * 
 * The indirect illumination is computed using reflection, if the specular color
 * is not black.
 * 
 * @author leberre
 *
 */
public class RecursiveColorFinder extends PhongAttenuationColorFinder {

    @Override
    protected MyColor computeIndirectLight(Scene scene, Intersection intersection, int depth) {
        Point pos = intersection.point.toPoint();
        Vec3 eyeDirection = intersection.point.origin.sub(pos).hat();
        Vec3 normal = intersection.object.normal(pos);
        Vec3 reflection = eyeDirection.negate().add(normal.mul(2 * eyeDirection.dot(normal))).hat();
        Optional<Intersection> reflectionIntersection = scene.computeIntersection(pos.add(reflection.mul(EPSILON)),
                reflection);
        if (reflectionIntersection.isPresent()) {
            return intersection.object.getSpecular().times(findColor(scene, reflectionIntersection.get(), depth + 1));
        }
        return MyColor.BLACK;
    }

    @Override
    protected boolean canReceiveIndirectLight(Intersection intersection) {
        return !MyColor.BLACK.equals(intersection.object.getSpecular());
    }

    @Override
    public MyColor findColor(Scene scene, Intersection intersection, int depth) {
        if (depth == scene.getMaxDepth()) {
            return MyColor.BLACK;
        }
        return super.findColor(scene, intersection, depth);
    }
}

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

import math.MyColor;
import math.Point;
import math.Vec3;
import raytracer.ColorFinder;
import scene.Intersection;
import scene.Light;
import scene.Scene;

/**
 * Adapter class to implement an illumination model based on multiple light
 * sources.
 * 
 * @author leberre
 *
 */
public abstract class AbstractMultilightColorFinder implements ColorFinder {

    private boolean shadowEnabled = true;

    @Override
    public boolean isShadowEnabled() {
        return shadowEnabled;
    }

    @Override
    public void setShadowEnabled(boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
    }

    @Override
    public MyColor findColor(Scene scene, Intersection intersection, int depth) {
        Point pos = intersection.point.toPoint();
        MyColor color = scene.getAmbient();
        for (Light light : scene) {
            if (!shadowEnabled || lightNotMasked(scene, pos, light)) {
                color = color.add(computeColorForLight(intersection, pos, light));
            }
        }
        if (canReceiveIndirectLight(intersection)) {
            color = color.add(computeIndirectLight(scene, intersection, depth));
        }
        return color;
    }

    /**
     * Computes the indirect light received by a given intersection point in the
     * scene.
     * 
     * Indirect illumination is typically caused by reflection or refraction.
     * 
     * @param scene
     *            the scene to render
     * @param intersection
     *            an intersection point
     * @param depth
     *            the current depth in the computation.
     * @return the color of the intersection point.
     */
    protected MyColor computeIndirectLight(Scene scene, Intersection intersection, int depth) {
        return MyColor.BLACK;
    }

    /**
     * Decides if a given intersection point can receive indirect light.
     * 
     * @param intersection
     *            an intersection point
     * @return <code>true</code> iff the intersection point can receive indirect
     *         illumination.
     */
    protected boolean canReceiveIndirectLight(Intersection intersection) {
        return false;
    }

    /**
     * Utility method to detect if a light source is masked by an object in the
     * scene in a specific point.
     * 
     * There is no good place to put that method:
     * <ul>
     * <li>In Scene, we already have a specific
     * {@link Scene#lightIntersection(Point, Vec3, double)} method.</li>
     * <li>In Light, we would need to pass the scene object as a parameter to
     * call the specific intersection method.</li>
     * </ul>
     * 
     * As such, we make it static, here, because it is used only for computing
     * illumination and does not require any instance specific data.
     * 
     * @param scene
     *            the scene to render
     * @param pos
     *            a point in the scene
     * @param light
     *            the light source
     * @return <code>true</code> iff the light source is masked for that point.
     */
    private static boolean lightNotMasked(Scene scene, Point pos, Light light) {
        Vec3 direction = light.getDirection(pos);
        return !scene.lightIntersection(pos, direction, light.getDistance(pos));
    }

    /**
     * Method to compute the illumination at a given point for a given light
     * source.
     * 
     * The basic illumination models are found in
     * {@link Light#lambert(fr.univartois.l3mi.prj2.SceneObject, Point)} and
     * {@link Light#blinnPhong(Intersection, Point)}.
     * 
     * @param intersection
     *            the intersection point to illuminate (given in intension)
     * @param pos
     *            the 3D coordinate of the intersection point.
     * @param light
     *            the light source.
     * @return the color of that point for the given light source.
     */
    protected abstract MyColor computeColorForLight(Intersection intersection, Point pos, Light light);
}

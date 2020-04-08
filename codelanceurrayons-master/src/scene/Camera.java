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

import math.OrthonormalBasis;
import math.Point;
import math.Vec3;

/**
 * Contains all the information relative to the camera.
 * 
 * @author leberre
 *
 */
public class Camera {

    private final Point lookFrom;

    private final Point lookAt;

    private final Vec3 up;

    private final double fov;

    private final OrthonormalBasis basis;

    /**
     * Creates a camera object.
     * 
     * @param lookFrom
     *            where the camera is located.
     * @param lookAt
     *            where the camera looks at
     * @param up
     *            the direction up
     * @param fov
     *            the field of view (an angle, in degree)
     */
    public Camera(Point lookFrom, Point lookAt, Vec3 up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
        this.basis = new OrthonormalBasis(lookFrom, lookAt, up);
    }

    /**
     * Get the location of the camera.
     * 
     * @return the location of the camera
     */
    public Point getLookFrom() {
        return lookFrom;
    }

    /**
     * Get where the camera is looking at.
     * 
     * @return the point the camera looks at.
     */
    public Point getLookAt() {
        return lookAt;
    }

    /**
     * Get the up vector.
     * 
     * @return the up vector.
     */
    public Vec3 getUp() {
        return up;
    }

    /**
     * Get the field of view.
     * 
     * @return the field of view (in degree).
     */
    public double getFov() {
        return fov;
    }

    /**
     * Compute a ray direction according to the camera coordinate system.
     * 
     * @param alpha
     *            horizontal position
     * @param beta
     *            vertical position
     * @return a ray in the camera coordinate system.
     */
    public Vec3 computeRay(double alpha, double beta) {
        return basis.changeBase(new Vec3(alpha, beta, -1.0));
    }
}

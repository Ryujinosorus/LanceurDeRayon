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

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import math.MyColor;
import math.Vec3;
import scene.Intersection;
import scene.Scene;

/**
 * This is the class responsible for rendering a specific part of the scene.
 * 
 * A TracingJobs object correspond to the work executed on a specific thread.
 * 
 * There are no concurrency issue because the only shared objects are either
 * read-only (the scene) or modified on different places (the rendered image).
 * 
 * @author leberre
 *
 */
public class TracingJob {

    private final int fromx, tox, fromy, toy;

    private final Scene scene;

    /**
     * Create a tracing job for rendering a subset of the scene.
     * 
     * @param scene
     *            the scene to render.
     * @param fromx
     *            the upper left x coordinate of the image to render
     * @param fromy
     *            the upper left y coordinate of the image to render
     * @param tox
     *            the lower right x coordinate of the image to render
     * @param toy
     *            the lower right y coordinate of the image to render
     */
    private TracingJob(Scene scene, int fromx, int fromy, int tox, int toy) {
        this.scene = scene;
        this.fromx = fromx;
        this.fromy = fromy;
        this.tox = tox;
        this.toy = toy;
    }

    /**
     * The classical ray tracing loop for a subset of the image.
     * 
     * @param out
     *            the rendered image.
     * @param grid
     *            a boolean flag to enable drawing or not a grid in the image.
     * @param colorFinder
     *            the illumination strategy.
     */
    public void trace(BufferedImage out, boolean grid, ColorFinder colorFinder) {
        MyColor color;
        for (int y = fromy; y < toy; y++) {
            for (int x = fromx; x < tox; x++) {
                if (grid && scene.touchTheGrid(x, y)) {
                    color = MyColor.WHITE;
                } else {
                    Collection<Vec3> rays = scene.rayThroughPixel(x, y);
                    color = computeMeanColor(colorFinder, rays);
                }
                // to fix the different coordinate system between Java and our
                // model
                out.setRGB(x, scene.getHeight() - y - 1, color.toColor().getRGB());
            }
        }
    }

    /**
     * Compute the color for a list of rays and return the mean color by
     * component.
     * 
     * @param colorFinder
     *            an illumination model
     * @param rays
     *            a list of rays
     * @return the mean color for those rays
     */
    private MyColor computeMeanColor(ColorFinder colorFinder, Collection<Vec3> rays) {
        MyColor color = MyColor.BLACK;
        for (Vec3 ray : rays) {
            Optional<Intersection> intersection = scene.computeIntersection(scene.getCamera().getLookFrom(), ray);
            if (intersection.isPresent()) {
                color = color.add(colorFinder.findColor(scene, intersection.get()));
            }
        }
        color = color.div(rays.size());
        return color;
    }

    /**
     * Split the image into n**2 parts to be rendered in parallel.
     * 
     * @param n
     *            the number of division for each side of the image should be
     *            divided in
     * @return the sub images coordinate
     */
    public static Collection<TracingJob> split(int n, Scene scene) {
        Collection<TracingJob> jobs = new ArrayList<>();
        int w = scene.getWidth() / n;
        int h = scene.getHeight() / n;
        for (int i = 0; i < (n - 1); i++) {
            for (int j = 0; j < (n - 1); j++) {
                jobs.add(new TracingJob(scene, i * w, j * h, i * w + w, j * h + h));
            }
        }
        int lastw = (n - 1) * w;
        int lasth = (n - 1) * h;
        for (int i = 0; i < (n - 1); i++) {
            jobs.add(new TracingJob(scene, lastw, i * h, scene.getWidth(), i * h + h));
        }
        for (int i = 0; i < (n - 1); i++) {
            jobs.add(new TracingJob(scene, i * w, lasth, i * w + w, scene.getHeight()));
        }
        jobs.add(new TracingJob(scene, lastw, lasth, scene.getWidth(), scene.getHeight()));
        return jobs;
    }

    /**
     * Split the image by lines.
     * 
     * @return a collection of jobs for rendering each line separately.
     */
    public static Collection<TracingJob> lines(Scene scene) {
        Collection<TracingJob> jobs = new ArrayList<>();
        for (int i = 0; i < scene.getHeight(); i++) {
            jobs.add(new TracingJob(scene, 0, i, scene.getWidth(), i + 1));
        }
        return jobs;
    }
}

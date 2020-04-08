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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

import colorfinders.RecursiveColorFinder;
import scene.Scene;

/**
 * This is the main component of the raytracer. It configures the various
 * parameters, create a pool of threads to perform the computations and takes
 * care of storing the results in a file and to display a progress rendering
 * (optional, activated by default).
 * 
 * That class does not perform the ray tracing directly, it delegates that part
 * to a set of {@link TracingJob} objects.
 * 
 * @author leberre
 *
 */
public class RayTracer {
    private static final Logger LOGGER = Logger.getGlobal();
    private final boolean grid;

    private final ColorFinder colorFinder;

    /**
     * Build a raytracer without grid and support for reflective surfaces.
     */
    public RayTracer() {
        this(false, new RecursiveColorFinder());
    }

    /**
     * Build a raytracer.
     * 
     * @param grid
     *            enable a grid on the image.
     * @param colorFinder
     *            the color model to use for rendering the image.
     */
    public RayTracer(boolean grid, ColorFinder colorFinder) {
        this.grid = grid;
        this.colorFinder = colorFinder;
    }

    /**
     * Render a specific scene in a buffered image. That method launches as many
     * jobs in parallel as the number of available processing unit found by
     * {@link Runtime#availableProcessors()}.
     * 
     * @param scene
     *            the scene to render
     * @param out
     *            the image containing the result of the rendering
     * @param component
     *            a component to refresh periodically if it exists
     */
    public void trace(Scene scene, BufferedImage out, JComponent component) {
        int sideDivision;
        String vmParameter = System.getProperty("division");
        if (vmParameter == null) {
            sideDivision = 10;
        } else {
            sideDivision = Integer.valueOf(vmParameter);
        }
        boolean lines = System.getProperty("lines") != null;
        boolean sequential = System.getProperty("sequential") != null;
        try {
            // launch as many threads as available computing unit
            ExecutorService pool = Executors
                    .newFixedThreadPool(sequential ? 1 : Runtime.getRuntime().availableProcessors());
            for (TracingJob job : lines ? TracingJob.lines(scene) : TracingJob.split(sideDivision, scene)) {
                pool.execute(() -> {
                    job.trace(out, grid, colorFinder);
                    component.repaint();
                });
            }
            // notify the pool to stop when the jobs are done
            pool.shutdown();
            // wait for the jobs to be done
            String str = System.getProperty("timeout");
            int timeout;
            if (str == null) {
                timeout = 1200;
            } else {
                timeout = Integer.parseInt(str.trim());
            }
            if (!pool.awaitTermination(timeout, TimeUnit.SECONDS)) {
                LOGGER.severe("Timeout reached!!!");
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE,"thread interrupted!",e);
            Thread.currentThread().interrupt();
        }
    }

}

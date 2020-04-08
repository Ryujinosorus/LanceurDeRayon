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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import math.MyColor;
import math.Point;
import math.Vec3;

/**
 * That class represents the whole scene.
 * 
 * It also has the responsibility to build the appropriate coordinate system for
 * the scene, and to take care of the anti-aliasing policy.
 * 
 * @author leberre
 *
 */
public class Scene implements Iterable<Light> {

    /**
     * Image width in pixels.
     */
    private int width;

    /**
     * Image height in pixels.
     */
    private int height;

    /**
     * Image half height in the scene unit.
     */
    private double imageHalfHeight;

    /**
     * Image half width in the scene unit.
     */
    private double imageHalfWidth;

    /**
     * Number of pixels per height unit
     */
    private long pixelsForUnitHeight;

    /**
     * Number of pixels per width unit
     */
    private long pixelsForUnitWidth;

    /**
     * Half width in pixels.
     */
    private int halfw;

    /**
     * Half height in pixels.
     */
    private int halfh;

    /**
     * Filename of the rendered image.
     */
    private String outputFileName;

    /**
     * Camera settings.
     */
    private Camera camera;

    /**
     * The objects in the scene.
     */
    private Collection<SceneObject> objects = new ArrayList<>();

    /**
     * The source lights in the scene.
     */
    private Collection<Light> lights = new ArrayList<>();

    /**
     * The maximum depth for recursive raytracing.
     */
    private int maxdepth = 1;

    /**
     * The ambient color.
     */
    private MyColor ambient = MyColor.BLACK;

    /**
     * The distance between the camera (lookFrom) and the middle of the scene
     * (lookAt).
     */
    private double length;

    /**
     * The anti-aliasing strategy.
     */
    private Sampler sampler;

    /**
     * Additional properties found in the scene file. We use a map to avoid
     * creating a new field each time there is a new addition in the scene file.
     */
    private final Map<String, String> properties = new HashMap<>();

    /**
     * Enable shadow on scenes
     */
    private boolean shadow = false;

    /**
     * Simply pick a single vector in the middle of the pixel.
     */
    public final Sampler middle = (x, y) -> {
        Collection<Vec3> rays = new ArrayList<>();
        double alpha = imageHalfWidth * (x - halfw + 0.5d) / halfw;
        double beta = imageHalfHeight * (y - halfh + 0.5d) / halfh;
        rays.add(camera.computeRay(alpha, beta));
        return rays;
    };

    /**
     * Randomly generate a given number of vectors for a pixel.
     */
    public final Sampler random = new Sampler() {
        private final Random rand = new Random(1234);

        private int n = 10;

        @Override
        public Collection<Vec3> rayThroughPixel(int x, int y) {
            Collection<Vec3> rays = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                double alpha = imageHalfWidth * (x - halfw + rand.nextDouble()) / halfw;
                double beta = imageHalfHeight * (y - halfh + rand.nextDouble()) / halfh;
                rays.add(camera.computeRay(alpha, beta));
            }
            return rays;
        }

        @Override
        public void setParameter(int n) {
            this.n = n;
        }

    };

    /**
     * Regularly generate a vector for the middle of each subpixel in a grid.
     * The size of the grid nxn is given as a parameter (n).
     */
    public final Sampler grid = new Sampler() {

        private int side = 6;

        @Override
        public Collection<Vec3> rayThroughPixel(int x, int y) {
            Collection<Vec3> rays = new ArrayList<>(side * side);
            double sw = 1.0 / side;
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    double alpha = imageHalfWidth * (x - halfw + (i + 0.5d) * sw) / halfw;
                    double beta = imageHalfHeight * (y - halfh + (j + 0.5d) * sw) / halfh;
                    rays.add(camera.computeRay(alpha, beta));
                }
            }
            return rays;
        }

        @Override
        public void setParameter(int n) {
            this.side = n;
        }
    };

    /**
     * Create a scene of a given dimension for a given filename.
     * 
     * @param width
     *            the width of the image to produce in pixels.
     * @param height
     *            the height of the image to produce in pixels.
     * @param output
     *            the filename of the image to produce.
     * @param camera
     *            the camera settings.
     */
    public Scene(int width, int height, String output, Camera camera) {
        this.width = width;
        this.height = height;
        this.outputFileName = output;
        this.camera = camera;
        this.sampler = middle;
    }

    public Scene() {
        this(0, 0, "output.png", null);
    }

    /**
     * 
     * @return the width of the rendered scene, in pixels
     */
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @return the height of the rendered scene, in pixels
     */
    public int getHeight() {
        return height;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Get the name of the file to store the rendered scene.
     * 
     * @return the filename of the rendered scene.
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * Change the name of the file to store the rendered scene.
     * 
     * @param outputFileName
     *            the filename of the rendered scene.
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     * Add a new object to the scene.
     * 
     * @param o
     *            a new object.
     */
    public void addObject(SceneObject o) {
        objects.add(o);
    }

    /**
     * Add a new light source to the scene.
     * 
     * @param l
     *            a new light source.
     */
    public void addLight(Light l) {
        lights.add(l);
    }

    /**
     * Get camera settings.
     * 
     * @return the camera settings.
     */
    public Camera getCamera() {
        return camera;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Detect if there is a scene object intersecting the vector
     * <code>direction</code> from <code>pos</code> within the
     * <code>limit</code> distance.
     * 
     * That method is typically used to check if an object hides a light source.
     * 
     * Compared to the classical @link
     * {@link Scene#computeIntersection(Point, Vec3)} method, this method stops
     * as soon as an intersection is detected (there is no minimization
     * process).
     * 
     * @param pos
     *            the origin of the ray
     * @param direction
     *            the direction of the ray
     * @param limit
     *            the distance within which the intersection should happen.
     * @return true iff an object intersect the ray within the given distance
     *         limit.
     * @see #computeIntersection(Point, Vec3)
     */
    public boolean lightIntersection(Point pos, Vec3 direction, double limit) {
        Optional<IntensionPoint> p;
        for (SceneObject o : objects) {
            p = o.intersect(pos, direction);
            if (p.isPresent() && p.get().length < limit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the closest intersection of an object of the scene with a ray.
     * 
     * That method uses Java 8 new {@link Optional} class. An intersection
     * exists iff {@link Optional#isPresent()} returns true. In that case, the
     * value of {@link Optional#get()} is an {@link Intersection} object.
     * 
     * @param pos
     *            the start of the ray
     * @param direction
     *            the direction of the ray
     * @return an Optional object containing the closest intersection if it
     *         exists.
     */
    public Optional<Intersection> computeIntersection(Point pos, Vec3 direction) {
        Optional<IntensionPoint> p;
        IntensionPoint minpoint = new IntensionPoint(null, null, Double.POSITIVE_INFINITY);
        SceneObject intersected = null;
        IntensionPoint ipoint;
        for (SceneObject o : objects) {
            p = o.intersect(pos, direction);
            if (p.isPresent()) {
                ipoint = p.get();
                if (ipoint.length < minpoint.length) {
                    minpoint = ipoint;
                    intersected = o;
                }
            }
        }
        if (intersected != null) {
            return Optional.of(new Intersection(minpoint, intersected));
        }
        return Optional.empty();
    }

    public String getAdditionalProperty(String key) {
        return properties.get(key);
    }

    public void addAdditionalProperty(String key, String value) {
        properties.put(key, value);
    }

    /**
     * Compute all scene informations. That method must be called before any
     * treatment.
     */
    public void initPixelHeightAndWidth() {
        halfw = width / 2;
        halfh = height / 2;
        length = camera.getLookFrom().sub(camera.getLookAt()).length();
        // translates fovy from degres to radian
        double fovy = Math.PI * camera.getFov() / 180;
        imageHalfHeight = Math.tan(fovy / 2);
        pixelsForUnitHeight = Math.round(halfh / (imageHalfHeight * length * 1.0d));
        imageHalfWidth = imageHalfHeight * width / height;
        pixelsForUnitWidth = Math.round(halfw / (imageHalfWidth * length * 1.0d));
    }

    /**
     * Access the half height of the image (in y unit).
     * 
     * @return the half height of the image
     */
    public double getHalfHeight() {
        return imageHalfHeight * length;
    }

    /**
     * Access the half width of the image (in x unit).
     * 
     * @return the half width of the image
     */
    public double getHalfWidth() {
        return imageHalfWidth * length;
    }

    /**
     * Check if a point belong to the optional grid corresponding to unit values
     * for x and y axis on the scene.
     * 
     * @param x
     *            horizontal coordinate
     * @param y
     *            vertical coordinate
     * @return true iff that point belong to a scene grid.
     */
    public boolean touchTheGrid(int x, int y) {
        return Math.abs(x - halfw) % pixelsForUnitWidth == 0 || Math.abs(y - halfh) % pixelsForUnitHeight == 0;
    }

    /**
     * Compute a collection of sampling rays for a given pixel.
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     * @return a collection of sampling rays to decide the color of the pixel.
     */
    public Collection<Vec3> rayThroughPixel(int x, int y) {
        return sampler.rayThroughPixel(x, y);
    }

    /**
     * Set the sampling strategy.
     * 
     * That method is supposed to be called to decode command line parameters.
     * 
     * Expected input are <code>grid/6</code>, <code>sampling/10</code> or
     * <code>middle</code> for instance.
     * 
     * @param sampler
     *            a sampling implementation name implemented in that class,
     *            followed by an optional "/integer" information.
     * 
     */
    public void setSampler(String sampler) {
        String[] pieces = sampler.split("/");
        setSampler(pieces[0], pieces.length > 1 ? Integer.parseInt(pieces[1]) : 1);
    }

    /**
     * Set the sampling strategy.
     * 
     * @param sampler
     *            a sampling implementation name implemented in that class.
     * @param number
     *            an integer
     */
    public void setSampler(String sampler, int number) {
        try {
            this.sampler = (Sampler) getClass().getField(sampler.toLowerCase()).get(this);
            this.sampler.setParameter(number);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            Logger.getGlobal().warning(e.getMessage());
        }
    }

    /**
     * Set the sampling strategy.
     * 
     * Allow custom sampling strategies to be implemented in the scene.
     * 
     * @param sampler
     *            a new sampling strategy.
     */
    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    @Override
    public Iterator<Light> iterator() {
        return lights.iterator();
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<SceneObject> getObjects() {
        return objects;
    }

    /**
     * Set the maximum depth recursion for reflective illumination.
     * 
     * @param maxdepth
     *            the maximum recursive depth
     */
    public void setMaxDepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

    /**
     * Get the maximum depth recursion for reflective illumination.
     * 
     * @return the maximum recursive depth
     */
    public int getMaxDepth() {
        return this.maxdepth;
    }

    /**
     * Get the ambient color.
     * 
     * @return the ambient color.
     */
    public MyColor getAmbient() {
        return ambient;
    }

    /**
     * Set the ambient color.
     * 
     * @param ambient
     *            the ambient color.
     */
    public void setAmbient(MyColor ambient) {
        this.ambient = ambient;
    }

    public boolean isShadowEnabled() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}

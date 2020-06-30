import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import environment.*;
import math.Color;
import math.Point3D;
import math.Ray;
import math.Vector3D;

public class ImageGenerator {

    private final String sceneFile;
    private List<BasicObject> objects;
    private Scene scene;

    private Vector3D[] system; // system (calculated after scene reading)
    private double[] pixelDim; // pixelDim of pixels

    ImageGenerator(String sceneFile) {
        this.sceneFile = sceneFile;
    }

    public void generate() throws IOException {
        SceneReader reader = new SceneReader(sceneFile);
        this.scene = reader.read();

        // the image rendered
        BufferedImage render = new BufferedImage(this.scene.getSize()[0], scene.getSize()[1], BufferedImage.TYPE_INT_RGB);

        // all the objects
        this.objects = this.scene.getProps();

        Point3D o = this.scene.getCamera().getPosition(); // origin
        this.system = this.calculateCoordinateSystem(); // calculate system
        this.pixelDim = this.calculatePixelDimension(); // calculate pixel dim

        // For Threading
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // loop through all the image and give jobs
        for (int y = 0; y < this.scene.getSize()[1]; y++) {
            final int tmp = y;
            pool.execute(() -> generateRow(tmp, o, render));
        }

        // Wait the ExecutorService end his jobs
        pool.shutdown();
        try {
            if (!pool.awaitTermination(1200, TimeUnit.SECONDS))
                System.err.println("Timeout reached!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // write the image
        ImageIO.write(render, "PNG", new File(this.scene.getOutput()));
    }

    private void generateRow(int y, Point3D o, BufferedImage render) {
        for (int x = 0; x < this.scene.getSize()[0]; x++) {
            Vector3D d = this.calculateDVector(new Vector3D(x, y, 1)); // calculate D vector
            Intersected intersected = this.intersect(new Ray(o, d), null, true); // find the closest intersected object

            if (intersected.hasIntersected) {
                Color calculatedColor = this.calculateColor(o, d, intersected, 1);

                // it is useful because we need to round
                java.awt.Color color = new java.awt.Color(
                        (float) calculatedColor.getX(),
                        (float) calculatedColor.getY(),
                        (float) calculatedColor.getZ()
                );

                render.setRGB(x, this.scene.getSize()[1] - y - 1, color.getRGB());
            } else {
                render.setRGB(x, this.scene.getSize()[1] - y - 1, 0); // color in black
            }
        }
    }

    /**
     * Calculate an orthonormal coordinate system
     *
     * @return an array containing u,v,w
     */
    private Vector3D[] calculateCoordinateSystem() {
        Camera camera = this.scene.getCamera();

        Vector3D[] ret = new Vector3D[3];

        ret[2] = this.calculateAxe();
        ret[0] = camera.getUpperEye().cross(ret[2]).normalize();
        ret[1] = ret[2].cross(ret[0]).normalize();

        return ret;
    }

    /**
     * @return pixel pixelDim
     */
    private double[] calculatePixelDimension() {
        double[] dimensions = new double[2];

        double fovr = this.scene.getCamera().getFov() * Math.PI / 180;

        dimensions[1] = Math.tan(fovr / 2); // height
        dimensions[0] = dimensions[1] * ((double) this.scene.getSize()[0] / (double) this.scene.getSize()[1]); // width

        return dimensions;
    }

    /**
     * Calculate the director vector that goes from the eye to the pixel
     *
     * @param ij vector going to the pixel
     * @return the director vector that goes from the eye to the pixel
     */
    private Vector3D calculateDVector(Vector3D ij) {
        double pixelwidth = this.pixelDim[0];
        double pixelHeight = this.pixelDim[1];

        double imgwidth = this.scene.getSize()[0];
        double imgheight = this.scene.getSize()[1];

        double na = pixelwidth / (imgwidth / 2);
        double nb = pixelHeight / (imgheight / 2);

        double a = na * (ij.getX() - (imgwidth / 2) + 0.5);
        double b = nb * (ij.getY() - (imgheight / 2) + 0.5);

        Vector3D u = this.system[0];
        Vector3D v = this.system[1];
        Vector3D w = this.system[2];

        return u.mul(a).add(v.mul(b)).sub(w).normalize();
    }

    /**
     * Calculate the axe that passes through the eye and the point looked
     *
     * @return a Vector3D
     */
    private Vector3D calculateAxe() {
        Camera camera = this.scene.getCamera();

        Point3D lookFrom = camera.getPosition();
        Vector3D lookAt = camera.getLookAt();

        return lookFrom.sub(lookAt).normalize();
    }

    /**
     * Return the object the ray intersected with
     *
     * @param ray      the ray from the eye to the object
     * @param excluded The object exclude from the loop (useful only for the shadows)
     * @param closest  Should it return the closest object or not
     * @return the object that intersected
     */
    private Intersected intersect(Ray ray, BasicObject excluded, boolean closest) {
        Intersected intersected = new Intersected();
        for (BasicObject object : objects) {
            Double t = object.hit(ray);

            if (t != null && !object.equals(excluded) && (!intersected.hasIntersected || t < intersected.t)) {
                intersected.hasIntersected = true;
                intersected.t = t;
                intersected.object = object;
                if (!closest) return intersected;
            }
        }
        return intersected;
    }

    /**
     * Calculate a color for a given direction
     *
     * @param origin            the origin
     * @param direction         a direction from the origin
     * @param intersectedObject the intersected object
     * @param depth             accumulator, count the depth of recursive call
     * @return a color
     */
    private Color calculateColor(Point3D origin, Vector3D direction, Intersected intersectedObject, int depth) {

        // formula : la + somme(max(n * ldir[i], 0) * lightcolor[i] * diffuseColor + max(n * h, 0)^shininess * lightcolor[i] * specularColor)

        Point3D p = origin.add(direction.mul(intersectedObject.t));

        BasicObject object = intersectedObject.object;
        Vector3D n = object.getNormal(p);

        Color la = this.scene.getAmbient();
        Color colDiffuse = object.getDiffuse();
        Color specular = object.getSpecular();

        Color sum = new Color();

        for (Light light : this.scene.getLights()) {

            Color lightColor = light.getColor();
            Vector3D lightDir = light.getLightDir(p);

            Color ip = light.getPhongIllumination(p, direction.mul(-1), n, object.getShininess(), specular);

            if (this.scene.isShadowEnabled()) {

                Intersected intersected = this.intersect(new Ray(p, lightDir), object, false);

                if (intersected.hasIntersected) continue;

            }
            sum = sum.add(lightColor.mul(Math.max(n.dot(lightDir), 0)).times(colDiffuse).add(ip));
        }

        Color result = la.add(sum);

        // calculate reflected ray with recursive call
        if (!specular.equals(0, 0, 0) && this.scene.getMaxDepth() > depth++) {
            // r = direction + 2 * (normal . -direction) * normal
            Vector3D r = direction.add(
                    n.mul(
                            2 * n.dot(direction.mul(-1))
                    )
            ).normalize();

            Intersected intersected = this.intersect(new Ray(p, r), object, true);
            if (intersected.hasIntersected) {
                Color cp = this.calculateColor(p, r, intersected, depth); // c'
                result = result.add(specular.times(cp)); // c = c + specular * c'
            }
        }

        return result.cap();
    }

    /**
     * Intersected object
     */
    private static class Intersected {
        public BasicObject object;
        public double t;
        public boolean hasIntersected = false;
    }
}

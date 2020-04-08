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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import math.Transformations;
import math.Matrix4;
import math.MyColor;
import math.Point;
import math.Vec3;
import scene.Camera;
import scene.Checker;
import scene.DirectedLight;
import scene.Material;
import scene.PlainColor;
import scene.Plane;
import scene.PointLight;
import scene.Scene;
import scene.Sphere;
import scene.Texture;
import scene.Triangle;

/**
 * Parse a scene file to create a SceneObject.
 * 
 * That class is using one method per command in the scene file. That idea come
 * from the students, implemented with the refactoring
 * "Replace Method with Method Object".
 * 
 * @author leberre
 *
 */
public class Parser {
    private static final Logger LOGGER = Logger.getGlobal();

    private final Scene scene = new Scene();
    private Material diffuse = new PlainColor(MyColor.BLACK);
    private MyColor specular = MyColor.BLACK;
    private double shininess = 1.0;
    private MyColor attenuation = null;
    private ArrayList<Point> vertices = new ArrayList<>();
    private Deque<Matrix4> transformationStack = new ArrayDeque<>();

    private final BufferedReader in;

    public Parser(BufferedReader in) {
        this.in = in;
        // start with identity matrix
        transformationStack.add(new Matrix4(1.0));
    }

    /**
     * Replace the transformation on the stack by the transformation resulting
     * from a right multiplication.
     * 
     * @param m
     *            a transformation matrix
     * @param stack
     *            a non empty transformation stack.
     */
    private void rightMultiply(Matrix4 m) {
        assert !transformationStack.isEmpty();
        Matrix4 t = transformationStack.pop();
        transformationStack.push(t.mul(m));
    }

    public Scene parse() throws IOException {
        LineNumberReader linedin = new LineNumberReader(in);
        String line;
        try {
            while ((line = linedin.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    // ignore comments and empty lines
                    continue;
                }
                handleCommand(linedin, line);

            }
        } catch (Exception e) {
            LOGGER.severe(" error line " + linedin.getLineNumber() + " > " + e.getMessage());
            throw e;
        }
        scene.initPixelHeightAndWidth();
        return scene;
    }

    private void handleCommand(LineNumberReader linedin, String line) {
        Liner liner = new Liner(line);
        try {
            Parser.class.getMethod(liner.getCommand(), Liner.class).invoke(this, liner);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            scene.addAdditionalProperty(liner.getCommand(), liner.readString());
            LOGGER.warning("Did not manage > " + line + "in line " + linedin.getLineNumber());
        }
    }

    public void size(Liner liner) {
        scene.setWidth(liner.readInt());
        scene.setHeight(liner.readInt());
    }

    public void output(Liner liner) {
        scene.setOutputFileName(liner.readString());
    }

    public void sampling(Liner liner) {
        scene.setSampler(liner.readString(), liner.readInt());
    }

    public void camera(Liner liner) {
        Point lookFrom = liner.readPoint();
        Point lookAt = liner.readPoint();
        Vec3 up = liner.readVector();
        double fov = liner.readDouble();
        scene.setCamera(new Camera(lookFrom, lookAt, up, fov));
    }

    public void maxdepth(Liner liner) {
        scene.setMaxDepth(liner.readInt());
    }

    public void ambient(Liner liner) {
        scene.setAmbient(liner.readColor());
    }

    public void directional(Liner liner) {
        scene.addLight(new DirectedLight(liner.readVector(), liner.readColor()));
    }

    public void diffuse(Liner liner) {
        diffuse = new PlainColor(liner.readColor());
    }

    public void checker(Liner liner) {
        diffuse = new Checker(liner.readColor(), liner.readColor(), liner.readDouble());
    }

    public void texture(Liner liner) {
        try {
            diffuse = new Texture(liner.readString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE," texture error ",e);
        }
    }

    public void specular(Liner liner) {
        specular = liner.readColor();
    }

    public void shininess(Liner liner) {
        shininess = liner.readDouble();
    }

    public void attenuation(Liner liner) {
        attenuation = liner.readColor();
    }

    public void point(Liner liner) {
        PointLight light = new PointLight(liner.readPoint(), liner.readColor());
        if (attenuation != null) {
            light.setAttenuation(attenuation);
        }
        scene.addLight(light);
    }

    public void maxverts(Liner liner) {
        int maxverts = liner.readInt();
        vertices.ensureCapacity(maxverts);
    }

    public void vertex(Liner liner) {
        vertices.add(liner.readPoint());
    }

    public void triangle(Liner liner) {
        // specific for rendering the shuttle
        scene.addObject(new Triangle(liner.readPoint(), liner.readPoint(), liner.readPoint(),
                transformationStack.peek(), diffuse, specular, shininess));
    }

    public void tri(Liner liner) {
        int a = liner.readInt();
        int b = liner.readInt();
        int c = liner.readInt();
        scene.addObject(new Triangle(vertices.get(a), vertices.get(b), vertices.get(c), transformationStack.peek(),
                diffuse, specular, shininess));
    }

    public void sphere(Liner liner) {
        scene.addObject(new Sphere(liner.readPoint(), liner.readDouble(), transformationStack.peek(), diffuse, specular,
                shininess));
    }

    public void plane(Liner liner) {
        scene.addObject(new Plane(scene, liner.readPoint(), liner.readVector(), diffuse, specular, shininess));
    }

    public void translate(Liner liner) {
        rightMultiply(Transformations.translate(liner.readVector()));
    }

    public void scale(Liner liner) {
        rightMultiply(Transformations.scale(liner.readVector()));
    }

    public void rotate(Liner liner) {
        Vec3 axis = liner.readVector();
        double degree = liner.readDouble();
        rightMultiply(Transformations.rotate(degree, axis.hat()));
    }

    public void pushTransform(Liner liner) {
        transformationStack.push(transformationStack.peek());
    }

    public void popTransform(Liner liner) {
        transformationStack.pop();
    }

    public void shadow(Liner liner) {
        scene.setShadow(liner.getBoolean());
    }
}

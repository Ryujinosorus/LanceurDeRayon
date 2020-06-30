package environment;

import math.Color;
import math.Point3D;
import math.Vector3D;

public abstract class Light {
    private Color color;

    Light(Color color) {
        this.color = color;
    }

    /**
     * Calculate the Phong illumination of a point
     * formula : max(n * h, 0)^shininess * lightColor * specularColor
     *
     * @param p a point
     * @param eyeDir the direction of the eye
     * @param normal the normal of the object
     * @param shininess the shininess of the object
     * @param specular the specular color of the object
     * @return the illumination of Phong
     */
    public Color getPhongIllumination(Point3D p, Vector3D eyeDir, Vector3D normal, int shininess, Color specular) {
        Vector3D h = this.getLightDir(p).add(eyeDir).normalize(); // halfway vector
        double specAngle  = Math.max(normal.dot(h), 0); // max(normal * h, 0) which is the spec angle

        double intensity = Math.pow(specAngle, shininess); // specAngle^shininess intensity of specular light

        return this.getColor().mul(intensity).times(specular); // intensity * lightcolor * specularColor (Light Blinn-Phong)
    }

    // return light direction
    public abstract Vector3D getLightDir(Point3D p);

    public Color getColor() {
        return this.color;
    }
}

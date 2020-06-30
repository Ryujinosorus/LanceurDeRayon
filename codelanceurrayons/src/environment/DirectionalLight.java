package environment;

import math.Color;
import math.Point3D;
import math.Vector3D;

public class DirectionalLight extends Light {

    // light direction
    private Vector3D direction;

    DirectionalLight(Vector3D direction, Color col) {
        super(col);
        this.direction = direction;
    }

    @Override
    public Vector3D getLightDir(Point3D p) {
        return this.direction.normalize();
    }

}

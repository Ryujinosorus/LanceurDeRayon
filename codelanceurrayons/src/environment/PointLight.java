package environment;

import math.Color;
import math.Point3D;
import math.Vector3D;

public class PointLight extends Light {

    private Point3D position;

    PointLight(Point3D position, Color col) {
        super(col);
        this.position = position;
    }

    @Override
    public Vector3D getLightDir(Point3D p) {
        return this.position.sub(p).normalize();
    }
}

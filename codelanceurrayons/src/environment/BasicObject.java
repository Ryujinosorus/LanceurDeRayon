package environment;

import math.Color;
import math.Point3D;
import math.Ray;
import math.Vector3D;

public abstract class BasicObject {

	protected Color diffuse; // color of an object when illuminated by ambient light
	protected Color specular; // color of the light from a shiny surface
	protected int shininess; // how much it shines

	public abstract Double hit(Ray ray);

	/**
	 * get normal from a 3D point
	 *
	 * @param p a 3D point
	 * @return the normal
	 */
	public abstract Vector3D getNormal(Point3D p);

	public Color getDiffuse() {
		return this.diffuse;
	}

	public Color getSpecular() {
		return this.specular;
	}

	public int getShininess() {
		return this.shininess;
	}
}

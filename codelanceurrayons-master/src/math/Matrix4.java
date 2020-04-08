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
package math;

import java.util.Arrays;

/**
 * A 4x4 square matrix.
 * 
 * The code works, but can probably be better written.
 * 
 * @author leberre
 *
 */
public class Matrix4 {

	private double[][] content;

	public static final Matrix4 IDENTITY = new Matrix4(1.0);

	public Matrix4() {
		this(0.0);
	}

	public Matrix4(double d) {
		this(d, 0, 0, 0, 0, d, 0, 0, 0, 0, d, 0, 0, 0, 0, d);
	}

	public Matrix4(Matrix3 m) {
		this(m.get(0, 0), m.get(0, 1), m.get(0, 2), 0, m.get(1, 0), m.get(1, 1), m.get(1, 2), 0, m.get(2, 0),
				m.get(2, 1), m.get(2, 2), 0, 0, 0, 0, 1);
	}

	public Matrix4(double... ds) {
		if (ds.length != 16) {
			throw new IllegalArgumentException("Building a 4x4 matrix requires 16 values");
		}
		this.content = new double[][] { { ds[0], ds[1], ds[2], ds[3] }, { ds[4], ds[5], ds[6], ds[7] },
				{ ds[8], ds[9], ds[10], ds[11] }, { ds[12], ds[13], ds[14], ds[15] } };
	}

	public Matrix4 add(Matrix4 m) {
		Matrix4 res = new Matrix4();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.content[i][j] = content[i][j] + m.content[i][j];
			}
		}
		return res;
	}

	public Matrix4 sub(Matrix4 m) {
		Matrix4 res = new Matrix4();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.content[i][j] = content[i][j] - m.content[i][j];
			}
		}
		return res;
	}

	public Matrix4 mul(Matrix4 m) {
		Matrix4 res = new Matrix4();
		double sum;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sum = 0.0;
				for (int k = 0; k < 4; k++) {
					sum += content[i][k] * m.content[k][j];
				}
				res.content[i][j] = sum;
			}
		}
		return res;
	}

	public Matrix4 mul(double d) {
		Matrix4 res = new Matrix4();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.content[i][j] = content[i][j] * d;
			}
		}
		return res;
	}

	public Vec3 mul(Vec3 v) {
		return mul(new Vec4(v)).toVec3();
	}

	public Point mul(Point p) {
		return mul(new Vec4(p)).toPoint();
	}

	public Vec4 mul(Vec4 v) {
		return new Vec4(v.x * content[0][0] + v.y * content[0][1] + v.z * content[0][2] + v.t * content[0][3],
				v.x * content[1][0] + v.y * content[1][1] + v.z * content[1][2] + v.t * content[1][3],
				v.x * content[2][0] + v.y * content[2][1] + v.z * content[2][2] + v.t * content[2][3],
				v.x * content[3][0] + v.y * content[3][1] + v.z * content[3][2] + v.t * content[3][3]);
	}

	public double det() {
		Matrix3 m1 = new Matrix3(content[1][1], content[1][2], content[1][3], content[2][1], content[2][2],
				content[2][3], content[3][1], content[3][2], content[3][3]);
		Matrix3 m2 = new Matrix3(content[1][0], content[1][2], content[1][3], content[2][0], content[2][2],
				content[2][3], content[3][0], content[3][2], content[3][3]);
		Matrix3 m3 = new Matrix3(content[1][0], content[1][1], content[1][3], content[2][0], content[2][1],
				content[2][3], content[3][0], content[3][1], content[3][3]);
		Matrix3 m4 = new Matrix3(content[1][0], content[1][1], content[1][2], content[2][0], content[2][1],
				content[2][2], content[3][0], content[3][1], content[3][2]);
		return content[0][0] * m1.det() - content[0][1] * m2.det() + content[0][2] * m3.det()
				- content[0][3] * m4.det();
	}

	public Matrix4 transpose() {
		Matrix4 res = new Matrix4();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.content[j][i] = content[i][j];
			}
		}
		return res;
	}

	/**
	 * Adjugate of the Matrix.
	 * 
	 * @return the adjugate matrix of this matrix.
	 */
	public Matrix4 adj() {
		Matrix3 m1 = new Matrix3(content[1][1], content[1][2], content[1][3], content[2][1], content[2][2],
				content[2][3], content[3][1], content[3][2], content[3][3]);

		Matrix3 m2 = new Matrix3(content[1][0], content[1][2], content[1][3], content[2][0], content[2][2],
				content[2][3], content[3][0], content[3][2], content[3][3]);

		Matrix3 m3 = new Matrix3(content[1][0], content[1][1], content[1][3], content[2][0], content[2][1],
				content[2][3], content[3][0], content[3][1], content[3][3]);

		Matrix3 m4 = new Matrix3(content[1][0], content[1][1], content[1][2], content[2][0], content[2][1],
				content[2][2], content[3][0], content[3][1], content[3][2]);

		Matrix3 m5 = new Matrix3(content[0][1], content[0][2], content[0][3], content[2][1], content[2][2],
				content[2][3], content[3][1], content[3][2], content[3][3]);

		Matrix3 m6 = new Matrix3(content[0][0], content[0][2], content[0][3], content[2][0], content[2][2],
				content[2][3], content[3][0], content[3][2], content[3][3]);

		Matrix3 m7 = new Matrix3(content[0][0], content[0][1], content[0][3], content[2][0], content[2][1],
				content[2][3], content[3][0], content[3][1], content[3][3]);

		Matrix3 m8 = new Matrix3(content[0][0], content[0][1], content[0][2], content[2][0], content[2][1],
				content[2][2], content[3][0], content[3][1], content[3][2]);

		Matrix3 m9 = new Matrix3(content[0][1], content[0][2], content[0][3], content[1][1], content[1][2],
				content[1][3], content[3][1], content[3][2], content[3][3]);

		Matrix3 m10 = new Matrix3(content[0][0], content[0][2], content[0][3], content[1][0], content[1][2],
				content[1][3], content[3][0], content[3][2], content[3][3]);

		Matrix3 m11 = new Matrix3(content[0][0], content[0][1], content[0][3], content[1][0], content[1][1],
				content[1][3], content[3][0], content[3][1], content[3][3]);

		Matrix3 m12 = new Matrix3(content[0][0], content[0][1], content[0][2], content[1][0], content[1][1],
				content[1][2], content[3][0], content[3][1], content[3][2]);

		Matrix3 m13 = new Matrix3(content[0][1], content[0][2], content[0][3], content[1][1], content[1][2],
				content[1][3], content[2][1], content[2][2], content[2][3]);

		Matrix3 m14 = new Matrix3(content[0][0], content[0][2], content[0][3], content[1][0], content[1][2],
				content[1][3], content[2][0], content[2][2], content[2][3]);

		Matrix3 m15 = new Matrix3(content[0][0], content[0][1], content[0][3], content[1][0], content[1][1],
				content[1][3], content[2][0], content[2][1], content[2][3]);

		Matrix3 m16 = new Matrix3(content[0][0], content[0][1], content[0][2], content[1][0], content[1][1],
				content[1][2], content[2][0], content[2][1], content[2][2]);

		return new Matrix4(m1.det(), -m5.det(), m9.det(), -m13.det(), -m2.det(), m6.det(), -m10.det(), m14.det(),
				m3.det(), -m7.det(), m11.det(), -m15.det(), -m4.det(), m8.det(), -m12.det(), m16.det());
	}

	public Matrix4 inv() {
		double factor = 1 / det();
		return adj().mul(factor);
	}

	public double get(int r, int c) {
		return content[r][c];
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Matrix4)) {
			return false;
		}
		Matrix4 m = (Matrix4) o;
		return Arrays.deepEquals(content,m.content);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(content);
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				stb.append(content[i][j]);
				stb.append(" ");
			}
			stb.append("\n");
		}
		return stb.toString();
	}
}

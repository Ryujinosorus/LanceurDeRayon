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
 * A 3x3 square matrix.
 * 
 * @author leberre
 *
 */
public class Matrix3 {

	private double[][] content = new double[3][3];

	public Matrix3() {
		this(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
	}

	public Matrix3(double d) {
		this(d, 0.0, 0.0, 0.0, d, 0.0, 0.0, 0.0, d);
	}

	public Matrix3(double... ds) {
		if (ds.length != 9) {
			throw new IllegalArgumentException("Building a 3x3 matrix requires 9 values");
		}
		content = new double[][] { { ds[0], ds[1], ds[2] }, { ds[3], ds[4], ds[5] }, { ds[6], ds[7], ds[8] } };
	}

	public Matrix3 add(Matrix3 m) {
		Matrix3 res = new Matrix3();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				res.content[i][j] = content[i][j] + m.content[i][j];
			}
		}
		return res;
	}

	public Matrix3 transpose() {
		Matrix3 res = new Matrix3();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				res.content[j][i] = content[i][j];
			}
		}
		return res;
	}

	/**
	 * Calcule le déterminant de la matrice.
	 * 
	 * @return the matrix determinant
	 */
	public double det() {
		double sum = 0.0;
		for (int i = 0; i < 3; i++) {
			sum += content[i][0] * content[(i + 1) % 3][1] * content[(i + 2) % 3][2];
		}
		for (int i = 0; i < 3; i++) {
			sum -= content[(i + 2) % 3][0] * content[(i + 1) % 3][1] * content[i][2];
		}
		return sum;
	}

	/**
	 * Calcule l'inverse de la matrice
	 * 
	 * @return the inverse of the matrix
	 */
	public Matrix3 inv() {
		double f = 1 / det();
		return new Matrix3((content[1][1] * content[2][2] - content[2][1] * content[1][2]) / f,
				-(content[0][1] * content[2][2] - content[2][1] * content[0][2]) / f,
				(content[0][1] * content[1][2] - content[1][1] * content[0][2]) / f,
				-(content[1][0] * content[2][2] - content[2][0] * content[1][2]) / f,
				(content[0][0] * content[2][2] - content[2][0] * content[0][2]) / f,
				-(content[0][0] * content[1][2] - content[1][0] * content[0][2]) / f,
				(content[1][0] * content[2][1] - content[2][0] * content[1][1]) / f,
				-(content[0][0] * content[2][1] - content[2][0] * content[0][1]) / f,
				(content[0][0] * content[1][1] - content[1][0] * content[0][1]) / f);
	}

	public double get(int r, int c) {
		return content[r][c];
	}

	public Matrix3 mul(Matrix3 m) {
		Matrix3 res = new Matrix3();
		double sum;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sum = 0;
				for (int k = 0; k < 3; k++) {
					sum += content[i][k] * m.content[k][j];
				}
				res.content[i][j] = sum;
			}
		}
		return res;
	}

	public <T extends Triplet> T mul(T v) {
		return (T) v.clone(v.getX() * content[0][0] + v.getY() * content[0][1] + v.getZ() * content[0][2],
				v.getX() * content[1][0] + v.getY() * content[1][1] + v.getZ() * content[1][2],
				v.getX() * content[2][0] + v.getY() * content[2][1] + v.getZ() * content[2][2]);
	}

	public Matrix3 mul(double d) {
		Matrix3 res = new Matrix3();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				res.content[i][j] = content[i][j] * d;
			}
		}
		return res;
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				stb.append(content[i][j]);
				stb.append(" ");
			}
			stb.append("\n");
		}
		return stb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Matrix3)) {
			return false;
		}
		Matrix3 m = (Matrix3) o;
		return Arrays.deepEquals(content,m.content);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(content);
	}
}
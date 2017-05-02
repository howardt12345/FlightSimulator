package java3dpipeline;
/** The Matrix class.*/
public class Matrix {
	/** The Matrix.*/
	private double[][] matrix = 
		{
			{1, 0, 0, 0},
			{0, 1, 0, 0}, 
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		};
	/** New Matrix. Default 4x4 Matrix.*/
	public Matrix () {}
	/** New transformation matrix from Transform.
	 * @param t the Transform.
	 */
	public Matrix (Transform t) 
	{
		Translate (t.getPosition());
		RotateXYZ (t.getRotation());
	}
	/** New Matrix from 2D array.*/
	public Matrix (double[][] matrix) 
	{
		this.setMatrix(matrix);
	}
	/** New Translation Matrix from Transformation.
	 * @param t the Transformation.
	 */
	public Matrix (Transformation t) 
	{
		if (t instanceof Vec4) 
			Translate ((Vec4) t);
		else if (t instanceof Rotation) 
			RotateXYZ ((Rotation) t);
		else if (t instanceof Scale) 
			Scale ((Scale) t);
	}
	/** New Matrix from rows and columns.
	 * @param rows the number of rows.
	 * @param columns the number of columns.
	 */
	public Matrix (int rows, int columns) 
	{
		setMatrix(new double[rows][columns]);
		for (int a = 0; a < rows; a++) 
		{
			for (int b = 0; b < columns; b++) 
				getMatrix()[a][b] = a == b ? 1 : 0;
		}
	}
	/** Identity Matrix. */
	public static Matrix identity () 
	{
		Matrix m = new Matrix ();
		for (int a = 0; a < m.getRow(); a++) 
		{
			for (int b = 0; b < m.getColumn(); b++) 
				m.set (a == b ? 1 : 0, a, b);
		}
		return m;
	}
	/** Identity Matrix from rows and columns.
	 * @param rows
	 * @param columns
	 */
	public static Matrix identity (int rows, int columns) 
	{
		Matrix m = new Matrix (rows, columns);
		for (int a = 0; a < m.getRow(); a++) 
		{
			for (int b = 0; b < m.getColumn(); b++) 
				m.set (a == b ? 1 : 0, a, b);
		}
		return m;
	}
	/** Overrides values of Matrix with identity Matrix.*/
	public void toIdentity () 
	{
		for (int a = 0; a < getRow(); a++) 
		{
			for (int b = 0; b < getColumn(); b++) 
				set (a == b ? 1 : 0, a, b);
		}
	}
	/** Matrix with all values at 0.*/
	public static Matrix zero () 
	{
		Matrix m = new Matrix ();
		for (int a = 0; a < m.getRow(); a++) {
			for (int b = 0; b < m.getColumn(); b++) 
				m.getMatrix()[a][b] = 0;
		}
		return m;
	}
	/** Matrix with all values at 0 from rows and columns.
	 * @param rows
	 * @param columns
	 */
	public static Matrix zero (int rows, int columns) 
	{
		Matrix m = new Matrix (rows, columns);
		m.setMatrix(new double[rows][columns]);
		for (int a = 0; a < rows; a++) 
		{
			for (int b = 0; b < columns; b++) 
				m.getMatrix()[a][b] = 0;
		}
		return m;
	}
	/** Overrides values of Matrix with Matrix with all values at 0.*/
	public void toZero () 
	{
		for (int a = 0; a < getRow(); a++) 
		{
			for (int b = 0; b < getColumn(); b++) 
				getMatrix()[a][b] = 0;
		}
	}
	/** Applies translation to matrix.
	 * @param v the Vec4 to translate by.
	 */
	private void Translate (Vec4 v) 
	{
		set(v.getX(), 0, 3);
		set(v.getY(), 1, 3);
		set(v.getZ(), 2, 3);
		set(v.getW(), 3, 3);
	}
	/** Returns a Matrix with translation applied.
	 * @param v the Vec4 to translate by.
	 */
	public Matrix translateMatrix (Vec4 v) 
	{
		Matrix m = new Matrix ();
		m.set(v.getX(), 0, 3);
		m.set(v.getY(), 1, 3);
		m.set(v.getZ(), 2, 3);
		m.set(v.getW(), 3, 3);
		return m;
	}
	/** Applies XYZ rotation to Matrix.
	 * @param r the Rotation.
	 */
	private void RotateXYZ (Rotation r) 
	{
		set(Math.cos(r.getRadianY())*Math.cos(r.getRadianZ()), 0, 0);
		set(Math.cos(r.getRadianZ())*Math.sin(r.getRadianX())*Math.sin(r.getRadianY())
				- Math.cos(r.getRadianX())*Math.sin(r.getRadianZ()), 0, 1);
		set(Math.cos(r.getRadianX())*Math.cos(r.getRadianZ())*Math.sin(r.getRadianY())
				+ Math.sin(r.getRadianX())*Math.sin(r.getRadianZ()), 0, 2);
		
		set(Math.cos(r.getRadianY())*Math.sin(r.getRadianZ()), 1, 0);
		set(Math.cos(r.getRadianX())*Math.cos(r.getRadianZ())
			+Math.sin(r.getRadianX())*Math.sin(r.getRadianY())*Math.sin(r.getRadianZ()), 1, 1);
		set(-Math.cos(r.getRadianZ())*Math.sin(r.getRadianX())
			+Math.cos(r.getRadianX())*Math.sin(r.getRadianY())*Math.sin(r.getRadianZ()), 1, 2);
		
		set(-Math.sin(r.getRadianY()), 2, 0);
		set(Math.cos(r.getRadianY())*Math.sin(r.getRadianX()), 2, 1);
		set(Math.cos(r.getRadianX())*Math.cos(r.getRadianY()), 2, 2);
	}
	/** Returns the XYZ Rotational matrix of the Rotation.
	 * @param r the Rotation.
	 */
	public static Matrix rotationXYZ (Rotation r) 
	{
		Matrix m = Matrix.multiply(Matrix.multiply(rotationX(r), rotationY(r)), rotationZ(r));
		return m;
	}
	/** Applies ZYX rotation to Matrix .
	 * @param r the Rotation.
	 */
	public void RotateZYX (Rotation r) 
	{
		set((Math.cos(r.getRadianY())*Math.cos(r.getRadianZ())), 0, 0);
		set((-Math.cos(r.getRadianY())*Math.sin(r.getRadianZ())), 0, 1);
		set((Math.sin(r.getRadianY())), 0, 2);
		
		set((Math.cos(r.getRadianX())*Math.sin(r.getRadianZ())
				+Math.sin(r.getRadianX())*Math.sin(r.getRadianY())
				*Math.cos(r.getRadianZ())), 1, 0);
		set((Math.cos(r.getRadianX())*Math.cos(r.getRadianZ())
				-Math.sin(r.getRadianX())*Math.sin(r.getRadianY())
				*Math.sin(r.getRadianZ())), 1, 1);
		set(-(Math.sin(r.getRadianX())*Math.cos(r.getRadianY())), 1, 2);
		
		set((Math.sin(r.getRadianX())*Math.sin(r.getRadianZ())
				-Math.cos(r.getRadianX())*Math.sin(r.getRadianY())
				*Math.cos(r.getRadianZ())), 2, 0);
		set((Math.sin(r.getRadianX())*Math.cos(r.getRadianZ())
				+Math.cos(r.getRadianX())*Math.sin(r.getRadianY())
				*Math.sin(r.getRadianZ())), 2, 1);
		set((Math.cos(r.getRadianX())*Math.cos(r.getRadianY())), 2, 2);
	}
	/** Returns the ZYX Rotational matrix of the Rotation.
	 * @param r the Rotation.
	 */
	public static Matrix rotationZYX (Rotation r) 
	{
		Matrix m = Matrix.multiply(Matrix.multiply(rotationZ(r), rotationY(r)), rotationX(r));
		return m;
	}
	/** Returns the Rotational Matrix on the X Axis of the Rotation.
	 * @param r the rotation.
	 */
	public static Matrix rotationX (Rotation r) 
	{
		Matrix m = new Matrix ();
		m.set(Math.cos(r.getRadianX()), 1, 1);
		m.set(-Math.sin(r.getRadianX()), 1, 2);
		m.set(Math.sin(r.getRadianX()), 2, 1);
		m.set(Math.cos(r.getRadianX()), 2, 2);
		return m;
	}
	/** Returns the Rotational Matrix on the Y Axis of the Rotation.
	 * @param r the rotation.
	 */
	public static Matrix rotationY (Rotation r) 
	{
		Matrix m = new Matrix ();
		m.set(Math.cos(r.getRadianY()), 0, 0);
		m.set(Math.sin(r.getRadianY()), 0, 2);
		m.set(-Math.sin(r.getRadianY()), 2, 0);
		m.set(Math.cos(r.getRadianY()), 2, 2);
		return m;
	}
	/** Returns the Rotational Matrix on the Z Axis of the Rotation.
	 * @param r the rotation.
	 */
	public static Matrix rotationZ (Rotation r) 
	{
		Matrix m = new Matrix ();
		m.set(Math.cos(r.getRadianZ()), 0, 0);
		m.set(-Math.sin(r.getRadianZ()), 0, 1);
		m.set(Math.sin(r.getRadianZ()), 1, 0);
		m.set(Math.cos(r.getRadianZ()), 1, 1);
		return m;
	}
	/** Returns the scaling matrix of the Scale.
	 * @param s the Scale.
	 */
	public Matrix scale (Scale s) 
	{
		Matrix m = new Matrix ();
		m.set (Math.abs(s.getX()), 0, 0);
		m.set (Math.abs(s.getY()), 1, 1);
		m.set (Math.abs(s.getZ()), 2, 2);
		return m;
	}
	/** Returns the scaling matrix of the Scale.
	 * @param s the Scale.
	 */
	private void Scale (Scale s) 
	{
		set (Math.abs(s.getX()), 0, 0);
		set (Math.abs(s.getY()), 1, 1);
		set (Math.abs(s.getZ()), 2, 2);
	}
	/** Multiplies a Vec4 by the Matrix.
	 * @param v the Vec4 to multiply.
	 * @return the multiplied Vec4.
	 */
	public Vec4 multiply (Vec4 v) 
	{
		return new Vec4 (
				get(0, 0)*v.getX() + get(0, 1)*v.getY() + get(0, 2)*v.getZ() + get(0, 3)*v.getW(), 
				get(1, 0)*v.getX() + get(1, 1)*v.getY() + get(1, 2)*v.getZ() + get(1, 3)*v.getW(), 
				get(2, 0)*v.getX() + get(2, 1)*v.getY() + get(2, 2)*v.getZ() + get(2, 3)*v.getW(),
				get(3, 0)*v.getX() + get(3, 1)*v.getY() + get(3, 2)*v.getZ() + get(3, 3)*v.getW()
			);
	}
	public void Multiply (Vec4 v)
	{
		v.setX(get(0, 0)*v.getX() + get(0, 1)*v.getY() + get(0, 2)*v.getZ() + get(0, 3)*v.getW()); 
		v.setY(get(1, 0)*v.getX() + get(1, 1)*v.getY() + get(1, 2)*v.getZ() + get(1, 3)*v.getW());
		v.setZ(get(2, 0)*v.getX() + get(2, 1)*v.getY() + get(2, 2)*v.getZ() + get(2, 3)*v.getW());
		v.setW(get(3, 0)*v.getX() + get(3, 1)*v.getY() + get(3, 2)*v.getZ() + get(3, 3)*v.getW());
	}
	/** Multiplies two Matrices together.
	 * @param m1 the first Matrix.
	 * @param m2 the second Matrix.
	 */
	public static Matrix multiply (Matrix m1, Matrix m2) 
	{
		if (m1.getColumn() != m2.getRow()) 
			return null;
		else 
		{
			Matrix m = new Matrix (m1.getRow(), m2.getColumn());
			for (int a = 0; a < m1.getRow(); a++) 
			{
				for (int b = 0; b < m2.getColumn(); b++) 
				{
					double sum = 0;
					for (int c = 0; c < m2.getRow(); c++) 
						sum += m1.get(a, c)*m2.get(c, b);
					m.set(sum, a, b);
					sum = 0;
				}
			}
			return m;
		}
	}
	/** Returns the product of current Matrix and another Matrix.
	 * @param m1 the Matrix.
	 */
	public Matrix multiply (Matrix m1) 
	{
		if (getColumn() != m1.getRow()) 
			return null;
		else 
		{
			Matrix m = new Matrix (getRow(), m1.getColumn());
			for (int a = 0; a < getRow(); a++) 
			{
				for (int b = 0; b < m1.getColumn(); b++) 
				{
					double sum = 0;
					for (int c = 0; c < m1.getRow(); c++) 
						sum += get(a, c)*m1.get(c, b);
					m.set(sum, a, b);
					sum = 0;
				}
			}
			return m;
		}
	}
	/** Multiplies current Matrix by another Matrix.
	 * @param m1 the Matrix.
	 */
	public void Multiply (Matrix m1) 
	{
		if (getColumn() != m1.getRow()) return;
		else 
		{
			for (int a = 0; a < getRow(); a++) 
			{
				for (int b = 0; b < m1.getColumn(); b++) 
				{
					double sum = 0;
					for (int c = 0; c < m1.getRow(); c++) 
						sum += get(a, c)*m1.get(c, b);
					set(sum, a, b);
					sum = 0;
				}
			}
		}
	}
	/** Returns true if the 2 Matrices are equal.
	 * @param m the Matrix to compare.
	 */
	public boolean equals (Matrix m) 
	{
		for (int a = 0; a < m.getRow(); a++) 
		{
			for (int b = 0; b < m.getColumn(); b++) 
				if (Double.compare(getMatrix()[a][b], m.getMatrix()[a][b]) != 0) 
					return false;
		}
		return true;
	}
	/** Sets the Matrix at the provided index.
	 * @param value the value to set.
	 * @param row the row index.
	 * @param column the column index.
	 */
	public void set (double value, int row, int column) 
	{
		getMatrix()[row][column] = value;
	}
	/** Gets the Matrix.
	 * @return the matrix.
	 */
	public double[][] getMatrix()
	{
		return matrix;
	}
	/** Sets the Matrix.
	 * @param matrix the matrix to set.
	 */
	public void setMatrix(double[][] matrix) 
	{
		this.matrix = matrix;
	}
	/** Gets the Matrix value at the provided index.
	 * @param row the row index.
	 * @param column the column index.
	 * @return the Matrix value.
	 */
	public double get (int row, int column) 
	{
		return getMatrix()[row][column];
	}
	/** Returns the length of the rows in the Matrix.*/
	public int getRow () 
	{
		return getMatrix().length;
	}
	/** Returns the length of the columns in the Matrix.*/
	public int getColumn () 
	{
		return getMatrix()[0].length;
	}
	/** Prints the Matrix.*/
	public void print () 
	{
		Utils.print(getMatrix());
	}
}
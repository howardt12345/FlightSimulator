package java3dpipeline;
import java.io.*;
/** The Transformation class, used as a superclass for transformations. 
 * Implements Serializable. */
@SuppressWarnings("serial")
public class Transformation implements Serializable {
	/** Internal values.*/
	protected double X, Y, Z;
	/** Creates a new Transformation with a X value, Y value, and Z value.
	 * @param x the X value.
	 * @param y the Y value.
	 * @param z the Z value.
	 */
	public Transformation (double x, double y, double z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	/** Creates a new Transformation from a value.
	 * @param a the value.
	 */
	public Transformation (double a)
	{
		X = Y = Z = a;
	}
	/** Creates a new Transformation.*/
	public Transformation () 
	{
		X = Y = Z = 0;
	}
	/** Returns the X value of the Transformation.*/
	public double getX () 
	{
		return X;
	}
	/** Returns the Y value of the Transformation.*/
	public double getY () 
	{
		return Y;
	}
	/** Returns the Z value of the Transformation.*/
	public double getZ () 
	{
		return Z;
	}
	/** Set x, y and z values of the Transformation.
	 * @param new_x new X value.
	 * @param new_y new Y value.
	 * @param new_z new Z value.
	 */
	public void Set (double new_x, double new_y, double new_z) 
	{
		X = new_x;
		Y = new_y;
		Z = new_z;
	}
	/** Sets the X value of the Transformation.
	 * @param new_X new X value.
	 */
	public void setX (double new_X) 
	{
		X = new_X;
	}
	/** Sets the Y value of the Transformation.
	 * @param new_Y new Y value.
	 */
	public void setY (double new_Y) 
	{
		Y = new_Y;
	}
	/** Sets the Z value of the Transformation.
	 * @param new_Z new Z value.
	 */
	public void setZ (double new_Z) 
	{
		Z = new_Z;
	}
	/** Returns true if the Transformation is equal.
	 * @param t Transformation to compare to.
	 */
	public boolean equals (Transformation t) 
	{
		return Double.compare(X, t.X) == 0 && Double.compare(Y, t.Y) == 0 && Double.compare(Z, t.Z) == 0 ? true : false;
	}
	/** Prints out the values of the Transformation, structured as "X, Y, Z".*/
	public void print () 
	{
		System.out.println(X + ", " + Y + ", " + Z);
	}
	/** Gets the values of the Transformation as a String structured as "X, Y, Z"*/
	public String asString () 
	{
		return X + ", " + Y + ", " + Z;
	}
	/** Gets the values of the Transformation as a formatted String.
	 * Recommended format: "%1$f, %2$f, %3$f"
	 * @param format the formatting of the string.
	 */
	public String asString (String format) 
	{
		return String.format(format, X, Y, Z);
	}
}
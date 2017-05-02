package java3dpipeline;
import java.io.*;

@SuppressWarnings("serial")
/** The Rotation Class, extends Transformation and implements Serializable.*/
public class Rotation extends Transformation implements Serializable {
	/** Creates a new Rotation with a X value, Y value, and Z value.
	 * @param x the X value.
	 * @param y the Y value.
	 * @param z the Z value.
	 */
	public Rotation (double x, double y, double z) 
	{
		super (x, y, z);
	}
	/** New Rotation.*/
	public Rotation () 
	{
		super();
	}
	/** Returns the X value of the Rotation in Radians.*/
	public double getRadianX () 
	{
		return Math.toRadians(X);
	}
	/** Returns the Y value of the Rotation in Radians.*/
	public double getRadianY () 
	{
		return Math.toRadians(Y);
	}
	/** Returns the Z value of the Rotation in Radians.*/
	public double getRadianZ () 
	{
		return Math.toRadians(Z);
	}
	/** @param zero Shorthand for writing Rotation (0,0,0)*/
	public static Rotation zero = new Rotation (0, 0, 0);
}
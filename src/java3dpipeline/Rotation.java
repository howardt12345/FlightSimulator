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
	/** Adds two Rotations.
	 * @param t1 Rotation #1.
	 * @param t2 Rotation #2*/
	public static Rotation add (Rotation t1, Rotation t2) 
	{
		return new Rotation (t1.X + t2.X, t1.Y + t2.Y, t1.Z + t2.Z);
	}
	/** Subtract two Rotations.
	 * @param t1 Rotation #1.
	 * @param t2 Rotation #2*/
	public static Rotation subtract (Rotation t1, Rotation t2) 
	{
		return new Rotation (t1.X - t2.X, t1.Y - t2.Y, t1.Z - t2.Z);
	}
	/** Multiplies two Rotations.
	 * @param t1 Rotation #1.
	 * @param t2 Rotation #2*/
	public static Rotation multiply (Rotation t1, Rotation t2) 
	{
		return new Rotation (t1.X * t2.X, t1.Y * t2.Y, t1.Z * t2.Z);
	}
	/** @param zero Shorthand for writing Rotation (0,0,0)*/
	public static Rotation zero = new Rotation (0, 0, 0);
}
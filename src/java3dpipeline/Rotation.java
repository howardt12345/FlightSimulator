package java3dpipeline;

@SuppressWarnings("serial")
/** The Rotation Class, extends Transformation.*/
public class Rotation extends Transformation {
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
	 * @param r1 Rotation #1.
	 * @param r2 Rotation #2*/
	public static Rotation add (Rotation r1, Rotation r2) 
	{
		return new Rotation (r1.X + r2.X, r1.Y + r2.Y, r1.Z + r2.Z);
	}
	/** Subtract two Rotations.
	 * @param r1 Rotation #1.
	 * @param r2 Rotation #2*/
	public static Rotation subtract (Rotation r1, Rotation r2) 
	{
		return new Rotation (r1.X - r2.X, r1.Y - r2.Y, r1.Z - r2.Z);
	}
	/** Multiplies two Rotations.
	 * @param r1 Rotation #1.
	 * @param r2 Rotation #2*/
	public static Rotation multiply (Rotation r1, Rotation r2) 
	{
		return new Rotation (r1.X * r2.X, r1.Y * r2.Y, r1.Z * r2.Z);
	}
	/** @param zero Shorthand for writing Rotation (0,0,0)*/
	public static Rotation zero = new Rotation (0, 0, 0);
}
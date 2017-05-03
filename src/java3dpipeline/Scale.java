package java3dpipeline;
import java.io.*;

@SuppressWarnings("serial")
/** The Scale class, extends Transformation and implements Serializable.*/
public class Scale extends Transformation implements Serializable {
	/** Creates a new Scale from 3 scale values.
	 * @param x The scale along the X axis.
	 * @param y The scale along the Y axis.
	 * @param z The scale along the Z axis.
	 */
	public Scale (double x, double y, double z) 
	{
		super (x, y, z);
	}
	/** Creates a new Scale from a scalar.
	 * @param scalar the scalar.
	 */
	public Scale (double scalar) 
	{
		super(scalar);
	}
	/** Creates a new Scale.*/
	public Scale () 
	{
		super (1);
	}
	/** Gets the average of the 3 scale factors.*/
	public double getAverage () 
	{
		return (X + Y + Z)/3;
	}
	/** Adds two Scales.
	 * @param t1 Scale #1.
	 * @param t2 Scale #2*/
	public static Scale add (Scale t1, Scale t2) 
	{
		return new Scale (t1.X + t2.X, t1.Y + t2.Y, t1.Z + t2.Z);
	}
	/** Subtract two Scales.
	 * @param t1 Scale #1.
	 * @param t2 Scale #2*/
	public static Scale subtract (Scale t1, Scale t2) 
	{
		return new Scale (t1.X - t2.X, t1.Y - t2.Y, t1.Z - t2.Z);
	}
	/** Multiplies two Scales.
	 * @param t1 Scale #1.
	 * @param t2 Scale #2*/
	public static Scale multiply (Scale t1, Scale t2) 
	{
		return new Scale (t1.X * t2.X, t1.Y * t2.Y, t1.Z * t2.Z);
	}
}
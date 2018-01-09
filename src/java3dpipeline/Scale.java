package java3dpipeline;

@SuppressWarnings("serial")
/** The Scale class, extends Transformation.*/
public class Scale extends Transformation {
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
	 * @param s1 Scale #1.
	 * @param s2 Scale #2*/
	public static Scale add (Scale s1, Scale s2) 
	{
		return new Scale (s1.X + s2.X, s1.Y + s2.Y, s1.Z + s2.Z);
	}
	/** Subtract two Scales.
	 * @param s1 Scale #1.
	 * @param s2 Scale #2*/
	public static Scale subtract (Scale s1, Scale s2) 
	{
		return new Scale (s1.X - s2.X, s1.Y - s2.Y, s1.Z - s2.Z);
	}
	/** Multiplies two Scales.
	 * @param s1 Scale #1.
	 * @param s2 Scale #2*/
	public static Scale multiply (Scale s1, Scale s2) 
	{
		return new Scale (s1.X * s2.X, s1.Y * s2.Y, s1.Z * s2.Z);
	}
}
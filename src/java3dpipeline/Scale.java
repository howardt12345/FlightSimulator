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
}
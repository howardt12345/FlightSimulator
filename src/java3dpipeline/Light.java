package java3dpipeline;
import java.io.*;

@SuppressWarnings("serial")
/** The Light class, extends GameObject and implements Serializable.*/
public class Light extends GameObject implements Serializable {
	/** The light intensity. Default value 1.*/
	private double intensity = 1;
	/** The light range. Default value 10.*/
	private double range = 10;
	/** New Light.*/
	public Light () 
	{
		super ();
	}
	/** New Light from Transform.
	 * @param t the Transform.
	 */
	public Light (Transform t) 
	{
		super (t);
	}
	/** New Light from intensity.
	 * @param intensity the intensity.
	 */
	public Light (double intensity) 
	{
		this.intensity = intensity;
	}
	/** New Light from intensity and range.
	 * @param intensity the intensity.
	 * @param range the range.
	 */
	public Light (double intensity, double range) 
	{
		this.intensity = intensity;
		this.range = range;
	}
	/** New Light from Transform, intensity, and range.
	 * @param t the Transform.
	 * @param intensity the intensity.
	 * @param range the range.
	 */
	public Light (Transform t, double intensity, double range) 
	{
		super (t);
		this.intensity = intensity;
		this.range = range;
	}
	/** Calculates the diffuse reflection of the light on the Polygon.
	 * @param p the Polygon.
	 * @return the diffuse intensity.
	 */
	public double diffuse (Polygon p) 
	{
		return (range/Vec4.getDistance(transform.getPosition(), p.getCenter()))*intensity
				*(Vec4.dot(Vec4.subtract(transform.getPosition(), p.getCenter()).normalized(), p.getNormal().normalized()));
	}
	/** Gets the light intensity.
	 * @return the intensity.
	 */
	public double getIntensity () 
	{
		return intensity;
	}
	/** Sets the light intensity.
	 * @param intensity the intensity to set.
	 */
	public void setIntensity (double intensity) 
	{
		this.intensity = intensity;
	}
	/** Gets the light range.
	 * @return the range.
	 */
	public double getRange () 
	{
		return range;
	}
	/** Sets the light range.
	 * @param range the range to set.
	 */
	public void setRange (double range) 
	{
		this.range = range;
	}
}
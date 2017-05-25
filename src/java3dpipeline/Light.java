package java3dpipeline;
import java.io.*;

@SuppressWarnings("serial")
/** The Light class, extends GameObject and implements Serializable.*/ 
class Light extends GameObject implements Serializable {
	/** The light intensity. Default value 1.*/
	protected double intensity = 1;
	/** The light range. Default value 10.*/
	protected double range = 10;
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
		super (t, true);
	}
	/** New Light from intensity.
	 * @param intensity the intensity.
	 */
	public Light (double intensity) 
	{
		this.intensity = intensity;
	}
	/** New Light from Transform and intensity.
	 * @param t the Transform.
	 * @param intensity the intensity.
	 */
	public Light (Transform t, double intensity) 
	{
		super (t, true);
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
		super (t, true);
		this.intensity = intensity;
		this.range = range;
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
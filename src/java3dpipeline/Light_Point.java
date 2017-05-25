package java3dpipeline;

@SuppressWarnings("serial")
public class Light_Point extends Light {
	/** New Light_Point.*/
	public Light_Point () 
	{
		super ();
	}
	/** New Light_Point from Transform.
	 * @param t the Transform.
	 */
	public Light_Point (Transform t) 
	{
		super (t);
	}
	/** New Light_Point from intensity.
	 * @param intensity the intensity.
	 */
	public Light_Point (double intensity) 
	{
		this.intensity = intensity;
	}
	/** New Light_Point from intensity and range.
	 * @param intensity the intensity.
	 * @param range the range.
	 */
	public Light_Point (double intensity, double range) 
	{
		this.intensity = intensity;
		this.range = range;
	}
	/** New Light_Point from Transform, intensity, and range.
	 * @param t the Transform.
	 * @param intensity the intensity.
	 * @param range the range.
	 */
	public Light_Point (Transform t, double intensity, double range) 
	{
		super (t);
		this.intensity = intensity;
		this.range = range;
	}
	/** Calculates the diffuse reflection of the light on the Polygon.
	 * @param p the Polygon.
	 * @return the diffuse intensity.
	 */
	public double calculate (Polygon p) 
	{
		return (range/Vec4.getDistance(getLocalTransform().getLocalPosition(), p.getCenter()))*intensity
				*(Vec4.dot(Vec4.subtract(getLocalTransform().getLocalPosition(), p.getCenter()).normalized(), p.getNormal().normalized()));
	}
}

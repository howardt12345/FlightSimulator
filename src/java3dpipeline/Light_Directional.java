package java3dpipeline;

@SuppressWarnings("serial")
/** The Light_Directional class, extends Light.*/
public class Light_Directional extends Light {
	/** The direction the Light_Directional is shining in.*/
	private Vec4 direction = new Vec4 (0, -1, 0);
	/** New Light_Directional.*/
	public Light_Directional ()
	{
		super ();
	}
	/** New Light_Directional from Vec4.
	 * @param v the Vec4.
	 */
	public Light_Directional (Vec4 v)
	{
		super ();
		direction = v.normalized();
	}
	/** New Light_Directional from Rotation.
	 * @param r the Rotation.
	 */
	public Light_Directional (Rotation r)
	{
		super (new Transform (new Vec4(0, 0, 1), r));
		direction = Vec4.Transform(new Vec4(), new Matrix (getGlobalRotation()));
	}
	/** New Light_Directional from Transform.
	 * @param t the Transform.
	 */
	public Light_Directional (Transform t)
	{
		super (t);
		direction = Vec4.Transform(new Vec4(0, 0, 1), new Matrix (getGlobalRotation()));
	}
	/** New Light_Directional from Vec4 and intensity.
	 * @param v the Vec4.
	 * @param intensity the intensity.
	 */
	public Light_Directional (Vec4 v, double intensity)
	{
		super (intensity);
		direction = v.normalized();
	}
	/** New Light_Directional from Rotation and intensity.
	 * @param r the Rotation.
	 * @param intensity the intensity.
	 */
	public Light_Directional (Rotation r, double intensity)
	{
		super (new Transform (new Vec4(), r), intensity);
		direction = Vec4.Transform(new Vec4(0, 0, 1), new Matrix (getGlobalRotation()));
	}
	/** Calculates the light intensity on the Polygon.
	 * @param p the Polygon to calculate.
	 * @return the light intensity on the Polygon.
	 */
	public double calculate (Polygon p) 
	{
		direction = Vec4.Transform(new Vec4(0, 0, 1), new Matrix (getGlobalRotation()));
		return -Vec4.dot(direction, p.getNormal().normalized())*intensity;
	}
	/** Sets the Direction of the Light_Directional.
	 * @param v the Vec4 to set.
	 */
	public void setDirection (Vec4 v) 
	{
		direction = v.Normalized();
	}
	/** Gets the direction of the Light_Directional.
	 * @return the direction.
	 */
	public Vec4 getDirection ()
	{
		return direction;
	}
}
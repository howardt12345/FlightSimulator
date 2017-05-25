package java3dpipeline;

@SuppressWarnings("serial")
public class Light_Directional extends Light {
	
	private Vec4 direction = new Vec4 (0, -1, 0);
	
	public Light_Directional ()
	{
		super ();
	}
	public Light_Directional (Vec4 v)
	{
		super ();
		direction = v.normalized();
	}
	public Light_Directional (Rotation r)
	{
		super (new Transform (new Vec4(0, 0, 1), r));
		direction = Vec4.Transform(new Vec4(), new Matrix (getGlobalRotation()));
	}
	public Light_Directional (Transform t)
	{
		super (t);
		direction = Vec4.Transform(new Vec4(0, 0, 1), new Matrix (getGlobalRotation()));
	}
	public Light_Directional (Vec4 v, double intensity)
	{
		super (intensity);
		direction = v.normalized();
	}
	public Light_Directional (Rotation r, double intensity)
	{
		super (new Transform (new Vec4(), r), intensity);
		direction = Vec4.Transform(new Vec4(0, 0, 1), new Matrix (getGlobalRotation()));
	}
	public double calculate (Polygon p) 
	{
		direction = Vec4.Transform(new Vec4(0, 0, 1), new Matrix (getGlobalRotation()));
		return -Vec4.dot(direction, p.getNormal().normalized())*intensity;
	}
	public void setDirection (Vec4 v) 
	{
		direction = v.Normalized();
	}
	public Vec4 getDirection ()
	{
		return direction;
	}
}
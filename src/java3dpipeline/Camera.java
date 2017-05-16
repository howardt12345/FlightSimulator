package java3dpipeline;
import java.io.*;

@SuppressWarnings("serial")
/** The Camera class, extends GameObject.*/
public class Camera extends GameObject implements Serializable {
	/** The lookFrom Vec4.*/
	private Vec4 lookFrom = Vec4.center;
	/** The lookAt Vec4.*/
	private Vec4 lookAt = new Vec4 (0, 0, 1);
	/** The lookUp Vec4.*/
	private Vec4 lookUp = new Vec4 (0, 1, 0, 1);
	/** Internal values of the Camera.*/
	private double nearClip = 0.1, farClip = 0.9, FOV = 60;
	/** lookAtMatrix to be used in calculations.*/
	private Matrix lookAtMatrix;
	/** Creaes a new Camera from a Transform, near clip value, far clip value, 
	 * FOV (Field of View) value, and aspect ratio value.
	 * @param t the Transform.
	 * @param nearClip the near clip.
	 * @param farClip the far clip.
	 * @param FOV the FOV (Field of View).
	 * @param aspectRatio the aspect ratio.
	 */
	public Camera (Transform t, double nearClip, double farClip, double FOV) 
	{
		super (t);
		this.nearClip = nearClip;
		this.farClip = farClip;
		this.FOV = FOV;
		lookAtMatrix = lookAtMatrix();
	}
	/** Creates a new Camera from a near clip value, far clip value, 
	 * FOV (Field of View) value, and aspect ratio value.
	 * @param nearClip the near clip.
	 * @param farClip the far clip.
	 * @param FOV the FOV (Field of View).
	 * @param aspectRatio the aspect ratio.
	 */
	public Camera (double nearClip, double farClip, double FOV, double aspectRatio) 
	{
		super (new Transform ());
		this.nearClip = nearClip;
		this.farClip = farClip;
		this.FOV = FOV;
		lookAtMatrix = lookAtMatrix();
	}
	/** Creates a new Camera from a Transform/
	 * @param t the Transform.
	 */
	public Camera (Transform t) 
	{
		super (t);
		this.lookFrom = t.getLocalPosition();
		lookAt = Vec4.Transform(new Vec4 (0, 0, 1), new Matrix (t));
		lookUp = Vec4.Transform(new Vec4 (0, 1, 0), new Matrix (t.getLocalRotation()));
		lookAtMatrix = lookAtMatrix();
	}
	/** Creates a new Camera.*/
	public Camera () 
	{
		super (new Transform());
		lookAtMatrix = lookAtMatrix();
	}
	private Matrix lookAtMatrix ()
	{
		Vec4 Vz = Vec4.subtract (lookFrom, lookAt).normalized(),
				Vx = Vec4.cross(lookUp, Vz).normalized(),
				Vy = Vec4.cross(Vz, Vx).normalized();
		
		Matrix viewMatrix = new Matrix();
		
		viewMatrix.set(Vx.getX(), 0, 0);
		viewMatrix.set(Vx.getY(), 0, 1);
		viewMatrix.set(Vx.getZ(), 0, 2);

		viewMatrix.set(Vy.getX(), 1, 0);
		viewMatrix.set(Vy.getY(), 1, 1);
		viewMatrix.set(Vy.getZ(), 1, 2);

		viewMatrix.set(Vz.getX(), 2, 0);
		viewMatrix.set(Vz.getY(), 2, 1);
		viewMatrix.set(Vz.getZ(), 2, 2);
		
		viewMatrix.set(-Vec4.dot(lookFrom, Vx), 0, 3);
		viewMatrix.set(-Vec4.dot(lookFrom, Vy), 1, 3);
		viewMatrix.set(-Vec4.dot(lookFrom, Vz), 2, 3);
		
		return viewMatrix;	
	}
	/** The lookAt Matrix.
	 * @return the lookAt Matrix.
	 */
	public Matrix getLookAtMatrix () 
	{
		return lookAtMatrix;
	}
	/** The Camera Perspective projection Matrix.
	 * @param width the width.
	 * @param height the height.
	 * @return the projection matrix.
	 */
    public Matrix getPerspectiveMatrix (double width, double height) 
    {
        Matrix projectionMatrix = new Matrix ();
           
        projectionMatrix.set(1/Math.tan(Math.toRadians(FOV/2)), 0, 0);
        
        projectionMatrix.set(1/(Math.tan(Math.toRadians(FOV/2))*Math.abs(height/width)), 1, 1);
        
        projectionMatrix.set(farClip/farClip-nearClip, 2, 2);
        projectionMatrix.set(-1, 3, 2);
        
        projectionMatrix.set(nearClip*farClip/farClip-nearClip, 2, 3);
        
        return projectionMatrix;
    }
	/** Sets the Transform.
	 * @param transform the transform to set.
	 */
	public void setTransform (Transform transform) 
	{
		super.setTransform(transform);
		Transform ();
	}
	/** Transforms the Camera by the Transform.*/
	protected void Transform () 
	{
		lookFrom = Vec4.Transform(new Vec4 (0, 0, 0), getGlobalTransformationMatrix());
		//lookAt = Vec4.Transform(new Vec4 (0, 0, 1), Matrix.multiply(new Matrix (getGlobalTransformedPosition()), new Matrix (new Rotation (getGlobalRotation().getX(), getGlobalRotation().getY(), 0))));
		//lookUp = Vec4.cross(Vec4.subtract(lookAt, lookFrom), Vec4.Transform(new Vec4 (1, 0, 0), Matrix.rotationXYZ(new Rotation (0, getGlobalRotation().getY(), getGlobalRotation().getZ()))));
		lookAt = Vec4.Transform(new Vec4 (0, 0, 1), getGlobalTransformationMatrix());
		lookUp = Vec4.Transform(new Vec4 (0, 1, 0), new Matrix (Rotation.multiply(getGlobalRotation(), new Rotation (-1, -1, -1))));
		lookAtMatrix = lookAtMatrix();
	}
	/** Checks whether or not the Polygon is visible to the Camera.
	 * @param p the Polygon.
	 */
	public boolean isVisible (Polygon p) 
	{
		Vec4 view = Vec4.subtract(lookFrom, p.getClosest(lookFrom));
		return Vec4.dot(view, p.getNormal()) > 0;
	}
	/** Adds an amount to the specified Axis. 
	 * @param amount the amount.
	 * @param axis the Axis.
	 */
	public void addTranslate (double amount, Axis axis) 
	{
		super.addTranslate(amount, axis);
		Transform ();
	}
	/** Sets a value to the specified Axis. 
	 * @param value the value.
	 * @param axis the Axis.
	 */
	public void setTranslate (double amount, Axis axis) 
	{
		super.setTranslate(amount, axis);
		Transform ();
	}
	/** Adds an amount to the Rotation.
	 * @param amount the amount.
	 * @param axis the Axis.
	 */
	public void addRotate (double amount, Axis axis) 
	{
		super.addRotate(amount, axis);
		Transform ();
	}
	/** Sets a value to the Rotation.
	 * @param value the value.
	 * @param axis the Axis.
	 */
	public void setRotate (double value, Axis axis) 
	{
		super.setRotate(value, axis);
		Transform ();
	}
	/** Adds an amount to the specified Direction. This is different from
	 *  GameObject.addTranslate(), as it moves the camera in a first person like movement.
	 * @param amount the amount.
	 * @param dir the Direction.
	 */
	public void addTranslate (double amount, Direction dir) 
	{
		switch (dir) 
		{
		case Forward:
			getLocalTransform().setPosX(getLocalTransform().getPosX()+(Math.sin(getLocalTransform().getLocalRotation().getRadianY()) * 
							Math.cos(getLocalTransform().getLocalRotation().getRadianX()) * amount));
			getLocalTransform().setPosY(getLocalTransform().getPosY()+(-Math.sin(getLocalTransform().getLocalRotation().getRadianX()) * amount));
			getLocalTransform().setPosZ(getLocalTransform().getPosZ()+(Math.cos(getLocalTransform().getLocalRotation().getRadianY())
					* Math.cos(getLocalTransform().getLocalRotation().getRadianX()) * amount));
			break;
		case Backward:
			getLocalTransform().setPosX(getLocalTransform().getPosX()-(Math.sin(getLocalTransform().getLocalRotation().getRadianY()) * 
					Math.cos(getLocalTransform().getLocalRotation().getRadianX()) * amount));
			getLocalTransform().setPosY(getLocalTransform().getPosY()-(-Math.sin(getLocalTransform().getLocalRotation().getRadianX()) * amount));
			getLocalTransform().setPosZ(getLocalTransform().getPosZ()-(Math.cos(getLocalTransform().getLocalRotation().getRadianY())
					* Math.cos(getLocalTransform().getLocalRotation().getRadianX()) * amount));			
			break;
		case Left:
			getLocalTransform().setPosX(getLocalTransform().getPosX()-Math.cos(getLocalTransform().getLocalRotation().getRadianY()) * amount);
			getLocalTransform().setPosZ(getLocalTransform().getPosZ()+Math.sin(getLocalTransform().getLocalRotation().getRadianY()) * amount);
			break;
		case Right:
			getLocalTransform().setPosX(getLocalTransform().getPosX()+Math.cos(getLocalTransform().getLocalRotation().getRadianY()) * amount);
			getLocalTransform().setPosZ(getLocalTransform().getPosZ()-Math.sin(getLocalTransform().getLocalRotation().getRadianY()) * amount);
			break;
		case Up:
			getLocalTransform().setPosX(getLocalTransform().getPosX()+Math.sin(getLocalTransform().getLocalRotation().getRadianY())
					* Math.sin(getLocalTransform().getLocalRotation().getRadianX()) * amount);
			getLocalTransform().setPosY(getLocalTransform().getPosY()+Math.cos(getLocalTransform().getLocalRotation().getRadianX()) * amount);
			getLocalTransform().setPosZ(getLocalTransform().getPosZ()+Math.cos(getLocalTransform().getLocalRotation().getRadianY())
					* Math.sin(getLocalTransform().getLocalRotation().getRadianX()) * amount);
			break;
		case Down:
			getLocalTransform().setPosX(getLocalTransform().getPosX()-Math.sin(getLocalTransform().getLocalRotation().getRadianY())
					* Math.sin(getLocalTransform().getLocalRotation().getRadianX()) * amount);
			getLocalTransform().setPosY(getLocalTransform().getPosY()-Math.cos(getLocalTransform().getLocalRotation().getRadianX()) * amount);
			getLocalTransform().setPosZ(getLocalTransform().getPosZ()-Math.cos(getLocalTransform().getLocalRotation().getRadianY())
					* Math.sin(getLocalTransform().getLocalRotation().getRadianX()) * amount);
			break;
		}
		Transform ();
	}
	/** Sets a value to the specified Direction. 
	 * @param value the value.
	 * @param dir the Direction.
	 */
	public void setTranslate (double value, Direction dir) 
	{
		super.setTranslate(value, dir);
		Transform ();
	}
	/** Adds an amount to the specified Direction. 
	 * @param amount the amount.
	 * @param dir the Direction.
	 */
	public void addRotate (double amount, Direction dir) 
	{
		super.addRotate(amount, dir);
		Transform ();
	}
	/** Sets a value to the specified Direction. 
	 * @param value the value.
	 * @param dir the Direction.
	 */
	public void setRotate (double value, Direction dir) 
	{
		super.setRotate(value, dir);
		Transform ();
	}
	/** Gets the LookAt Vec4.*/
	public Vec4 getLookAt () 
	{
		return lookAt;
	}
	/** Sets the LookAt Vec4.
	 * @param lookAt the lookAt Vec4 to set.
	 */
	public void setLookAt (Vec4 lookAt) 
	{
		this.lookAt = lookAt;
	}
	/** Gets the LookFrom Vec4.*/
	public Vec4 getLookFrom () 
	{
		return lookFrom;
	}
	/** Sets the lookFrom Vec4.
	 * @param lookFrom the lookFrom Vec4 to set.
	 */
	public void setLookFrom (Vec4 lookFrom) 
	{
		this.lookFrom = lookFrom;
	}
	/** Gets the lookUp Vec4.*/
	public Vec4 getLookUp () 
	{
		return lookUp;
	}
	/** Sets the lookUp Vec4.
	 * @param lookUp the lookUp Vec4 to set.
	 */
	public void setLookUp (Vec4 lookUp) 
	{
		this.lookUp = lookUp;
	}
	/** Gets the near clip of the Camera.*/
	public double getNearClip () 
	{
		return nearClip;
	}
	/** Sets the near clip of the Camera.
	 * @param nearClip the near clip to set.
	 */
	public void setNearClip (double nearClip) 
	{
		this.nearClip = nearClip;
	}
	/** Gets the far clip of the Camera.*/
	public double getFarClip () 
	{
		return farClip;
	}
	/** Sets the far clip of the Camera.
	 * @param farClip the far clip to set.
	 */
	public void setFarClip (double farClip) 
	{
		this.farClip = farClip;
	}
	/** Gets the FOV of the Camera.*/
	public double getFOV () 
	{
		return FOV;
	}
	/** Sets the FOV of the Camera.
	 * @param fov the FOV to set.
	 */
	public void setFOV (double fov) 
	{
		FOV = fov >= 179 || fov <= 1 ? fov >= 179 ? 179 : 1 : fov; 
	}
}
package java3dpipeline;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
/** The GameObject Class, used as a Superclass for objects. Implements Serializable.*/
public class GameObject implements Serializable {
	/** This GameObject's parent GameObject.
	 * @param parent the parent GameObject.*/
	private GameObject parent = null;
	/** The Transform of the gameObject.
	 * @param transform the Transform.*/
	private Transform transform;
	/** Whether or not the Polyhedron is active.
	 * @param active the boolean.*/
	private boolean active = true;
	/** New gameObject from a Transform.
	 * @param t the Transform.
	 */
	public GameObject (Transform t) 
	{
		transform = t;
	}
	/** new gameObject.*/
	public GameObject () 
	{
		transform = new Transform ();
	}
	/** Gets the local Transform of the gameObject.
	 * @return the local transform.
	 */
	public Transform getLocalTransform () 
	{
		return transform;
	}
	/** Gets the global Transform of the GameObject.
	 * @return the global transform.
	 */
	public Transform getGlobalTransform ()
	{
		return new Transform (getGlobalTransformedPosition(), getGlobalRotation(), getGlobalScale());
	}
	/** Sets the Transform of the gameObject.
	 * @param transform the transform to set.
	 */
	public void setTransform (Transform transform) 
	{
		this.transform = transform;
	}
	/** Gets the local transformation matrix of this GameObject.*/
	public Matrix getLocalTransformationMatrix ()
	{
		return new Matrix (transform);
	}
	/** Gets the global transformation matrix of this GameObject.*/
	public Matrix getGlobalTransformationMatrix ()
	{
		Matrix m = new Matrix ();
		GameObject tmp = this;
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		while (tmp != null)
		{
			list.add(tmp);
			tmp = tmp.parent;
		}
		for (int a = list.size()-1; a >= 0; a--)
		{
			m = m.multiply(list.get(a).getLocalTransformationMatrix());
		}
		return m;
	}
	/** Gets the global position of this GameObject through subtracting 
	 * transform by the parents of the GameObject.*/
	public Vec4 getGlobalPosition ()
	{
		/*Vec4 v = transform.getLocalPosition();
		GameObject tmp = parent;
		while (tmp != null)
		{
			v = Vec4.add(tmp.getLocalTransform().getLocalPosition(), v);
			tmp = tmp.parent;
		}
		return v;*/
		return Vec4.Transform(Vec4.center, getGlobalTransformationMatrix());
	}
	/** Gets the global position of this GameObject through transforming
	 * by the global transformation matrix.*/
	public Vec4 getGlobalTransformedPosition ()
	{
		return Vec4.Transform(new Vec4 (), getGlobalTransformationMatrix());
	}
	/** Gets the global rotation of this GameObject.*/
	public Rotation getGlobalRotation ()
	{
		Rotation r = transform.getLocalRotation();
		GameObject tmp = parent;
		while (tmp != null)
		{
			r = Rotation.subtract(Rotation.multiply(tmp.getLocalTransform().getLocalRotation(), new Rotation (-1, -1, -1)), r);
			tmp = tmp.parent;
		}
		return r;
	}
	/** Gets the global scale of this GameObject.*/
	public Scale getGlobalScale ()
	{
		Scale s = transform.getLocalScale();
		GameObject tmp = parent;
		while (tmp != null)
		{
			s = Scale.multiply(s, tmp.getLocalTransform().getLocalScale());
			tmp = tmp.parent;
		}
		return s;
	}
	/** Adds an amount to the specified Axis. 
	 * @param amount the amount.
	 * @param axis the Axis.
	 */
	public void addTranslate (double amount, Axis axis) 
	{
		switch (axis) 
		{
		case X:
			transform.setPosX(transform.getPosX()+amount);
			break;
		case Y:
			transform.setPosY(transform.getPosY()+amount);
			break;
		case Z:
			transform.setPosZ(transform.getPosZ()+amount);
			break;
		}
	}
	/** Sets a value to the specified Axis. 
	 * @param value the value.
	 * @param axis the Axis.
	 */
	public void setTranslate (double amount, Axis axis) 
	{
		switch (axis) 
		{
		case X:
			transform.setPosX(amount);
			break;
		case Y:
			transform.setPosY(amount);
			break;
		case Z:
			transform.setPosZ(amount);
			break;
		}
	}
	/** Adds an amount to the Rotation.
	 * @param amount the amount.
	 * @param axis the Axis.
	 */
	public void addRotate (double amount, Axis axis) 
	{
		switch (axis) 
		{
		case X:
			transform.setRotX(transform.getRotX()+amount);
			break;
		case Y:
			transform.setRotY(transform.getRotY()+amount);
			break;
		case Z:
			transform.setRotZ(transform.getRotZ()+amount);
			break;
		}
	}
	/** Sets a value to the Rotation.
	 * @param value the value.
	 * @param axis the Axis.
	 */
	public void setRotate (double value, Axis axis) 
	{
		switch (axis) 
		{
		case X:
			transform.setRotX(value);
			break;
		case Y:
			transform.setRotY(value);
			break;
		case Z:
			transform.setRotZ(value);
			break;
		}
	}
	/** Adds an amount to the specified Direction. 
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
	}
	/** Sets a value to the specified Direction. 
	 * @param value the value.
	 * @param dir the Direction.
	 */
	public void setTranslate (double value, Direction dir) 
	{
		switch (dir) 
		{
		case Forward:
			transform.setPosZ(value);
			break;
		case Backward:
			transform.setPosZ(-value);
			break;
		case Left:
			transform.setPosX(-value);
			break;
		case Right:
			transform.setPosX(value);
			break;
		case Up:
			transform.setPosY(-value);
			break;
		case Down:
			transform.setPosY(value);
			break;
		}
	}
	/** Adds an amount to the specified Direction. 
	 * @param amount the amount.
	 * @param dir the Direction.
	 */
	public void addRotate (double amount, Direction dir) 
	{
		switch (dir) 
		{
		case Forward:
			transform.setRotZ(transform.getRotZ()+amount);
			break;
		case Backward:
			transform.setRotZ(transform.getRotZ()-amount);
			break;
		case Left:
			transform.setRotX(transform.getRotX()-amount);
			break;
		case Right:
			transform.setRotX(transform.getRotX()+amount);
			break;
		case Up:
			transform.setRotY(transform.getRotY()-amount);
			break;
		case Down:
			transform.setRotY(transform.getRotY()+amount);
			break;
		}
	}
	/** Sets a value to the specified Direction. 
	 * @param value the value.
	 * @param dir the Direction.
	 */
	public void setRotate (double value, Direction dir) 
	{
		switch (dir) 
		{
		case Forward:
			transform.setRotZ(value);
			break;
		case Backward:
			transform.setRotZ(-value);
			break;
		case Left:
			transform.setRotX(-value);
			break;
		case Right:
			transform.setRotX(value);
			break;
		case Up:
			transform.setRotY(-value);
			break;
		case Down:
			transform.setRotY(value);
			break;
		}
	}
	/** Adds an amount to the specified Axis. 
	 * @param amount the amount.
	 * @param axis the Axis.
	 */
	public void addScale (double amount, Axis axis) 
	{
		switch (axis) 
		{
		case X:			
			transform.setScaleX(transform.getScaleX()+amount);
			break;
		case Y:			
			transform.setScaleY(transform.getScaleY()+amount);
			break;
		case Z:
			transform.setScaleZ(transform.getScaleZ()+amount);
			break;
		}
	}
	/** Sets a value to the specified Axis. 
	 * @param value the value.
	 * @param axis the Axis.
	 */
	public void setScale (double amount, Axis axis) 
	{
		switch (axis) 
		{
		case X:
			transform.setScaleX(amount);
			break;
		case Y:
			transform.setScaleY(amount);
			break;
		case Z:
			transform.setScaleZ(amount);
			break;
		}
	}
	/** Returns whether or not gameObject is active.*/
	public boolean isActive () 
	{
		return active;
	}
	/** Sets whether or not gameObject is active.
	 * @param active the value.
	 */
	public void setActive (boolean value) 
	{
		active = value;
	}
	/** Sets the parent of this GameObject.
	 * @param g the new parent.
	 */
	public void setParent (GameObject g)
	{
		parent = g;
		//transform = new Transform (getGlobalPosition(), getGlobalRotation(), getGlobalScale());
	}
}
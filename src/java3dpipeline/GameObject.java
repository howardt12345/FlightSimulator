package java3dpipeline;
import java.io.*;

@SuppressWarnings("serial")
/** The GameObject Class, used as a Superclass for objects. Implements Serializable.*/
public class GameObject implements Serializable {
	/** The Transform of the gameObject.
	 * @param transform the Transform.*/
	protected Transform transform;
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
	/** Gets the Transform of the gameObject.
	 * @return the transform
	 */
	public Transform getTransform () 
	{
		return transform;
	}
	/** Sets the Transform of the gameObject.
	 * @param transform the transform to set
	 */
	public void setTransform (Transform transform) 
	{
		this.transform = transform;
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
			transform.setPosZ(transform.getPosZ()+amount);
			break;
		case Backward:
			transform.setPosZ(transform.getPosZ()-amount);
			break;
		case Left:
			transform.setPosX(transform.getPosX()-amount);
			break;
		case Right:
			transform.setPosX(transform.getPosX()+amount);
			break;
		case Up:
			transform.setPosY(transform.getPosY()-amount);
			break;
		case Down:
			transform.setPosY(transform.getPosY()+amount);
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
}
package java3dpipeline;
import java.util.*;
import java.io.*;
@SuppressWarnings({ "unused", "serial" })
/** The Transform class, implements Serializable.*/
public class Transform implements Serializable {
	/** The position of the Transform stored as a Vec4.
	 * @param position the location of the Transform.
	 */
	private Vec4 position = new Vec4 ();
	/** The rotation of the Transform stored as a Rotation.
	 * @param rotation the Rotation of the Transform.
	 */
	private Rotation rotation = new Rotation();
	/** The scale of the Transform stored as a Scale.
	 * @param scale the Scale of the Transform.
	 */
	private Scale scale = new Scale ();
	/** Creates a Transform from a Vec4, a Rotation, and a Scale.
	 * @param pos the position of the Transform.
	 * @param rot the rotation of the Transform.
	 * @param sc the Scale of the Transform.
	 */
	public Transform (Vec4 pos, Rotation rot, Scale sc) 
	{
		position = pos;
		rotation = rot;
		scale = sc;
	}
	/** Creates a Transform off of a Vec4 and a Rotation, with the Scale defaulting at 1.
	 * @param pos the position of the Transform.
	 * @param rot the rotation of the Transform.
	 */
	public Transform (Vec4 pos, Rotation rot) 
	{
		position = pos;
		rotation = rot;
		scale = new Scale (1);
	}
	/** Creates a new Transform.*/
	public Transform () {
		position = new Vec4 ();
		rotation = new Rotation ();
		scale = new Scale (1);
	}
	/** new Transform from 3 Positional values.
	 * @param posX the X position.
	 * @param posY the Y position.
	 * @param posZ the Z position.
	 */
	public Transform (double posX, double posY, double posZ) 
	{
		position = new Vec4 (posX, posY, posZ);
		rotation = new Rotation ();
		scale = new Scale (1);
	}
	/** New Transform from Vec4.
	 * @param pos the position.
	 */
	public Transform (Vec4 pos) {
		position = pos;
	}
	/** Prints the Transform values.*/
	public void print() {
		position.print();
		rotation.print();
		scale.print();
	}
	/** Checks if 2 Transforms are equal.
	 * @param t the transform.
	 */
	public boolean equals (Transform t) 
	{
		return position.equals(t.position) && rotation.equals(t.rotation) && scale.equals(t.scale);
	}
	/** Gets the position of the Transform as a Vec4.*/
	public Vec4 getLocalPosition () 
	{
		return position;
	}
	/** Gets the rotation of the Transform as a Rotation.*/
	public Rotation getLocalRotation () 
	{
		return rotation;
	}
	/** Gets the scale of the Transform as a Scale.*/
	public Scale getLocalScale () 
	{
		return scale;
	}
	/** Gets the Position X value.*/
	public double getPosX () 
	{
		return position.getX();
	}
	/** Gets the Position Y value.*/
	public double getPosY () 
	{
		return position.getY();
	}
	/** Gets the Position Z value.*/
	public double getPosZ () 
	{
		return position.getZ();
	}
	/** Gets the Rotation X value.*/
	public double getRotX () 
	{
		return rotation.getX();
	}
	/** Gets the Rotation Y value.*/
	public double getRotY () 
	{
		return rotation.getY();
	}
	/** Gets the Rotation Z value.*/
	public double getRotZ () 
	{
		return rotation.getZ();
	}
	/** Gets the scale X value.*/
	public double getScaleX () 
	{
		return scale.getX();
	}
	/** Gets the scale Y value.*/
	public double getScaleY () 
	{
		return scale.getY();
	}
	/** Gets the scale Z value.*/
	public double getScaleZ () 
	{
		return scale.getZ();
	}
	/** Sets position, rotation, and scale of an existing Transform.
	 * @param new_pos new position
	 * @param new_rot new rotation
	 * @param new_scale new scale
	 */
	public void Set (Vec4 new_pos, Rotation new_rot, Scale new_scale) 
	{
		position = new_pos;
		rotation = new_rot;
		scale = new_scale;
	}
	/** Sets a new position for the Transform.
	 * @param new_pos new position
	 */
	public void setPosition (Vec4 new_pos) 
	{
		position = new_pos;
	}
	/** Sets the X value of the position.
	 * @param new_X the new X value.*/
	public void setPosX (double new_X) 
	{
		position.setX(new_X);
	}
	/** Sets the Y value of the position.
	 * @param new_Y the new Y value.*/
	public void setPosY (double new_Y) 
	{
		position.setY(new_Y);
	}
	/** Sets the Z value of the position.
	 * @param new_Z the new Z value.*/
	public void setPosZ (double new_Z) 
	{
		position.setZ(new_Z);
	}
	/** Sets a new Rotation for the Transform.
	 * @param new_rot new rotation
	 */
	public void setRotation (Rotation new_rot) 
	{
		rotation = new_rot;
	}
	/** Sets the X value of the Rotation.
	 * @param new_X the new X value.*/
	public void setRotX (double new_X) 
	{
		rotation.setX(new_X);
	}
	/** Sets the Y value of the Rotation.
	 * @param new_Y the new Y value.*/
	public void setRotY (double new_Y) 
	{
		rotation.setY(new_Y);
	}
	/** Sets the Z value of the Rotation.
	 * @param new_Z the new Z value.*/
	public void setRotZ (double new_Z) 
	{
		rotation.setZ(new_Z);
	}
	/** Sets a new scale for the Transform.
	 * @param new_scale new scale
	 */
	public void setScale (Scale new_scale) 
	{
		scale = new_scale;
	}
	/** Sets the X value of the scale.
	 * @param new_X the new X value.*/
	public void setScaleX (double new_X) 
	{
		scale.setX(new_X);
	}
	/** Sets the Y value of the scale.
	 * @param new_Y the new Y value.*/
	public void setScaleY (double new_Y) 
	{
		scale.setY(new_Y);
	}
	/** Sets the Z value of the scale.
	 * @param new_Z the new Z value.*/
	public void setScaleZ (double new_Z) 
	{
		scale.setZ(new_Z);
	}
}
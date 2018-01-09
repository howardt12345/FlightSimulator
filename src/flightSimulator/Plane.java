package flightSimulator;
import java3dpipeline.*;

import java.awt.event.KeyEvent;

import javax.swing.*;
public class Plane {
	/** The Plane.*/
	private Polyhedron plane;
	/** The amount of missiles.*/
	private int missiles = 8;
	/** The Missile.*/
	private Missile missile;
	/** The Spawnpoint of the Missile.*/
	private GameObject spawnPoint = new GameObject (new Transform (0, -1.5, -2.8), true);
	private long time;
	private double reloadTime = 1;
	/** The speed of the Plane.*/
	private double speed = 150;
	private Vec4 v;
	/** New Plane from Polyhedron, Missile, amount of missiles and speed.
	 * @param plane the Polyhedron of the Plane.
	 * @param missile the Missile.
	 * @param missiles the amount of Missiles.
	 * @param speed the speed.
	 */
	public Plane (Polyhedron plane, Missile missile, int missiles, double speed)
	{
		this.plane = plane;
		this.missile = missile;
		this.setMissiles(missiles);
		this.speed = speed;
		v = new Vec4 (0, 0, speed, true);
		spawnPoint.setParent(plane);
	}
	/** Initializes the automatic movement of the Plane.
	 * @param f the JFrame.
	 */
	public void move (JFrame f) 
	{
		Animator anim = new Animator (f);
		anim.add(new Animation (plane, v, 6, true));
		anim.play();
	}
	/** Fires a missile.
	 * @param scene the Scene.
	 * @param f the JFrame.
	 */
	public void fire (Scene scene, JFrame f)
	{
		if (System.currentTimeMillis() > (long) time+(reloadTime*1000) && missiles > 0)
		{
			try {
				for (GameObject g : scene.getScene())
					if (g instanceof Missile)
						scene.remove(scene.indexOf(g));
			}
			catch (Exception e) {}
			time = System.currentTimeMillis();
			Missile missile = ((Missile) Utils.deepClone(this.missile));
			missile.getLocalTransform().setPosition(spawnPoint.getGlobalPosition());
			missile.getLocalTransform().setRotation(new Rotation (
					plane.getGlobalRotation().getX(),
					plane.getGlobalRotation().getY(),
					plane.getGlobalRotation().getZ()
					));
			scene.add(missile);
			Animator anim = new Animator (f);
			anim.add(new Animation (missile, new Vec4 (0, 0, missile.getRange(), true), reloadTime*0.75, scene, true));
			anim.play();
			missiles--;
		}
	}
	/** Corrects the Orientation of the Plane.*/
	public void correct (boolean[] keys)
	{
		if (plane.getLocalTransform().getLocalRotation().getX() > 360 ||
				plane.getLocalTransform().getLocalRotation().getX() < -360)
			plane.setRotate(plane.getLocalTransform().getLocalRotation().getX()%360, Axis.X);
		if (plane.getLocalTransform().getLocalRotation().getY() > 360 ||
				plane.getLocalTransform().getLocalRotation().getY() < -360)
			plane.setRotate(plane.getLocalTransform().getLocalRotation().getY()%360, Axis.Y);
		if (plane.getLocalTransform().getLocalRotation().getZ() > 360 ||
				plane.getLocalTransform().getLocalRotation().getZ() < -360)
			plane.setRotate(plane.getLocalTransform().getLocalRotation().getZ()%360, Axis.Z);
		if (!keys[KeyEvent.VK_SHIFT] && !keys[KeyEvent.VK_CONTROL]) {
			if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 < -85)
				if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 > -90)
					plane.setRotate(-84, Axis.Z);
				else plane.addRotate(8, Axis.Z);
			if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 > 85)
				if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 < 90)
					plane.setRotate(84, Axis.Z);
				else plane.addRotate(-8, Axis.Z);
			if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 < 4 &&
					Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 > -4) {
				plane.setRotate(0, Axis.Z);
			}
			if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 > 4 ||
					Math.rint(plane.getLocalTransform().getLocalRotation().getZ())%360 < -4) {
				if (Math.rint(plane.getLocalTransform().getLocalRotation().getZ()) > 0)
					plane.addRotate(-4, Axis.Z); 
				else 
					plane.addRotate(4, Axis.Z);
			}
		}
	}
	public void control (boolean[] keys)
	{
		if (keys[KeyEvent.VK_UP]) {
			plane.addRotate(3*Math.cos(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
			plane.addRotate(3*Math.sin(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
		}
		if (keys[KeyEvent.VK_DOWN]) {
			plane.addRotate(-3*Math.cos(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
			plane.addRotate(-3*Math.sin(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
		}
		if (keys[KeyEvent.VK_LEFT]) {
			if (!keys[KeyEvent.VK_CONTROL]) {
				if (keys[KeyEvent.VK_SHIFT]) {
					plane.addRotate(-3*Math.cos(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
					plane.addRotate(-3*Math.sin(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
				}
				else 
					plane.addRotate(-3, Axis.Y);
			}
			if (!keys[KeyEvent.VK_SHIFT]) 
				plane.addRotate(8, Axis.Z);
		}
		if (keys[KeyEvent.VK_RIGHT]) {
			if (!keys[KeyEvent.VK_CONTROL]) {
				if (keys[KeyEvent.VK_SHIFT]) {
					plane.addRotate(3*Math.cos(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
					plane.addRotate(3*Math.sin(plane.getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
				}
				else
					plane.addRotate(3, Axis.Y);
			}
			if (!keys[KeyEvent.VK_SHIFT]) 
				plane.addRotate(-8, Axis.Z);
		}
		if (keys[KeyEvent.VK_MINUS]) {
			addSpeed(-5);
		}
		if (keys[KeyEvent.VK_EQUALS]) {
			addSpeed(5);
		}
		correct(keys);
	}
	/** Gets the speed of the Plane.
	 * @return the speed
	 */
	public double getSpeed() 
	{
		return speed;
	}
	/** Sets the speed of the Plane.
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) 
	{
		if (speed >= 0) 
		{
			this.speed = speed;
			v.setZ(this.speed);
		}
	}
	/** Adds speed to the Plane.
	 * @param speed the speed to add.
	 */
	public void addSpeed (double speed) 
	{
		if (this.speed+speed >= 0) 
		{
			this.speed += speed;
			v.setZ(this.speed);
		}
	}
	/** Gets the amount of missiles.
	 * @return the missiles
	 */
	public int getMissiles() 
	{
		return missiles;
	}
	/** Sets the amount of missiles.
	 * @param missiles the missiles to set
	 */
	public void setMissiles(int missiles) 
	{
		this.missiles = missiles;
	}
}
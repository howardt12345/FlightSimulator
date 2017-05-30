package flightSimulator;
import java3dpipeline.*;
import javax.swing.*;
public class Plane {
	private Polyhedron plane;
	private int missiles = 8;
	private Missile missile;
	private int health = 100;
	private GameObject spawnPoint = new GameObject (new Transform (0, -1.5, -2.8), true);
	private long time;
	private double reloadTime = 0.3;
	private double speed = 150;
	private Vec4 v = new Vec4 (0, 0, speed, true);

	public Plane (Polyhedron plane, Missile missile, int missiles, int health)
	{
		this.plane = plane;
		this.missile = missile;
		this.setMissiles(missiles);
		this.health = health;
		spawnPoint.setParent(plane);
	}
	public void move (JFrame f) 
	{
		Animator anim = new Animator (f);
		anim.add(new Animation (plane, v, 6, true));
		anim.play();
	}
	public void fire (Scene scene, JFrame f)
	{
		if (System.currentTimeMillis() > (long) time+(reloadTime*1000) && missiles > 0)
		{
			for (GameObject g : scene.getScene())
				if (g instanceof Missile)
					scene.remove(scene.indexOf(g));
			time = System.currentTimeMillis();
			Missile missile = ((Missile) Utils.deepClone(this.missile));
			missile.getLocalTransform().setPosition(spawnPoint.getGlobalPosition());
			missile.getLocalTransform().setRotation(new Rotation (plane.getGlobalRotation().getX(),
					plane.getGlobalRotation().getY(),
					plane.getGlobalRotation().getZ()));
			scene.add(missile);
			Animator anim = new Animator (f);
			anim.add(new Animation (missile, new Vec4 (0, 0, missile.getRange(), true), reloadTime*0.75, scene, true));
			anim.play();
			missiles--;
		}
	}
	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		if (speed >= 0) {
			this.speed = speed;
			v.setZ(this.speed);
		}
	}
	public void addSpeed (double speed) {
		if (this.speed+speed >= 0) {
			this.speed += speed;
			v.setZ(this.speed);
		}
		System.out.println(this.speed);
	}
	/**
	 * @return the missiles
	 */
	public int getMissiles() {
		return missiles;
	}
	/**
	 * @param missiles the missiles to set
	 */
	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}
}
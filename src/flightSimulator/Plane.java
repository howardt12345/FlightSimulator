package flightSimulator;
import java3dpipeline.*;
import javax.swing.*;
public class Plane {
	private Polyhedron plane;
	private int missiles = 8;
	private Missile missile;
	private int health = 100;
	private GameObject spawnPoint = new GameObject (new Transform (0, -1.5, -2.8));
	private long time;
	private double reloadTime = 0.3;
	
	public Plane (Polyhedron plane, Missile missile, int missiles, int health)
	{
		this.plane = plane;
		this.missile = missile;
		this.missiles = missiles;
		this.health = health;
		spawnPoint.setParent(plane);
	}
	public void fire (Scene scene, JFrame f)
	{
		if (System.currentTimeMillis() > (long) time+(reloadTime*1000))
		{
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
		}
	}
}
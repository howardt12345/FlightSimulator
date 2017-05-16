package java3dpipeline;
import java.io.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
/** The Animator class, implements Serializable. */
public class Animator implements Serializable {
	/** The Animations to run.*/
	private ArrayList<Animation> anim = new ArrayList<Animation>();
	/** The JFrame.*/
	private JFrame f;
	/** New Animator from Animation, target and JFrame.
	 * @param a the Animation.
	 * @param g the target.
	 * @param f the JFrame.
	 */
	public Animator (Animation a, JFrame f) 
	{
		anim.add(a);
		this.f = f;
	}
	/** New Animator from JFrame.
	 * @param f the JFrame.
	 */
	public Animator (JFrame f) 
	{
		this.f = f;
	}
	/** Runs all the Animations in the Animator.*/
	public void play () 
	{
		for (Animation a : anim) 
		{
			a.play(f);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/** Adds an Animation to the Animator.
	 * @param a the Animation to add.
	 */
	public void add (Animation a) 
	{
		anim.add(a);
	}
	/** Gets the Animation at the index.
	 * @param index the index.
	 */
	public Animation get (int index)
	{
		return anim.get(index);
	}
}
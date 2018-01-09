package flightSimulator;
import java3dpipeline.*;
@SuppressWarnings("serial")
public class Missile extends Polyhedron {
	private int range = 500;
	/** New Missile. */
	public Missile (Transform t, String filename, boolean active, int range)
	{
		super (t, filename, active, true);
		this.range = range;
	}
	/** Gets the range of the missile.*/
	public int getRange ()
	{
		return range;
	}
}
package flightSimulator;
import java3dpipeline.*;
import java.util.*;
public class Missile extends Polyhedron {
	protected int range = 500;
	
	public Missile (Transform t, String filename, boolean active, int range)
	{
		super (t, filename, active, true);
		this.range = range;
	}
	public int getRange ()
	{
		return range;
	}
}
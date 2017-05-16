package java3dpipeline;
import java.io.*;
import java.util.*;
import java.awt.*;

@SuppressWarnings("serial")
/** The Polyhedron Class, extends GameObject and implements Serializable.*/
public class Polyhedron extends GameObject implements Serializable {
	/** The Arraylist of Polygons in the Polyhedron.*/
	private ArrayList<Polygon> object = new ArrayList<Polygon>();
	/** The File name.*/
	private String FileName;
	/** Creates a new Polyhedron with Transform, Scring filename, and Camera.
	 * @param transform the Transform of the Polyhedron.
	 * @param filename the file name.
	 * @param active whether or not the Polyhedron is active.
	 */
	public Polyhedron (Transform t, String filename, boolean a) 
	{
		super (t);
		super.setActive(a);
		ReadFile(filename);
		FileName = filename;
	}
	/** Reads the file and loads values in ArrayList.
	 * @param filename the filename.
	 */
	private void ReadFile (String filename) 
	{
		try 
		{
			Scanner input = new Scanner(new FileReader(filename));
			Scanner line = new Scanner(input.nextLine());
			while (input.hasNextLine() || line.hasNext()) 
			{			
				Polygon polygon = new Polygon ();
				while (line.hasNextDouble()) 
				{
					double tmpX = line.nextDouble(),
							tmpY = line.nextDouble(),
							tmpZ = line.nextDouble();
					polygon.add(new Vec4(tmpX, tmpY, tmpZ));
				}
				if (input.hasNextLine())
				{
					line = new Scanner(input.nextLine());			
				}
				object.add(polygon);
			}
			input.close();
			line.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** The Polyhedron transformed by the Polyhedron View Perspective Matrix.
	 * @param polyhedron the Polyhedron to transform.
	 * @param cam the Camera to transform to.
	 * @param lights the lights in scene.
	 * @param width the width.
	 * @param height the height.
	 * */
	public static Polyhedron MVP (Polyhedron polyhedron, Camera cam, ArrayList<Light> lights, double width, double height) 
	{
		Polyhedron m = (Polyhedron) Utils.deepClone(polyhedron); //Deep Clones model.
		ArrayList<Polygon> tmp = new ArrayList<Polygon>();
		Matrix globalTM = m.getGlobalTransformationMatrix(),
				viewPerspective = Matrix.multiply(cam.getPerspectiveMatrix(width, height), cam.getLookAtMatrix());
		for (int a = 0; a < m.object.size(); a++) 
		{ //Goes through all polygons.
			m.object.set(a, m.object.get(a).Transform(globalTM)); //Translates and Rotates Polygon.
			boolean isVisible = cam.isVisible(m.object.get(a)); //Calculates Polygon visibility.
			if (isVisible) 
			{
				m.object.get(a).calculateIntensity(lights); //Calculates light intensity.
				float intensity = m.object.get(a).getIntensity(); //stores in tmp value.
				m.object.set(a, m.object.get(a).Transform(viewPerspective).Normalized()); //Transforms Polygon to Projection space.
				if (m.object.get(a).getClosest(Vec4.center).getX() > -0.9 && m.object.get(a).getClosest(Vec4.center).getX() < 0.9
						&& m.object.get(a).getClosest(Vec4.center).getY() > -0.9 && m.object.get(a).getClosest(Vec4.center).getY() < 0.9
						&& m.object.get(a).getClosest(Vec4.center).getZ() < -cam.getNearClip() && m.object.get(a).getClosest(Vec4.center).getZ() > -cam.getFarClip()) 
				{
					m.object.get(a).setIntensity(intensity); //loads tmp value.
					tmp.add(m.object.get(a));
				}
			}
		}
		m.object = tmp;
		return m;
	}
	/** Paints the Polyhedron.
	 * @param g the Graphics component.
	 * @param cam the Camera.
	 * @param lights the lights.
	 * @param width the width.
	 * @param height the height.
	 * @param shiftX the screen shift on X axis
	 * @param shiftY the screen shift on Y axis.
	 * @param wire if wireframe enabled.
	 * @param shade if shading enabled.
	 * @return the number of polygons rendered.
	 */
	public int paint (Graphics g, Camera cam, ArrayList<Light> lights, int width, int height, int shiftX, int shiftY, boolean wire, boolean shade) 
	{
		Polyhedron P = Utils.polygonSort(Polyhedron.MVP (this, cam, lights, width, height));
		for (Polygon p : P.object) 
			p.paint(g, width, height, shiftX, shiftY, wire, shade);
		return P.object.size();
	}
	/** Prints the information on the Polyhedron.*/
	public void print () 
	{
		System.out.println("Polyhedron File Name: " + FileName);
		System.out.println("Object active: " + Boolean.toString(isActive()));
		printCoordinates();
	}
	/** Prints out the Polyhedron Coordinates.*/
	public void printCoordinates () 
	{
		for (int a = 0; a < object.size(); a++) 
		{
			System.out.println("Polygon " + (a+1) + ":");
			object.get(a).detailedPrint();
			System.out.println("**********");
		}
	}
	/** Gets the closest polygon to a Vec4.
	 * @param v the Vec4.
	 * @return the closest polygon.
	 */
	public Polygon getClosest (Vec4 v) 
	{
		Polygon tmp = object.get(0);
		for (int a = 0; a < object.size(); a++) 
		{ 
			if (Vec4.getDistance(v, object.get(a).getClosest(v)) < Vec4.getDistance(v, tmp.getClosest(v)))
			tmp = object.get(a);
		}
		return tmp;
	}
	/** Gets the farthest polygon to a Vec4.
	 * @param v the Vec4.
	 * @return the fathest polygon.
	 */
	public Polygon getFarthest (Vec4 v) 
	{
		Polygon tmp = object.get(0);
		for (int a = 0; a < object.size(); a++) 
		{ 
			if (Vec4.getDistance(v, object.get(a).getClosest(v)) > Vec4.getDistance(v, tmp.getClosest(v)))
			tmp = object.get(a);
		}
		return tmp;
	}
	/** Gets the polygons in the Polyhedron.
	 * @return the Polygons in the Polyhedron.
	 */
	public ArrayList<Polygon> getPolygons () 
	{
		return object;
	}
	/** Gets the fileName of the Polyhedron.
	 * @return the fileName
	 */
	public String getFileName () 
	{
		return FileName;
	}
}
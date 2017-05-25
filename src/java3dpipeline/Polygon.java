package java3dpipeline;
import java.io.*;
import java.util.*;
import java.awt.*;

/** The Polygon Class, implements Serializable.*/
@SuppressWarnings("serial")
public class Polygon implements Serializable {
	/** The vertices of the polygon.*/
	private ArrayList <Vec4> polygon;
	/** Whether or not the Polygon is visible.*/
	private boolean visible = true;
	/** The Light Intensity on a Polygon.*/
	private float intensity;
	/** new Polygon.*/
	public Polygon () 
	{
		polygon = new ArrayList <Vec4>();
	}
	/** New Polygon from an ArrayList of Vec4s.
	 * @param vertices the ArrayList
	 */
	public Polygon (ArrayList<Vec4> vertices) 
	{
		polygon = vertices;
	}
	/** Adds a vertex to the Polygon.
	 * @param v the Vec4 to Add.
	 */
	public void add (Vec4 v) 
	{
		polygon.add(v);
	}
	/** Returns the transformed Polygon.
	 * @param t the Transform.
	 * @return the transformed polygon.
	 */
	public Polygon Transform (Transform t) 
	{
		Polygon p = this;
		p.transform(t);
		return p;
	}
	/** Returns the transformed Polygon.
	 * @param t the Matrix.
	 * @return the transformed polygon.
	 */
	public Polygon Transform (Matrix m) 
	{
		Polygon p = this;
		p.transform(m);
		return p;
	}
	/** Transforms the Polygon.
	 * @param t the Transform.
	 */
	public void transform (Transform t) 
	{
		for (int a = 0; a < polygon.size(); a++) 
			polygon.set(a, polygon.get(a).Transform(new Matrix (t)));
	}
	/** Transforms the Polygon.
	 * @param t the Matrix to Transform with.
	 */
	public void transform (Matrix m) 
	{
		for (int a = 0; a < polygon.size(); a++) 
			polygon.set(a, polygon.get(a).Transform(m));
	}
	/** Returns the Vec4 at the specified index.
	 * @param index the index.
	 * @return the Vec4 at the index.
	 */
	public Vec4 get (int index) 
	{
		return polygon.get(index);
	}
	/** Replaces the Vec4 at the specified position with the specified Vec4.
	 * @param index the index.
	 * @param v the Vec4.
	 */
	public void set (int index, Vec4 v) 
	{
		polygon.set(index, v);
	}
	/** Normalizes the Polygon Vec4s by W.*/
	public void Normalize () 
	{
		for (int a = 0; a < polygon.size(); a++) 
			polygon.set(a, polygon.get(a).Normalized());
	}
	/** Returns a Polygon with normalized Vec4s by W.*/
	public Polygon Normalized () 
	{
		Polygon p = new Polygon ();
		for (Vec4 v : polygon) 
			p.add(v.Normalized());
		return p;
	}
	/** Normalizes the Polygon Vec4s by dividing by magnitude.*/
	public void normalize () 
	{
		for (int a = 0; a < polygon.size(); a++) 
			polygon.set(a, polygon.get(a).Normalized());
	}
	/** Returns a polygon with normalized Vec4s by dividing by magnitude.*/
	public Polygon normalized () 
	{
		Polygon p = new Polygon ();
		for (Vec4 v : polygon) 
			p.add(v.normalized());
		return p;
	}
	/** Whether or not the polygon is visible.
	 */
	public boolean isVisible () 
	{
		return visible;
	}
	/** Sets polygon visibility.
	 * @param visible polygon visibility.
	 */
	public void setVisible (boolean visible) 
	{
		this.visible = visible;
	}
	/** Gets the light intensity on the Polygon.
	 * @return the intensity
	 */
	public float getIntensity () 
	{
		return intensity;
	}
	/** sets light intensity on the Polygon.
	 * @param intensity the intensity to set
	 */
	public void setIntensity (float intensity) 
	{
		this.intensity = intensity;
	}
	/** Calculates the light intensity on the Polygon.
	 * @param lights the lights in the scene.
	 */
	public void calculateIntensity (ArrayList<Light> lights) 
	{
		float tmp = 0;
		for(int a = 1; a <= lights.size(); a++) 
		{
			if (lights.get(a-1) instanceof Light_Point)
				tmp += (((Light_Point) lights.get(a-1)).calculate(this)/(a+1));
			else if (lights.get(a-1) instanceof Light_Directional)
				tmp += (((Light_Directional) lights.get(a-1)).calculate(this)/(a+1));
		}
		setIntensity (tmp < 0 || tmp > 1 ? tmp <= 0 ? 0 : 1 : tmp);
	}
	/** Returns the number of vertices in polygon.*/
	public int size () 
	{
		return polygon.size();
	}
	/** Gets the Normal of the Polygon.*/
	public Vec4 getNormal () 
	{
		return !polygon.isEmpty() ? Vec4.getNormal(polygon.get(0), polygon.get(1), polygon.get(2)) : null; 
	}
	/** Gets the Center of the Polygon.*/
	public Vec4 getCenter () 
	{
		return Vec4.getCenter(polygon);
	}
	/** Gets the closest point in the polygon relative to a Vec4.
	 * @param v the Vec4.
	 */
	public Vec4 getClosest (Vec4 v) 
	{
		Vec4 tmp = polygon.get(0);
		for (int a = 0; a < polygon.size(); a++) 
		{
			if (Vec4.getDistance(v, polygon.get(a)) < Vec4.getDistance(v, tmp))
			tmp = polygon.get(a);
		}
		return tmp;
	}
	/** Gets the closest point in the polygon relative to a Vec4.
	 * @param v the Vec4.
	 */
	public Vec4 getFarthest (Vec4 v) 
	{
		Vec4 tmp = polygon.get(0);
		for (int a = 0; a < polygon.size(); a++) 
		{
			if (Vec4.getDistance(v, polygon.get(a)) > Vec4.getDistance(v, tmp))
			tmp = polygon.get(a);
		}
		return tmp;
	}
	/** Paints the Polygon.
	 * @param g the Graphics component.
	 * @param width the width.
	 * @param height the height.
	 * @param shiftX the screen shift on X axis
	 * @param shiftY the screen shift on Y axis.
	 * @param wire if wireframe enabled.
	 * @param shade if shading enabled.
	 */
	public void paint (Graphics g, int width, int height, int shiftX, int shiftY, boolean wire, boolean shade) 
	{
		int[] xCoord = new int [size()],
		yCoord = new int [size()];
		for (int b = 0; b < size(); b++) 
		{
			xCoord[b] = (int) Math.rint((get(b).getX()/get(b).getZ()*width/2)+(width/2)+shiftX);
			yCoord[b] = (int) Math.rint((get(b).getY()/get(b).getZ()*height/2)+(height/2)+shiftY);
		}
		if (shade) 
		{
			g.setColor(Color.getHSBColor(0, 0, getIntensity()));
			g.fillPolygon(xCoord, yCoord, xCoord.length);
		}
		if (wire) 
		{
			g.setColor(Color.BLACK);
			g.drawPolygon(xCoord, yCoord, xCoord.length);
		}
	}
	/** Prints out the vertices of the Polygon.*/
	public void print () 
	{
		for (Vec4 v : polygon) 
			v.print();
	}
	/** Prints out detailed information on the Polygon.*/
	public void detailedPrint () 
	{
		for (int a = 0; a < polygon.size(); a++) 
		{
			System.out.println("Vertex " + (a+1) + ":");
			polygon.get(a).print();
		}
		System.out.println("Normal:");
		getNormal().print();
	}
}
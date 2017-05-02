package java3dpipeline;
import java.io.*;
import java.text.*;
import java.util.*;

/** The utility Class. */
public class Utils {
	/** Sorts an ArrayList of Polyhedrons relative to the Camera.
	 * @param data the input data.
	 * @return the sorted ArrayList.
	 */
	public static ArrayList<Polyhedron> zSort (ArrayList<Polyhedron> data, Camera c)
	{
		try
		{
			ArrayList<Polyhedron> tmp = data;
			QuickSort (tmp, c, 0, data.size()-1);
			return tmp;
		}
		catch (Exception e) 
		{
			return data;
		}
	}
	/** Quick Sort.
	 * @param data the input data.
	 * @param left the left.
	 * @param right the right.
	 */
	private static void QuickSort (ArrayList<Polyhedron> data, Camera c, int left, int right) 
	{
		int index = Partition(data, c, left, right);
		if (left < index - 1)
			QuickSort(data, c, left, index - 1);
		if (index < right)
			QuickSort(data, c, index, right);
	}
	/** Partitions the Array.
	 * @param data the data.
	 * @param left the left.
	 * @param right the right.
	 */
	private static int Partition (ArrayList<Polyhedron> data, Camera cam, int left, int right) 
	{
		int a = left, b = right;
		double pivot = Vec4.getDistance(cam.getLookFrom(), data.get((int)Math.rint((left + right) / 2)).getTransform().getPosition());
		while (a <= b) 
		{
			while (Vec4.getDistance(cam.getLookFrom(), data.get(a).getTransform().getPosition()) > pivot)
				a++;
			while (Vec4.getDistance(cam.getLookFrom(), data.get(b).getTransform().getPosition()) < pivot)
				b--;
			if (a <= b) 
			{
				Swap (data, a, b);
				a++;
				b--;
			}
		}
		return a;
	}
	/** Swaps the elements in the ArrayList.
	 * @param array the array.
	 * @param a the first index
	 * @param b the second index.
	 */
	private static void Swap (ArrayList<Polyhedron> array, int a, int b) 
	{
		Polyhedron tmp = array.get(a);
		array.set(a, array.get(b));
		array.set(b, tmp);
	}
	/** Sorts the polygons in a Polyhedron relative to the Origin.
	 * @param data the input data.
	 */
	public static Polyhedron polygonSort (Polyhedron data)
	{
		try 
		{
			Polyhedron tmp = data;
			quickSort (tmp.getPolygons(), 0, tmp.getPolygons().size()-1);
			return tmp;
		}
		catch (Exception e) 
		{
			return data;
		}
	}
	/** Quick Sort.
	 * @param data the input data.
	 * @param left the left.
	 * @param right the right.
	 */
	private static void quickSort (ArrayList<Polygon> data, int left, int right) 
	{
		int index = partition(data, left, right);
		if (left < index - 1)
			quickSort(data, left, index - 1);
		if (index < right)
			quickSort(data, index, right);
	}
	/** Partitions the Array.
	 * @param data the data.
	 * @param left the left.
	 * @param right the right.
	 */
	private static int partition (ArrayList<Polygon> data, int left, int right) 
	{
		int a = left, b = right;
		double pivot = Vec4.getDistance(new Vec4 (0, 0, 1), data.get((int) (left + right)/2).getClosest(Vec4.center));
		while (a <= b) 
		{
			while (Double.compare(Vec4.getDistance(new Vec4 (0, 0, 1), data.get(a).getClosest(Vec4.center)), pivot) > 0)
				a++;
			while (Double.compare(Vec4.getDistance(new Vec4 (0, 0, 1), data.get(b).getClosest(Vec4.center)), pivot) < 0)
				b--;
			if (a <= b) 
			{
				swap (data, a, b);
				a++;
				b--;
			}
		}
		return a;
	}
	/** Swaps the elements in the ArrayList.
	 * @param array the array.
	 * @param a the first index
	 * @param b the second index.
	 */
	private static void swap (ArrayList<Polygon> array, int a, int b) 
	{
		Polygon tmp = array.get(a);
		array.set(a, array.get(b));
		array.set(b, tmp);
	}
	/** Checks if a String is numeric.
	 * @param str the input string.
	 */
	public static boolean isNumeric (String str) 
	{
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}
	/** Print Method for an Array.
	 * @param in the Array to print.
	 */
	public static void print (int[] in) 
	{
		for (int a = 0; a < in.length; a++) 
			System.out.println(in[a]);
	}
	/** Print method for a 2D Array.
	 * @param in the 2D Array to print.
	 */
	public static void print (int[][] in) 
	{
		for (int i = 0; i < in.length; i++) 
		{
			for (int j = 0; j < in[i].length; j++) 
				System.out.print(" " + in[i][j]);
			System.out.println();
		}
	}
	/** Print Method for an Array.
	 * @param in the Array to print.
	 */
	public static void print (double[] in) 
	{
		for (int a = 0; a < in.length; a++) 
			System.out.println(in[a]);
	}
	/** Print method for a 2D Array.
	 * @param in the 2D Array to print.
	 */
	public static void print (double[][] in) 
	{
		for (int i = 0; i < in.length; i++) 
		{
			for (int j = 0; j < in[i].length; j++) 
				System.out.print(" " + in[i][j]);
			System.out.println();
		}
	}
	/** Print Method for an Array.
	 * @param in the Array to print.
	 */
	public static void print (String[] in) 
	{
		for (int a = 0; a < in.length; a++) 
			System.out.println(in[a]);
	}
	/** Print method for a 2D Array.
	 * @param in the 2D Array to print.
	 */
	public static void print (String[][] in) 
	{
		for (int i = 0; i < in.length; i++)
		{
			for (int j = 0; j < in[i].length; j++) 
				System.out.print(" " + in[i][j]);
			System.out.println();
		}
	}
	/** Deep Clone of any Java Object.
	A deep clone copies all fields, and makes copies of dynamically 
	allocated memory pointed to by the fields. Unlike a shallow clone, a deep clone 
	will be 100% independent from the original and any changes made to clone object 
	will not be reflected in the original object.
	* @param object the object to deep clone.
	* @return deep clone of object.
	*/
	public static Object deepClone (Object object) 
	{
		try 
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}
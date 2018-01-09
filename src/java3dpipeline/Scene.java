package java3dpipeline;
import java.util.*;
import java.awt.*;
import java.io.*;
/** The Scene class, implements Serializable.
Scene file documentation:
Scene can only contain one camera. 
 * */
@SuppressWarnings("serial")
public class Scene implements Serializable {
	/** The GameObjects in the Scene.*/
	private ArrayList<GameObject> scene = new ArrayList<GameObject>();
	/** The main camera.*/
	private Camera cam = new Camera ();
	/** Creates a Scene with a filename. 
	 * @param filename the filename.
	 */
	public Scene (String filename) 
	{
		ReadFile(filename);
	}
	/** Creates a Scene with a a file containing filenames. 
	 * @param filename the filename.
	 * @param multipleFiles if file contains multiple filenames.
	 */
	public Scene (String filename, boolean multipleFiles) 
	{
		if (multipleFiles) //If file contains multiple filenames
		{
			try 
			{
				Scanner input = new Scanner(new FileReader(filename));
				System.out.println("Loading: " + filename);
				System.out.println("Loading multiple files.");
				while (input.hasNextLine()) 
				{
					String next = input.nextLine();
					/** If ignore line*/
					if (next.toLowerCase().contains("ignore ") || next.contains("//"))
					{
						try 
						{
							continue;
						}
						catch (Exception e) 
						{
							break;
						}
					}
					else
						ReadFile (next);
				}
				input.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
			ReadFile (filename);
		System.out.println(filename + " has been loaded."); 
	}
	/** Reads the file and adds to Scene.
	 * @param filename
	 */
	private void ReadFile (String filename) 
	{
		try 
		{
			Scanner input = new Scanner(new FileReader(filename));
			Scanner line = new Scanner(input.nextLine());
			System.out.println("Loading: " + filename);
			while (input.hasNextLine() || line.hasNext()) 
			{	
				String next = line.next().toLowerCase();
				/** If ignore line*/
				if (next.equals("ignore") || next.contains("//")) 
				{
					try 
					{
						line = new Scanner(input.nextLine());			
						continue; //Proceed to next line.
					}
					catch (Exception e) 
					{
						break; //Break.
					}
				}
				else 
				{
					if (next.equals("add:")) //If add:
					{
						next = line.next().toLowerCase();
						switch (next) 
						{
						case "light": //If light
							next = line.next().toLowerCase();
							switch (next)
							{
							case "point": //If point light
								Light_Point lp = new Light_Point (new Transform (
										new Vec4 (line.nextDouble(), line.nextDouble(), line.nextDouble()), 
										new Rotation (line.nextDouble(), line.nextDouble(), line.nextDouble())));
								if (line.hasNextDouble()) 
									lp.setIntensity(line.nextDouble());
								if (line.hasNextDouble()) 
									lp.setRange(line.nextDouble());
								scene.add(lp);
								System.out.println("Add: Light_Point to index " + scene.indexOf(lp));
								break;
							case "directional"://If directional light
								Light_Directional ld = new Light_Directional (new Rotation (line.nextDouble(), line.nextDouble(), line.nextDouble()));
								if (line.hasNextDouble()) 
									ld.setIntensity(line.nextDouble());
								scene.add(ld);
								System.out.println("Add: Light_Directional to index " + scene.indexOf(ld));
								break;
							}
							break;
						case "polyhedron": //If Polyhedron
							Vec4 v = new Vec4 (line.nextDouble(), line.nextDouble(), line.nextDouble());
							Rotation r = new Rotation (line.nextDouble(), line.nextDouble(), line.nextDouble());
							Scale s = new Scale (line.nextDouble());
							if (line.hasNextDouble()) 
								s.setY(line.nextDouble());
							if (line.hasNextDouble()) 
								s.setZ(line.nextDouble());
							String fn = line.next();
							Polyhedron p = new Polyhedron (new Transform (v, r, s), fn, line.nextBoolean(), line.nextBoolean());
							if (line.hasNext()) p.setColor(line.next());
							scene.add(p);
							System.out.println("Add: " + fn + " to index " + scene.indexOf(p));
							break;
						case "camera": //if Camera
							cam = new Camera (new Transform (
									new Vec4 (line.nextDouble(), line.nextDouble(), line.nextDouble()), 
									new Rotation (line.nextDouble(), line.nextDouble(), line.nextDouble())));
							System.out.println("Add: Camera.");
							break;
						default: //If anything else
							input.close();
							line.close();
							throw new IllegalArgumentException ("Line does not contain a GameObject.");
						}
					}
					else if (next.equals("set:")) //Else if set:
					{
						String g1 = line.next().toLowerCase(), 
								pc = line.next().toLowerCase(), 
								g2 = line.next().toLowerCase();
						if (g1.equals("camera")) //If first object is camera
						{
							if (pc.equals("parentof")) 
								scene.get(Integer.parseInt(g2)).setParent(this.getCamera());
							else if (pc.equals("childof"))
								this.getCamera().setParent(scene.get(Integer.parseInt(g2)));
							else 
							{
								input.close();
								line.close();
								throw new IllegalArgumentException ("Cannot set parent or child.");
							}
						}
						else if (g2.equals("camera")) //Else if first object is camera
						{
							if (pc.equals("childof"))
								scene.get(Integer.parseInt(g1)).setParent(this.getCamera());
							else if (pc.equals("parentof"))
								this.getCamera().setParent(scene.get(Integer.parseInt(g1)));
							else 
							{
								input.close();
								line.close();
								throw new IllegalArgumentException ("Cannot set parent or child.");
							}
						}
						else //Else
						{
							if (pc.equals("parentof"))
								scene.get(Integer.parseInt(g2)).setParent(scene.get(Integer.parseInt(g1)));
							else if (pc.equals("childof"))
								scene.get(Integer.parseInt(g1)).setParent(scene.get(Integer.parseInt(g2)));
							else 
							{
								input.close();
								line.close();
								throw new IllegalArgumentException ("Cannot set parent or child.");
							}
						}
						System.out.println("Set: " + g1 + " " + pc + " " + g2); 
					}
				}
				if (input.hasNextLine()) //If input has next line
				{
					line = new Scanner(input.nextLine()); 
				}
				else
					System.out.println(filename + " has been loaded.");
			}
			input.close();
			line.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/** Adds a gameObject to the scene.
	 * @param g the gameObject to add.
	 */
	public void add (GameObject g) 
	{
		scene.add(g);
	}
	/** Paints all active Models in the scene.
	 * @param g the Graphics component.	 
	 * @param width the width.
	 * @param height the height.
	 * @param shiftX the screen shift on X axis
	 * @param shiftY the screen shift on Y axis.
	 * @param wire if wireframe enabled.
	 * @param shade if shading enabled.
	 * @param debug if debugging enabled.
	 */
	public void paint (Graphics g, int width, int height, int shiftX, int shiftY, boolean wire, boolean shade, boolean debug) 
	{
		long start = System.nanoTime(), end;
		ArrayList<Light> lights = new ArrayList<Light>();
		ArrayList<Polyhedron> tmp = new ArrayList<Polyhedron>();
		int active = 0, ctr = 0;
		for (GameObject gameObject : scene) /** For every GameObject in scene.*/
		{
			if (gameObject.isActive()) //If active
			{
				active++;
				if (gameObject instanceof Light) //If GameObject is light
					lights.add((Light) gameObject);
				else if (gameObject instanceof Polyhedron) //Else if GameObject is Polyhedron
				{
					if (gameObject.isCulled()) 
					{ //If GameObject will be culled
						Polyhedron p = (Polyhedron) gameObject;
						/** Calculate Object Culling.*/
						double d = p.getFarthest(Vec4.center).getFarthest(Vec4.center).magnitude();
						double d1 = (Vec4.dot(Vec4.subtract(cam.getGlobalTransformedPosition(), p.getGlobalTransformedPosition()).normalized(), 
								Vec4.subtract(cam.getLookFrom(), cam.getLookAt()).normalized()) > 0)
								? Vec4.subtract(cam.getGlobalTransformedPosition(), p.getGlobalTransformedPosition()).magnitude()
									: -Vec4.subtract(cam.getGlobalTransformedPosition(), p.getGlobalTransformedPosition()).magnitude();
						Vec4 v = Vec4.add(Vec4.multiply(Vec4.subtract(cam.getLookAt(), cam.getLookFrom()), d + d1), cam.getLookFrom());
						if (Vec4.dot(Vec4.subtract(cam.getLookFrom(), cam.getLookAt()), Vec4.subtract(cam.getGlobalTransformedPosition(), v)) > 0) 
							tmp.add(p);
					}
					else 
						tmp.add((Polyhedron) gameObject);
				}
			}
		}
		cam.Transform(); //Transform Camera
		tmp = Utils.zSort(tmp, cam); 
		for (Polyhedron p : tmp) //For every Polyhedron in tmp
		{ //Paint Polyhedron
			ctr += p.paint(g, cam, lights, width, height, shiftX, shiftY, wire, shade);
		}
		System.gc(); //Garbage Collect.
		end = System.nanoTime();
		if (debug) //If debug
		{ //Draw Debug info on screen.
			g.setColor(Color.black);
			g.drawRect(shiftX, shiftY, width, height);
			g.drawString(scene.size() + " objects total", width + 2*shiftX - 150, height + 2*shiftY - 90);
			g.drawString(active + " objects active", width + 2*shiftX - 150, height + 2*shiftY - 75);
			g.drawString(tmp.size() + " objects calculated", width + 2*shiftX - 150, height + 2*shiftY - 60);
			g.drawString(ctr + " total polygons", width + 2*shiftX - 150, height + 2*shiftY - 45);
			g.drawString(1000000000/(end - start)+ "fps", width + 2*shiftX - 70, 30);
			g.drawString(width + " X " + height, width > 1000 ? width-100+2*shiftX : width-75+2*shiftX, 15);
			g.drawString("Camera:", 5, 15);
			g.drawString("Position: (local) " + cam.getLocalTransform().getLocalPosition().asString("%1$.2f, %2$.2f, %3$.2f") + 
					" (global) " + cam.getGlobalPosition().asString("%1$.2f, %2$.2f, %3$.2f"), 5, 30);
			g.drawString("Rotation: " + cam.getGlobalRotation().asString("%1$.2f, %2$.2f, %3$.2f"), 5, 45);
			g.drawString("LookFrom: " + cam.getLookFrom().asString("%1$.5f, %2$.5f, %3$.5f"), 5, 60);
			g.drawString("LookAt: " + cam.getLookAt().asString("%1$.5f, %2$.5f, %3$.5f"), 5, 75);
			g.drawString("LookUp: " + cam.getLookUp().asString("%1$.5f, %2$.5f, %3$.5f"), 5, 90);
			for (int a = 0; a < scene.size(); a++) //List all GameObject information.
			{
				g.drawString("Object " + a + ": " + scene.get(a).getClass().getName().replaceAll("java3dpipeline.", ""), 5, (a+1)*70+45);
				g.drawString("Position: (local) " + scene.get(a).getLocalTransform().getLocalPosition().asString("%1$.2f, %2$.2f, %3$.2f") + 
				" (global) " + scene.get(a).getGlobalPosition().asString("%1$.2f, %2$.2f, %3$.2f"), 5, (a+1)*70+60);
				g.drawString("Rotation: (local) " + scene.get(a).getLocalTransform().getLocalRotation().asString("%1$.2f, %2$.2f, %3$.2f") +
						" (global) " + scene.get(a).getGlobalRotation().asString("%1$.2f, %2$.2f, %3$.2f"), 5, (a+1)*70+75);
				g.drawString("Scale: (local) " + scene.get(a).getLocalTransform().getLocalScale().asString("%1$.2f, %2$.2f, %3$.2f") + 
						" (global) " + scene.get(a).getGlobalScale().asString("%1$.2f, %2$.2f, %3$.2f"), 5, (a+1)*70+90);
			}
		}
	}
	/** Sets the Main Camera in the Scene.
	 * @param cam the Camera.
	 */
	public void setCamera (Camera cam) 
	{
		this.cam = cam;
	}
	/** Gets the gameObject located at the index.
	 * @param index the index.
	 */
	public GameObject get (String index) 
	{
		return Utils.isNumeric (index) ? (Integer.parseInt(index) < 0 || Integer.parseInt(index) >= scene.size()
			?  scene.get(0) : scene.get(Integer.parseInt(index))) : scene.get(0);
	}
	/** Gets the gameObject located at the index.
	 * @param index the index.
	 */
	public GameObject get (char index) 
	{
		return Utils.isNumeric (""+index) ? (Integer.parseInt(""+index) < 0 || Integer.parseInt(""+index) >= scene.size()
			?  scene.get(0) : scene.get(Integer.parseInt(""+index))) : scene.get(0);
	}
	/** Gets the gameObject located at the index.
	 * @param index the index.
	 */
	public GameObject get (int index) 
	{
		return index < 0 || index >= scene.size() ? scene.get(0) : scene.get(index);
	}
	/** Sets the GameObject at the located at the index.
	 * @param index the index of the GameObject to set.
	 * @param g the GameObject to set.
	 */
	public void set (int index, GameObject g)
	{
		scene.set(index, g);
	}
	/** Removes the gameObject located at the index.
	 * @param index the index.
	 */
	public void remove (String index) 
	{
		scene.remove(Utils.isNumeric (index) ? (Integer.parseInt(index) < 0 || Integer.parseInt(index) >= scene.size()
				? 0 : Integer.parseInt(index)) : 0);
	}
	/** Removes the gameObject located at the index.
	 * @param index the index.
	 */
	public void remove (char index) 
	{
		scene.remove(Utils.isNumeric (""+index) ? (Integer.parseInt(""+index) < 0 || Integer.parseInt(""+index) >= scene.size()
				? 0 : Integer.parseInt(""+index)) : 0);
	}
	/** Removes the gameObject located at the index.
	 * @param index the index.
	 */
	public void remove (int index) 
	{
		scene.remove(index < 0 || index >= scene.size() ? 0 : index);
	}
	/** Gets the index of the Object.
	 * @param o the Object.
	 * @return the index of the Object.
	 */
	public int indexOf (Object o)
	{
		return scene.indexOf(o);
	}
	/** Gets the main camera of the scene.*/
	public Camera getCamera () 
	{
		return cam;
	}
	public ArrayList<GameObject> getScene ()
	{
		return scene;
	}
	/** Gets the amount of GameObjects in scene.*/
	public int size () 
	{
		return scene.size();
	}
}
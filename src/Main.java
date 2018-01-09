import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

import java3dpipeline.*;
import flightSimulator.*;

@SuppressWarnings({ "serial" })
/** The Main Class, used to test the program. Anything in this class does not affect
 * the renderer.*/
public class Main extends JPanel implements ActionListener {
	private static Scene scene;
	private static JFrame f;
    private static boolean wire = true, shade = true, debug = false;
    private static Main m = new Main ();
    private static Timer t = new Timer (0, m);
    private static Plane plane;
    private static boolean[] keys = new boolean [256];
	public static void main (String[] args) {
		f = new JFrame();
		scene = new Scene ("scene.txt", true);
		plane = new Plane ((Polyhedron)scene.get(0), 
				new Missile (new Transform (), "missile.txt", true, 500), 8, 400);
		plane.move(f);
		new Plane ((Polyhedron)scene.get(2), 
				new Missile (new Transform (new Scale (4)), "missile.txt", true, 500), 8, 600).move(f);
		new Animator (new Animation (scene.get(2), new Rotation (0, 10, 0), true), f).play();
		f.addKeyListener(new KeyListener () 
		{
			public void keyTyped(KeyEvent e) 
			{
			}
			public void keyPressed(KeyEvent e) 
			{
				keys[e.getKeyCode()] = true;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SLASH: 
					wire = !wire;
					break;
				case KeyEvent.VK_PERIOD:
					shade = !shade; 
					break;
				case KeyEvent.VK_COMMA: 
					debug = !debug; 
					break;
				case KeyEvent.VK_BACK_SPACE: 
					System.exit(0);
					break;
				case KeyEvent.VK_ENTER:
					f.setExtendedState(JFrame.MAXIMIZED_BOTH);
					break;
				case KeyEvent.VK_SPACE:
					plane.fire(scene, f);
					break;
				case KeyEvent.VK_R:
					plane.setMissiles(8);
					break;
				}
				System.out.println(e.getKeyChar());
				f.repaint();
			}
			public void keyReleased(KeyEvent e) 
			{
				keys[e.getKeyCode()] = false;
			}
		});
		f.addMouseWheelListener(new MouseWheelListener() 
		{
			public void mouseWheelMoved(MouseWheelEvent e) 
			{
				plane.addSpeed(e.getWheelRotation());
				f.repaint();
			}
		});
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Main());
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setVisible(true);
		t.start();
	}
	public void paintComponent (Graphics g) {
		int shiftX = 0, shiftY = 0;
		scene.paint(g, f.getWidth()-shiftX*2, f.getHeight()-shiftY*2, shiftX, shiftY, wire, shade, debug);
		g.setColor(Color.black);
		g.drawString("Speed:" + plane.getSpeed(), f.getWidth() + 2*shiftX - 100, 45);
		g.drawString("Missiles:" + plane.getMissiles(), f.getWidth() + 2*shiftX - 100, 60);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		plane.control(keys);
		scene.get(1).addRotate(1, Axis.Y);
		f.repaint();
	}
}
import javax.swing.*;
import javax.swing.Timer;

import java3dpipeline.*;
import flightSimulator.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;

@SuppressWarnings({ "serial", "unused" })
/** The Main Class, used to test the program. Anything in this class does not affect
 * the renderer.*/
public class Main extends JPanel implements ActionListener {
	public static Scene scene;
	public static JFrame f = new JFrame();
    public static double oldX, newX, oldY, newY;
    public static double dx = 0, dy = 0, sensitivity = 0.05;
    static int tmp = 1;
    static boolean wire = true, shade = true, debug = true, 
    		mouseDown = false, keyDown = false,
    		left = false, right = false, up = false, down = false;
    static Timer t = new Timer (1, new Main ());
	public static void main (String[] args) {
		scene = new Scene ("scene.txt", true);
		scene.getCamera().setParent(scene.get(1));
		Plane plane = new Plane ((Polyhedron)scene.get(1), 
				new Missile (new Transform (), "missile.txt", true, 500),
				8, 100);
		Vec4 v = new Vec4 (0, 0, 100, true);
		Animator anim = new Animator (f);
		anim.add(new Animation (scene.get(tmp), v, 6, true));
		anim.play();
		f.addKeyListener(new KeyListener () 
		{
			public void keyTyped(KeyEvent e) 
			{
				if (Utils.isNumeric(""+e.getKeyChar()))
					tmp = Integer.parseInt(""+e.getKeyChar());
			}
			public void keyPressed(KeyEvent e) 
			{
				keyDown = true;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_BACK_SPACE: 
					System.exit(0);
					break;
				case KeyEvent.VK_ENTER:
					f.setExtendedState(JFrame.MAXIMIZED_BOTH);
					break;
				case KeyEvent.VK_SPACE:
					plane.fire(scene, f);
					break;
				case KeyEvent.VK_BACK_SLASH:
					scene.get(tmp).setActive(!scene.get(tmp).isActive());
					break;
				case KeyEvent.VK_DELETE:
					scene.remove(tmp);
					break;
				case KeyEvent.VK_R:
					scene = new Scene ("scene.txt", true);
					break;
				case KeyEvent.VK_P:
					scene.getCamera().setParent(scene.get(tmp));
					break;
				case KeyEvent.VK_N:
					scene.getCamera().setParent(null);
					break;
				case KeyEvent.VK_UP:
					/*scene.get(tmp).addTranslate(0.5, Axis.Z);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();*/
					scene.get(tmp).addRotate(2, Axis.X);
					break;
				case KeyEvent.VK_DOWN:
					/*scene.get(tmp).addTranslate(-0.5, Axis.Z);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();*/
					scene.get(tmp).addRotate(-2, Axis.X);
					break;
				case KeyEvent.VK_LEFT:
					/*scene.get(tmp).addTranslate(-0.5, Axis.X);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();*/
					scene.get(tmp).addRotate(-2, Axis.Y);
					scene.get(tmp).addRotate(2, Axis.Z);
					break;
				case KeyEvent.VK_RIGHT:
					/*scene.get(tmp).addTranslate(0.5, Axis.X);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();*/
					scene.get(tmp).addRotate(2, Axis.Y);
					scene.get(tmp).addRotate(-2, Axis.Z);
					break;
				case KeyEvent.VK_COMMA:
					scene.get(tmp).addTranslate(-0.5, Axis.Y);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();
					break;
				case KeyEvent.VK_PERIOD:
					scene.get(tmp).addTranslate(0.5, Axis.Y);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();
					break;
				case KeyEvent.VK_OPEN_BRACKET:
					scene.get(tmp).addRotate(1, Axis.Y);
					scene.get(tmp).getLocalTransform().getLocalRotation().print();
					break;
				case KeyEvent.VK_CLOSE_BRACKET:
					scene.get(tmp).addRotate(-1, Axis.Y);
					scene.get(tmp).getLocalTransform().getLocalRotation().print();
					break;
				case KeyEvent.VK_QUOTE:
					scene.get(tmp).addRotate(1, Axis.X);
					scene.get(tmp).getLocalTransform().getLocalRotation().print();
					break;
				case KeyEvent.VK_SEMICOLON:
					scene.get(tmp).addRotate(-1, Axis.X);
					scene.get(tmp).getLocalTransform().getLocalRotation().print();
					break;
				case KeyEvent.VK_MINUS:
					/*scene.get(tmp).addRotate(1, Axis.Z);
					scene.get(tmp).getLocalTransform().getLocalRotation().print();*/
					v.setZ(v.getZ() - 1);
					System.out.println(v.getZ());
					break;
				case KeyEvent.VK_EQUALS:
					/*scene.get(tmp).addRotate(-1, Axis.Z);
					scene.get(tmp).getLocalTransform().getLocalRotation().print();*/
					v.setZ(v.getZ() + 1);
					System.out.println(v.getZ());
					break;
				case KeyEvent.VK_SLASH:
					wire = !wire;
					break;
				case KeyEvent.VK_SHIFT:
					shade = !shade;
					break;
				case KeyEvent.VK_CONTROL:
					debug = !debug;
					break;
				case KeyEvent.VK_7:
					scene.getCamera().addRotate(1, Axis.X);
					break;
				case KeyEvent.VK_8:
					scene.getCamera().addRotate(-1, Axis.X);
					break;
				case KeyEvent.VK_9:
					scene.getCamera().addRotate(-1, Axis.Y);
					break;
				case KeyEvent.VK_0:
					scene.getCamera().addRotate(1, Axis.Y);
					break;
				}
				System.out.println(e.getKeyChar());
				f.repaint();
			}
			public void keyReleased(KeyEvent e) 
			{
				keyDown = false;
			}
		});
		f.addMouseWheelListener(new MouseWheelListener() 
		{
			public void mouseWheelMoved(MouseWheelEvent e) 
			{
				v.setZ(v.getZ() - e.getWheelRotation());
				f.repaint();
			}
		});
		f.addMouseMotionListener(new MouseMotionListener() 
		{
			public void mouseDragged(MouseEvent e) 
			{
				oldX = newX; 
				oldY = newY;
				newX = e.getX();
				newY = e.getY();
				dx = (newX - oldX)*sensitivity;
				dy = (newY - oldY)*sensitivity;
				f.repaint();
			}
			public void mouseMoved(MouseEvent e) 
			{
				oldX = newX = e.getX();
				oldY = newY = e.getY();
			}
		});
		f.addMouseListener(new MouseListener ()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isMiddleMouseButton(e))
					dx = dy = 0;
			}
			@Override
			public void mousePressed(MouseEvent e)
			{
				mouseDown = true;
			}
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				mouseDown = false;
			}
			@Override
			public void mouseEntered(MouseEvent e) 
			{
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
			}
		});
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Main());
		f.setSize(800, 600);
		f.setVisible(true);
		t.start();
	}
	public void paintComponent (Graphics g) {
		scene.paint(g, f.getWidth(), f.getHeight(), 0, 0, wire, shade, debug);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		scene.get(tmp).addRotate(-dy, Axis.X);
		scene.get(tmp).addRotate(dx, Axis.Y);
		scene.get(tmp).addRotate(-dx, Axis.Z);
		if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) < -85)
			scene.get(tmp).setRotate(-84, Axis.Z);
		if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) > 85)
			scene.get(tmp).setRotate(84, Axis.Z);
		//if (!mouseDown) {
			if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) < 4 &&
					Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) > -4) {
				scene.get(tmp).setRotate(0, Axis.Z);
			}
			if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) > 4 ||
					Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) < -4) {
				if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) > 0)
					scene.get(tmp).addRotate(-4, Axis.Z); 
				else 
					scene.get(tmp).addRotate(4, Axis.Z);
			}
		//}
		/*if (Math.rint (scene.get(tmp).getLocalTransform().getLocalRotation().getX()) < 4 &&
				Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getX()) > -4) {
			scene.get(tmp).setRotate(0, Axis.X);
		}
		if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getX()) > 1 ||
				Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getX()) < -1) {
			if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getX()) > 0) 
				scene.get(tmp).addRotate(-4, Axis.X);
			else scene.get(tmp).addRotate(4, Axis.X);
		}*/
		f.repaint();
	}
}

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
	private static Scene scene;
	private static JFrame f;
    private static double oldX, newX, oldY, newY;
    private static double dx = 0, dy = 0, sensitivity = 0.05;
    private static int tmp = 0;
    private static boolean wire = true, shade = true, debug = true, 
    		mouseDown = false, keyDown = false,
    		left = false, right = false, up = false, down = false;
    private static Timer t = new Timer (0, new Main ());
    private static Plane plane;
    private static boolean[] keys = new boolean [256];
	public static void main (String[] args) {
		f = new JFrame();
		scene = new Scene ("scene.txt", true);
		scene.getCamera().setParent(scene.get(0));
		plane = new Plane ((Polyhedron)scene.get(0), 
				new Missile (new Transform (), "missile.txt", true, 500),
				8, 100);
		plane.move(f);
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
				keys[e.getKeyCode()] = true;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SLASH: 
					wire = !wire;
					break;
				case KeyEvent.VK_SHIFT: 
					//shade = !shade; 
					break;
				case KeyEvent.VK_CONTROL: 
					//debug = !debug; 
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
				case KeyEvent.VK_BACK_SLASH:
					scene.get(tmp).setActive(!scene.get(tmp).isActive());
					break;
				case KeyEvent.VK_DELETE:
					scene.remove(tmp);
					break;
				case KeyEvent.VK_R:
					scene = new Scene ("scene.txt", true);
					break;
				}
				System.out.println(e.getKeyChar());
				f.repaint();
			}
			public void keyReleased(KeyEvent e) 
			{
				keyDown = false;
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
		f.addMouseMotionListener(new MouseMotionListener() 
		{
			public void mouseDragged(MouseEvent e) 
			{
				oldX = newX; 
				oldY = newY;
				newX = e.getX();
				newY = e.getY();
				dx = (newX - oldX)*sensitivity*1.5;
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
		int shiftX = 0, shiftY = 0;
		scene.paint(g, f.getWidth(), f.getHeight(), shiftX, shiftY, wire, shade, debug);
		g.drawString("Speed:" + plane.getSpeed(), f.getWidth() + 2*shiftX - 100, 45);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (keys[KeyEvent.VK_UP]) {
			scene.get(tmp).addRotate(2*Math.cos(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
			scene.get(tmp).addRotate(2*Math.sin(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
		}
		if (keys[KeyEvent.VK_DOWN]) {
			scene.get(tmp).addRotate(-2*Math.cos(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
			scene.get(tmp).addRotate(-2*Math.sin(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
		}
		if (keys[KeyEvent.VK_LEFT]) {
			if (!keys[KeyEvent.VK_CONTROL]) {
				if (keys[KeyEvent.VK_SHIFT]) {
					scene.get(tmp).addRotate(-3*Math.cos(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
					scene.get(tmp).addRotate(-3*Math.sin(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
				}
				else 
					scene.get(tmp).addRotate(-3, Axis.Y);
			}
			if (!keys[KeyEvent.VK_SHIFT]) 
				scene.get(tmp).addRotate(8, Axis.Z);
		}
		if (keys[KeyEvent.VK_RIGHT]) {
			if (!keys[KeyEvent.VK_CONTROL]) {
				if (keys[KeyEvent.VK_SHIFT]) {
					scene.get(tmp).addRotate(3*Math.cos(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.Y);
					scene.get(tmp).addRotate(3*Math.sin(scene.get(tmp).getLocalTransform().getLocalRotation().getRadianZ()), Axis.X);
				}
				else
					scene.get(tmp).addRotate(3, Axis.Y);
			}
			if (!keys[KeyEvent.VK_SHIFT]) 
				scene.get(tmp).addRotate(-8, Axis.Z);
		}
		if (keys[KeyEvent.VK_MINUS]) {
			plane.addSpeed(-5);
		}
		if (keys[KeyEvent.VK_EQUALS]) {
			plane.addSpeed(5);
		}
		scene.get(1).addRotate(1, Axis.Y);
		if (!keys[KeyEvent.VK_SHIFT] && !keys[KeyEvent.VK_CONTROL]) {
			/*if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ())%360 < -85)
				scene.get(tmp).setRotate(-84, Axis.Z);
			if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ())%360 > 85)
				scene.get(tmp).setRotate(84, Axis.Z);*/
			if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ())%360 < 4 &&
					Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ())%360 > -4) {
				scene.get(tmp).setRotate(0, Axis.Z);
			}
			if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ())%360 > 4 ||
					Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ())%360 < -4) {
				if (Math.rint(scene.get(tmp).getLocalTransform().getLocalRotation().getZ()) > 0)
					scene.get(tmp).addRotate(-4, Axis.Z); 
				else 
					scene.get(tmp).addRotate(4, Axis.Z);
			}
		}
		f.repaint();
	}
}

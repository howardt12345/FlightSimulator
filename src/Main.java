import javax.swing.*;
import javax.swing.Timer;

import java3dpipeline.*;

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
    public static double dx = 0, dy = 0, speed = 0.3;
    static int tmp = 0;
    static boolean wire = true, shade = true, debug = true, mouseDown = false;
    static Timer t = new Timer (0, new Main ());
	public static void main (String[] args) {
		scene = new Scene ("scene.txt", true);
		f.addKeyListener(new KeyListener () 
		{
			public void keyTyped(KeyEvent e) 
			{
				if (Utils.isNumeric(""+e.getKeyChar()))
					tmp = Integer.parseInt(""+e.getKeyChar());
			}
			public void keyPressed(KeyEvent e) 
			{
				switch (e.getKeyCode()) {
				case KeyEvent.VK_BACK_SPACE: 
					System.exit(0);
					break;
				case KeyEvent.VK_ENTER:
					f.setExtendedState(JFrame.MAXIMIZED_BOTH);
					break;
				case (KeyEvent.VK_BACK_SLASH):
					scene.get(tmp).setActive(!scene.get(tmp).isActive());
					break;
				case KeyEvent.VK_W:
					scene.getCamera().addTranslate(speed, Direction.Forward);
					break;
				case KeyEvent.VK_S:
					scene.getCamera().addTranslate(speed, Direction.Backward);
					break;
				case KeyEvent.VK_A:
					scene.getCamera().addTranslate(speed, Direction.Left);
					break;
				case KeyEvent.VK_D:
					scene.getCamera().addTranslate(speed, Direction.Right);						
					break;
				case KeyEvent.VK_Q:
					scene.getCamera().addTranslate(speed, Direction.Down);
					break;
				case KeyEvent.VK_E:
					scene.getCamera().addTranslate(speed, Direction.Up);
					break;
				case KeyEvent.VK_R:
					scene = new Scene ("scene.txt", true);
					break;
				case KeyEvent.VK_P:
					scene.get(tmp).setParent(scene.getCamera());
					break;
				case KeyEvent.VK_N:
					scene.get(tmp).setParent(null);
					break;
				case KeyEvent.VK_X:
					Animator anim = new Animator (f);
					anim.add(new Animation (scene.getCamera(), new Vec4 (0, 0, 500, true), 30));
					anim.play();
					break;
				case KeyEvent.VK_UP:
					scene.get(tmp).addTranslate(0.5, Axis.Z);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();
					break;
				case KeyEvent.VK_DOWN:
					scene.get(tmp).addTranslate(-0.5, Axis.Z);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();
					break;
				case KeyEvent.VK_LEFT:
					scene.get(tmp).addTranslate(-0.5, Axis.X);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();
					break;
				case KeyEvent.VK_RIGHT:
					scene.get(tmp).addTranslate(0.5, Axis.X);
					scene.get(tmp).getLocalTransform().getLocalPosition().print();
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
				case KeyEvent.VK_SLASH:
					wire = !wire;
					break;
				case KeyEvent.VK_SHIFT:
					shade = !shade;
					break;
				case KeyEvent.VK_CONTROL:
					debug = !debug;
					break;
				case KeyEvent.VK_MINUS:
					scene.getCamera().addRotate(1, Axis.Z);
					break;
				case KeyEvent.VK_EQUALS:
					scene.getCamera().addRotate(-1, Axis.Z);
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
				case KeyEvent.VK_SPACE:
					Animator animator = new Animator (f);
					animator.add(new Animation (scene.get(tmp), new Rotation (0, 0, 360), 2));
					animator.add(new Animation (scene.get(tmp), new Rotation (0, 0, -360), 2, 4, 1));
					animator.add(new Animation (scene.get(tmp), new Rotation (-360, 0, 0), 4, 6, 1));
					animator.add(new Animation (scene.get(tmp), new Rotation (360, 0, 0), 6, 8, 1));
					animator.play();
					break;
				}
				System.out.println(e.getKeyChar());
				f.repaint();
			}
			public void keyReleased(KeyEvent e) 
			{
			}
		});
		f.addMouseWheelListener(new MouseWheelListener() 
		{
			public void mouseWheelMoved(MouseWheelEvent e) 
			{
				scene.getCamera().setFOV(scene.getCamera().getFOV()+e.getWheelRotation());
				System.out.println(scene.getCamera().getFOV());
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
				dx = (newX - oldX)*0.1;
				dy = (newY - oldY)*0.1;
				if (SwingUtilities.isMiddleMouseButton(e)) {
					scene.getCamera().addTranslate(dx*speed, Direction.Left);
					scene.getCamera().addTranslate(dy*speed, Direction.Up);
				}
				else if (SwingUtilities.isRightMouseButton(e) && e.isAltDown()) {
					scene.getCamera().addTranslate(dx*speed + dy*speed, Direction.Forward);
				}
				else if (e.isAltDown()) {
					scene.getCamera().addTranslate(dx*speed, Direction.Left);
					scene.getCamera().addTranslate(dy*speed, Direction.Up);
					scene.getCamera().addRotate(dy, Axis.X);
					scene.getCamera().addRotate(dx, Axis.Y);
				}
				else {
					scene.getCamera().addRotate(-dy*0.5, Axis.X);
					scene.getCamera().addRotate(dx*0.5, Axis.Y);
					scene.get(tmp).addRotate(-dx, Axis.Z);
					scene.get(tmp).addRotate(-dy, Axis.X);
				}
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
		if (!mouseDown)
		{
			if (Math.rint(scene.get(tmp).getLocalTransform().getRotZ()) < 1 &&
					Math.rint(scene.get(tmp).getLocalTransform().getRotZ()) > -1) {
				scene.get(tmp).setRotate(0, Axis.Z);
			}
			if (Math.rint(scene.get(tmp).getLocalTransform().getRotZ()) > 1 ||
					Math.rint(scene.get(tmp).getLocalTransform().getRotZ()) < -1) {
				if (Math.rint(scene.get(tmp).getLocalTransform().getRotZ()) > 0) 
					scene.get(tmp).addRotate(-2, Axis.Z);
				else scene.get(tmp).addRotate(2, Axis.Z);
			}
			if (Math.rint(scene.get(tmp).getLocalTransform().getRotX()) < 1 &&
					Math.rint(scene.get(tmp).getLocalTransform().getRotX()) > -1) {
				scene.get(tmp).setRotate(0, Axis.X);
			}
			if (Math.rint(scene.get(tmp).getLocalTransform().getRotX()) > 1 ||
					Math.rint(scene.get(tmp).getLocalTransform().getRotX()) < -1) {
				if (Math.rint(scene.get(tmp).getLocalTransform().getRotX()) > 0) 
					scene.get(tmp).addRotate(-2, Axis.X);
				else scene.get(tmp).addRotate(2, Axis.X);
			}
		}
		f.repaint();
	}
}

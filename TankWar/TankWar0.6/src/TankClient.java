import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JFrame.*;

public class TankClient extends Frame {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	int x = 50, y = 50;

	Image offScreenImage = null;

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}

	public void lauchFrame() {
		setTitle("TankWar");
		setBounds(600, 200, GAME_WIDTH, GAME_HEIGHT);
		setBackground(Color.GREEN);
		setResizable(false);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		addKeyListener(new KeyMonitor());
		setVisible(true);
		new Thread(new PaintThread()).start();
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class KeyMonitor extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				x -= 5;
				break;
			case KeyEvent.VK_UP:
				y -= 5;
				break;
			case KeyEvent.VK_RIGHT:
				x += 5;
				break;
			case KeyEvent.VK_DOWN:
				y += 5;
				break;
			}
		}

	}
}

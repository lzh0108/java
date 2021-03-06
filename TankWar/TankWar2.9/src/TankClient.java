import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame {

	/**
	 * 莫名其妙的serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	Tank myTank = new Tank(350, 500, true, Direction.STOP, this);

	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();

	Wall w1 = new Wall(375, 200, 20, 150, this);
	Wall w2 = new Wall(250, 100, 300, 20, this);

	Blood b = new Blood();

	Image offScreenImage = null;

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}

	public void createEnemyTanks() {

		int initTankCount = Integer.parseInt(PropertyManager.getProperty("initTankCount"));

		for (int i = 1; i <= initTankCount; ++i) {
			tanks.add(new Tank(50 + 60 * i, 50, false, Direction.D, this));
		}
	}

	public void lauchFrame() {
		createEnemyTanks();

		setTitle("TankWar");
		setBounds(600, 200, GAME_WIDTH, GAME_HEIGHT);
		setBackground(Color.BLACK);
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
		g.setColor(Color.WHITE);
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		g.drawString("myTank    life:" + myTank.getLife(), 10, 110);
		g.setColor(c);

		myTank.draw(g);
		myTank.eat(b);

		w1.draw(g);
		w2.draw(g);

		b.draw(g);

		for (int i = 0; i < missiles.size(); ++i) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}

		for (int i = 0; i < explodes.size(); ++i) {
			Explode e = explodes.get(i);
			e.draw(g);
		}

		for (int i = 0; i < tanks.size(); ++i) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTank(tanks);
		}
	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
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
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

	}
}

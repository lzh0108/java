import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {

	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int MAXLIFE = 100;

	private static Random r = new Random();

	private int x, y;
	private int oldX, oldY;
	private int life = MAXLIFE;

	private boolean bGood;
	private boolean live = true;

	TankClient tc;

	private BloodBar bb = new BloodBar();

	private boolean bL = false, bU = false, bR = false, bD = false;

	private Direction dir = Direction.STOP;
	private Direction mDir = Direction.D;

	private int step = r.nextInt(12) + 3;

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isbGood() {
		return bGood;
	}

	public Tank(int x, int y, boolean bGood) {
		this.x = x;
		this.y = y;

		this.bGood = bGood;
	}

	public Tank(int x, int y, boolean bGood, Direction dir, TankClient tc) {
		this(x, y, bGood);
		this.dir = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!bGood) {
				tc.tanks.remove(this);
			}
			return;
		}

		Color c = g.getColor();
		if (bGood)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		if (bGood)
			bb.draw(g);

		switch (mDir) {
		case L:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y);
			break;
		case U:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y);
			break;
		case RU:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y);
			break;
		case R:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT);
			break;
		case D:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y + HEIGHT);
			break;
		case LD:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT);
			break;
		case STOP:
			break;
		}

		move();
	}

	void move() {

		oldX = x;
		oldY = y;

		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if (this.dir != Direction.STOP)
			this.mDir = this.dir;

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

		if (!bGood) {
			Direction[] dirs = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			--step;
			if (r.nextInt(40) > 38)
				this.fire();
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		case KeyEvent.VK_F2:
			if(this.live == false) {
				this.live = true;
				this.life = MAXLIFE;
			}
			break;
		}
		locateDirection();
	}

	void locateDirection() {
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void fire() {
		if (!live)
			return;

		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, bGood, mDir, this.tc);
		tc.missiles.add(m);
	}

	public void fire(Direction dir) {
		if (!live)
			return;

		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, bGood, dir, this.tc);
		tc.missiles.add(m);
	}

	private void superFire() {
		if (!live)
			return;

		Direction[] dirs = Direction.values();
		for (int i = 0; i < 8; ++i) {
			fire(dirs[i]);
		}

	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	private void stay() {
		x = oldX;
		y = oldY;
	}

	public boolean collidesWithWall(Wall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesWithTank(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); ++i) {
			Tank t = tanks.get(i);
			if (this != t && this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
				this.stay();
				t.stay();
				return true;
			}
		}
		return false;
	}

	public boolean eat(Blood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			this.life = MAXLIFE;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	private class BloodBar {

		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, WIDTH, 10);
			int w = WIDTH * life / MAXLIFE;
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
		}
	}

}

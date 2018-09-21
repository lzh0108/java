import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {

	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
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

	private static Toolkit tk = Toolkit.getDefaultToolkit();// 得到一个对不同OS进行操作的工具包
	private static Image[] tankImgs = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		tankImgs = new Image[] { 
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU1.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU1.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD1.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD1.png")) };
		imgs.put("L", tankImgs[0]);
		imgs.put("LU", tankImgs[1]);
		imgs.put("U", tankImgs[2]);
		imgs.put("RU", tankImgs[3]);
		imgs.put("R", tankImgs[4]);
		imgs.put("RD", tankImgs[5]);
		imgs.put("D", tankImgs[6]);
		imgs.put("LD", tankImgs[7]);
	}

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

		if (bGood)
			bb.draw(g);

		String str = mDir.toString();
		g.drawImage(imgs.get(str), x, y, null);

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
			if (this.live == false) {
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
			g.drawRect(x + 5, y - 10, WIDTH - 10, 10);
			int w = (WIDTH - 10) * life / MAXLIFE;
			g.fillRect(x + 5, y - 10, w, 10);
			g.setColor(c);
		}
	}

}

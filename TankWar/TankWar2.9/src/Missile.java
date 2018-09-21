import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Missile {

	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 12;
	public static final int HEIGHT = 12;

	int x, y;
	Direction dir;

	private boolean live = true;
	private boolean bGood ;

	private TankClient tc;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Missile(int x, int y, boolean bGood, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.bGood = bGood;
		this.tc = tc;
	}
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();// 得到一个对不同OS进行操作的工具包
	private static Image[] missileImgs = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		missileImgs = new Image[] { tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")) };
		imgs.put("L", missileImgs[0]);
		imgs.put("LU", missileImgs[1]);
		imgs.put("U", missileImgs[2]);
		imgs.put("RU", missileImgs[3]);
		imgs.put("R", missileImgs[4]);
		imgs.put("RD", missileImgs[5]);
		imgs.put("D", missileImgs[6]);
		imgs.put("LD", missileImgs[7]);
	}

	public boolean isLive() {
		return live;
	}

	public void draw(Graphics g) {
		if(!live)
			tc.missiles.remove(this);
		
		String str = dir.toString();
		g.drawImage(imgs.get(str), x, y, null);

		move();
	}

	private void move() {
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
		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean hitTank(Tank t) {
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.bGood != t.isbGood()) {
			if(t.isbGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0)
					t.setLive(false);
			}
			else {
				t.setLive(false);
			}
			this.live = false;
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0;i<tanks.size();++i) {
			if(hitTank(tanks.get(i)))
				return true;
		}
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}

}





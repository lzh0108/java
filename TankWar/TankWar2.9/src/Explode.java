import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {

	int x, y;
	private boolean live = true;
	private TankClient tc;

	private static Toolkit tk = Toolkit.getDefaultToolkit();// 得到一个对不同OS进行操作的工具包
	private static Image[] imgs = { tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif")) };
	private static boolean init = false;
	int step = 0;

	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		/*
		 * init解决可能由异步IO导致的图片初始时未完全加载完的情况
		 */
		if (!init) {
			for (int i = 0; i < imgs.length; ++i) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}

		if (!live) {
			tc.explodes.remove(this);
			return;
		}

		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}

		g.drawImage(imgs[step], x, y, null);
		++step;

	}

}

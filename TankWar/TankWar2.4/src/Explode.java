import java.awt.*;

public class Explode {

	int x, y;
	private boolean live = true;
	private TankClient tc;

	int[] diameter = { 3, 7, 12, 18, 25, 33, 42, 32, 16, 4 };
	int step = 0;

	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if (step == diameter.length) {
			live = false;
			step = 0;
			return;
		}

		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		++step;
		g.setColor(c);

	}

}

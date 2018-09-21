import java.awt.*;

public class Blood {

	int x, y, w, h;

	int step = 0;

	private boolean live = true;

	TankClient tc;

	private int[][] pos = { { 300, 180 }, { 320, 180 }, { 340, 180 }, { 360, 180 }, { 380, 180 }, { 400, 180 },
			{ 420, 180 }, { 440, 180 }, { 460, 180 }, { 480, 180 }, { 500, 180 }, { 500, 200 }, { 500, 220 },
			{ 500, 240 }, { 500, 260 }, { 500, 280 }, { 500, 300 }, { 500, 320 }, { 500, 340 }, { 500, 360 },
			{ 480, 360 }, { 460, 360 }, { 440, 360 }, { 420, 360 }, { 400, 360 }, { 380, 360 }, { 360, 360 },
			{ 340, 360 }, { 320, 360 }, { 300, 360 }, { 300, 340 }, { 300, 320 }, { 300, 300 }, { 300, 280 },
			{ 300, 260 }, { 300, 240 }, { 300, 220 }, { 300, 200 }, { 300, 180 } };

	Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = h = 15;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {
		if (!live)
			return;

		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);

		move();
	}

	private void move() {
		++step;
		step = step % pos.length;
		x = pos[step][0];
		y = pos[step][1];
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}

}

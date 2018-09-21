import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JFrame.*;

public class TankClient extends Frame {

	int x = 50, y = 50;

	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.lauchFrame();
	}

	public void lauchFrame() {
		setTitle("TankWar");
		setBounds(600, 200, 800, 600);
		setVisible(true);
		setBackground(Color.GREEN);
		setResizable(false);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		new Thread(new PaintThread()).start();
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		y += 5;
	}

	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

/* 将JFrame换成Frame的原因
 * 继承了Java.awt.Panel类并覆盖了paint方法的，在绘制得到的图形，界面上会一闪一闪的，换句话说，就是得到闪屏。
 * 而继承了javax.swing.JPanel类后重写paint方法的，绘制得到的图形，刷新频率比较高，图形也就比较平稳，看起来就是一个连续的过程。
 */

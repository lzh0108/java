import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JFrame.*;

public class TankClient extends JFrame {

	public static void main(String[] args) {
		new TankClient();
	}

	public TankClient() {
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
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30);
		g.setColor(c);
	}

}

// public class TankClient extends Frame {
//
// public void paint(Graphics g) {
// Color c = g.getColor();
// g.setColor(Color.RED);
// g.fillOval(50, 50, 30, 30);
// g.setColor(c);
//
// }
//
// public void lauchFrame() {
// this.setLocation(600,200);
// this.setSize(800,600);
// this.setTitle("TankWar");
// setVisible(true);
// this.setBackground(Color.GREEN);
// this.setResizable(false);
// this.addWindowListener(new WindowAdapter() {
//
// public void windowClosing(WindowEvent e) {
// System.exit(0);
// }
//
// });
// }
//
// public static void main(String[] args) {
// TankClient tc = new TankClient();
// tc.lauchFrame();
// }
// }

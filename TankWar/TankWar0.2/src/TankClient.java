import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JFrame.*;

public class TankClient {

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}

	public void lauchFrame() {
		JFrame window = new JFrame("TankWar");
		window.setBounds(600, 200, 800, 600);
		window.setVisible(true);
		window.setResizable(false);
		window.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}

}

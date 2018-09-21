import javax.swing.*;
import static javax.swing.JFrame.*;

public class TankClient {

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}

	public void lauchFrame() {
		JFrame window = new JFrame();
		window.setBounds(600, 200, 800, 600);
		window.setVisible(true);
		//window.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}

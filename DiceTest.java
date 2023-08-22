import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiceTest {
	public static void main(String args[]) {
		JFrame frame = new JFrame("Dice Test");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // gets resolution
		DiceTestPanel panel = new DiceTestPanel(screenSize.getWidth(), screenSize.getHeight()); // inputs resolution
		frame.add(panel); // panel goes in frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // window size
		frame.setUndecorated(true); // window bar
		frame.setVisible(true); // window visible
	}

}

class DiceTestPanel extends JPanel {

	// debug
	private final boolean debug = true;

	// screen
	private int screenWidth, screenHeight;

	// dice
	private final int sides = 6;
	private Dice dice;
	private int[] rolls;

	// graph
	private int buffer, width, scale;

	public DiceTestPanel(double screenWidth, double screenHeight) {
		// screen
		this.screenWidth = (int) screenWidth;
		this.screenHeight = (int) screenHeight;

		// dice
		dice = new Dice(sides);
		rolls = new int[sides];

		// graph
		buffer = this.screenWidth / 3;
		width = buffer / 6;
		scale = 1;
	}

	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screenWidth, screenHeight);

		// debug
		g.setColor(Color.BLACK);
		if (debug == true) {
			g.drawString("Buffer: " + buffer, 10, 20);
			g.drawString("Width: " + width, 10, 40);
			g.drawString("Scale: " + scale, 10, 60);
			String total = "";
			for (int amount : rolls) {
				total += amount + " ";
			}
			g.drawString("Rolls: " + total, 10, 80);
		}

		g.translate(0, 0);

		// graph
		for (int i = 0; i < rolls.length; i++) {
			int x1 = buffer + (i * width);
			int y1 = 0;
			int x2 = width;
			int y2 = rolls[i] * scale;
			g.fillRect(x1, y1, x2, y2);
		}

		// roll
		rolls[dice.roll() - 1]++;

		repaint();
	}
}

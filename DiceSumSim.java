import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiceSumSim {
	public static void main(String args[]) {
		JFrame frame = new JFrame("Dice Sum Sim");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // gets resolution
		DiceSumSimPanel panel = new DiceSumSimPanel(screenSize.getWidth(), screenSize.getHeight()); // inputs resolution
		frame.add(panel); // panel goes in frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // window size
		frame.setUndecorated(true); // window bar
		frame.setVisible(true); // window visible
	}

}

class DiceSumSimPanel extends JPanel {

	// debug
	private final boolean debug = true;

	// screen
	private int screenWidth, screenHeight;

	// dice
	private final int sides = 10;
	private final int numDice = 3;
	private Dice dice;
	private int min, max;
	private int[] rolls;

	// graph
	private int buffer, width;
	private final int vBuffer = 100;
	private double scale = 1.0;

	public DiceSumSimPanel(double screenWidth, double screenHeight) {
		// screen
		this.screenWidth = (int) screenWidth;
		this.screenHeight = (int) screenHeight;

		// dice
		dice = new Dice(sides);
		min = 1 * numDice;
		max = sides * numDice;
		rolls = new int[max - min + 1];

		// graph
		buffer = this.screenWidth / 8;
		width = (this.screenWidth - buffer * 2) / rolls.length;
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
		int x = buffer + width / 2;
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		for (int i = 0; i < rolls.length; i++) {
			g.drawString(Integer.toString(i + min), x + (i * width), vBuffer / 2);
		}
		// bars
		boolean adjust = false;
		for (int i = 0; i < rolls.length; i++) {
			int x1 = buffer + (i * width);
			int y1 = vBuffer;
			int x2 = width;
			int y2 = (int) (rolls[i] * scale);
			g.fillRect(x1, y1, x2, y2);
			if (y2 + vBuffer > screenHeight) // adjust check
				adjust = true;
		}
		
		// adjust
		if (adjust == true) {
			scale /= 2;
		}

		// roll
		int roll = 0;
		for (int i = 0; i < numDice; i++) {
			roll += dice.roll();
		}
		rolls[roll - min]++;

		repaint();
	}
}

package uk.ac.cam.rkh23.oop.tick5;

import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private World mWorld = null;

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		// Paint the background white
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int xOff = 10;
		int yOff = 15;
		if (mWorld != null) {
			g.setColor(Color.BLACK);
			g.drawString("Generation: " + mWorld.getGenerationCount(), 10, this.getHeight()-20);
			// Sample drawing statements
			int size = (int) Math.min((double) this.getWidth() / (double) mWorld.getWidth(),
					(double) this.getHeight() / (double) mWorld.getHeight());
			for (int row = 0; row < mWorld.getHeight(); row++) {
				for (int col = 0; col < mWorld.getWidth(); col++) {
					g.setColor(Color.LIGHT_GRAY);
					g.drawRect(xOff + (col * size), yOff + (row * size), size, size);
					if (mWorld.getCell(col, row)) {
						g.setColor(Color.BLACK);
						g.fillRect(xOff + (col * size), yOff + (row * size), size, size);
					}
					g.setColor(Color.LIGHT_GRAY);
					g.drawRect(xOff + (col * size), yOff + (row * size), size, size);

				}
			}
		}
	}

	public void display(World w) {
		mWorld = w;
		repaint();
	}
}
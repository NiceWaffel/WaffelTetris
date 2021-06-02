package de.waffel.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class ColoredRect {
	
	private int x;
	private int y;
	
	private Color color;
	
	public ColoredRect(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Returns true if the ColoredRect is out of bounds
	 * (i.e. the x or y is too large or too small)
	 */
	public boolean isOutOfBounds() {
		// reminder: the grid is 14 x 23 in size
		return x < 0 || x > 13 || y < 0 || y > 23;
	}
	
	/**
	 * Paints this ColoredRect with the provided {@link Graphics2D} object
	 * @param g The graphics object of the grid component
	 * @param bottomLeft This point describes the bottom left point of the grid drawing area
	 *                   relative to the graphics objects (0, 0) point
	 */
	public void paint(Graphics2D g, Point bottomLeft, int scale) {
		// We don't want to paint rects sticking out the top (after spawning)
		if(y == 23)
			return;
		
		int rectSize = 32 * scale;
		
		g.translate(bottomLeft.x, bottomLeft.y - rectSize);
		g.translate(x * rectSize, -y * rectSize);
		
		// Draw outline
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 3 * scale, rectSize);
		g.fillRect(0, rectSize - 3 * scale, rectSize, 3 * scale);
		g.fillRect(0, 0, rectSize, 3 * scale);
		g.fillRect(rectSize - 3 * scale, 0, 3 * scale, rectSize);
		
		// Draw center
		g.setColor(this.color);
		g.fillRect(7 * scale, 7 * scale, 19 * scale, 19 * scale);
		
		// Draw highlight and shadow
		g.setColor(this.color.brighter());
		g.fillPolygon(new int[] {3 * scale, 7 * scale, rectSize - 7 * scale, rectSize - 7 * scale, rectSize - 3 * scale, rectSize - 3 * scale},
				new int[] {3 * scale, 7 * scale, 7 * scale, rectSize - 7 * scale, rectSize - 3 * scale, 3 * scale}, 6);
		g.setColor(this.color.darker());
		g.fillPolygon(new int[] {3 * scale, 7 * scale, 7 * scale, rectSize - 7 * scale, rectSize - 3 * scale, 3 * scale},
				new int[] {3 * scale, 7 * scale, rectSize - 7 * scale, rectSize - 7 * scale, rectSize - 3 * scale, rectSize - 3 * scale}, 6);
		
		// Translate back
		g.translate(-x * rectSize, y * rectSize);
		g.translate(-bottomLeft.x, -bottomLeft.y + rectSize);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

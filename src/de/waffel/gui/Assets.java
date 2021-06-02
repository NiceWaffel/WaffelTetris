package de.waffel.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Assets {
	
	/**
	 * Returns a 896x800 BufferedImage filled with the background pattern
	 */
	public static BufferedImage getBackgroundPattern() {
		BufferedImage bi = new BufferedImage(896, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		Image pattern = getImageResource("bg_pattern");
		
		for(int x = 0; x < 896; x += 32) {
			for(int y = 0; y < 800; y += 32) {
				g.drawImage(pattern, x, y, 32, 32, null);
			}
		}
		g.dispose();
		return bi;
	}
	
	/**
	 * Loads a Resource from inside the assets folder as an Image object
	 * @param name the file name of the resource file, without the .png ending
	 */
	public static Image getImageResource(String name) {
		name = "/assets/" + name + ".png";
		return new ImageIcon(Assets.class.getResource(name)).getImage();
	}
	
	/**
	 * Loads a Resource from inside the assets folder as an ImageIcon object
	 * @param name the file name of the resource file, without the .png ending
	 */
	public static ImageIcon getImageIconResource(String name) {
		name = "/assets/" + name + ".png";
		return new ImageIcon(Assets.class.getResource(name));
	}
}

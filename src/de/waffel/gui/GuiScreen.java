package de.waffel.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class GuiScreen extends JPanel {

	private static final long serialVersionUID = -2735886467664260764L;
	
	public GuiScreen() {
		this.setPreferredSize(new Dimension(896, 800));
		this.setLayout(null);
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}

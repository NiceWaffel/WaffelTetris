package de.waffel.gui.screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import de.waffel.gui.Assets;
import de.waffel.gui.GuiButton;
import de.waffel.gui.GuiScreen;
import de.waffel.main.MainController;

public class MenuScreen extends GuiScreen {

	private static final long serialVersionUID = 6482773494823420284L;
	
	private GuiButton startButton;
	private GuiButton quitButton;

	public MenuScreen() {
		// Sets preferred size to 896x800
		super();
		
		startButton = new GuiButton(384, 576, 128, 64);
		startButton.setImage("text_start");
		startButton.addActionListener((ActionEvent e) -> {
			MainController.getSingleton().startGame();
		});
		this.add(startButton);

		quitButton = new GuiButton(384, 672, 128, 64);
		quitButton.setImage("text_quit");
		quitButton.addActionListener((ActionEvent e) -> {
			MainController.getSingleton().quit();
		});
		this.add(quitButton);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Assets.getBackgroundPattern(), 0, 0, null);
		
		// I probably don't have any rights to use this commercially
		g.drawImage(Assets.getImageResource("tetris_logo"), 128, 64, null);
	}
}

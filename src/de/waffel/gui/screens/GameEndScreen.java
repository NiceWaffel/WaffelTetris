package de.waffel.gui.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.waffel.gui.GuiButton;
import de.waffel.gui.GuiScreen;
import de.waffel.main.MainController;

public class GameEndScreen extends GuiScreen {

	private static final long serialVersionUID = -2481990377798140773L;

	private GuiButton toMenuButton;
	private JLabel scoreLabel;
	
	public GameEndScreen() {
		// Sets preferred size to 896x800
		super();
		
		this.setBackground(Color.WHITE);
		
		scoreLabel = new JLabel();
		scoreLabel.setBounds(0, 192, 896, 320);
		scoreLabel.setText("Your score: " + String.valueOf(MainController.getSingleton().getTetrisGame().getScore()));
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(48.0f));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(scoreLabel);

		toMenuButton = new GuiButton(384, 576, 128, 64);
		toMenuButton.setText("Back to Menu");
		toMenuButton.addDefaultBorder();
		toMenuButton.addActionListener((ActionEvent e) -> {
			MainController.getSingleton().getMainWindow().setGuiScreen(new MenuScreen());
		});
		this.add(toMenuButton);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(0x55ffffff)); // Semi-transparent black as overlay
		g.fillRect(0, 0, 896, 800);
	}

}

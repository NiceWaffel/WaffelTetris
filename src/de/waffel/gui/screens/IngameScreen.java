package de.waffel.gui.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import de.waffel.gui.Assets;
import de.waffel.gui.GuiButton;
import de.waffel.gui.GuiScreen;
import de.waffel.logic.ColoredRect;
import de.waffel.logic.Direction;
import de.waffel.logic.Rotation;
import de.waffel.main.MainController;

public class IngameScreen extends GuiScreen {
	
	private static final long serialVersionUID = 7617527132590398583L;
	
	private GuiButton quitButton;
	private GuiButton muteButton;

	public IngameScreen() {
		// Sets size to 896x800 and position to 0, 0
		super();
		
		quitButton = new GuiButton(704, 704, 128, 64);
		quitButton.setText("Back to Menu");
		quitButton.addDefaultBorder();
		quitButton.addActionListener((ActionEvent e) -> {
			MainController.getSingleton().getAudioPlayer().stop();
			MainController.getSingleton().getMainWindow().setGuiScreen(new MenuScreen());
		});
		this.add(quitButton);

		muteButton = new GuiButton(576, 704, 64, 64);
		muteButton.setImage("mute");
		muteButton.addDefaultBorder();
		muteButton.addActionListener((ActionEvent e) -> {
			// Toggle mute state of Audio
			if(MainController.getSingleton().getAudioPlayer().isPlaying()) {
				MainController.getSingleton().getAudioPlayer().stop();
				muteButton.setImage("unmute");
			} else {
				MainController.getSingleton().getAudioPlayer().playAudio();
				muteButton.setImage("mute");
			}
			IngameScreen.this.repaint(10);
		});
		this.add(muteButton);
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_UP:
						MainController.getSingleton().getTetrisGame().tryRotating(Rotation.CLOCKWISE);
						break;
					case KeyEvent.VK_DOWN:
						// Add 1 to the score if no collision happened
						boolean addScore = MainController.getSingleton().getTetrisGame().tryMoving(Direction.DOWN);
						if(addScore)
							MainController.getSingleton().getTetrisGame().addScore(1);
						break;
					case KeyEvent.VK_LEFT:
						MainController.getSingleton().getTetrisGame().tryMoving(Direction.LEFT);
						break;
					case KeyEvent.VK_RIGHT:
						MainController.getSingleton().getTetrisGame().tryMoving(Direction.RIGHT);
						break;
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw background pattern
		g.drawImage(Assets.getBackgroundPattern(), 0, 0, null);
		
		// Draw box outlines and background
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(2, 96, 104)); // Some sort of dark cyan
		g2d.fillRoundRect(32 - 8, 32 - 8, 14 * 32 + 16, 23 * 32 + 16, 5, 5); // outline thickness of 8px
		g2d.fillRoundRect(512 - 8, 32 - 8, 11 * 32 + 16, 6 * 32 + 16, 5, 5); // outline thickness of 8px
		
		g2d.setColor(new Color(205, 220, 235)); // very light cyan
		g2d.fillRect(32, 32, 14 * 32, 23 * 32);
		g2d.fillRect(512, 32, 11 * 32, 6 * 32);
		
		// Draw the current grid
		ColoredRect[][] grid = MainController.getSingleton().getTetrisGame().getTetrisGrid().getGrid();
		for(int x = 0; x < 14; x++) {
			for(int y = 0; y < 23; y++) {
				if(grid[x][y] != null)
					grid[x][y].paint(g2d, new Point(32, 768), 1);
			}
		}
		
		// Draw pieces
		MainController.getSingleton().getTetrisGame().paintCurrentPiece(g2d);
		MainController.getSingleton().getTetrisGame().paintNextPiece(g2d);
		
		// Draw Score, Level and lines
		String level = String.valueOf(MainController.getSingleton().getTetrisGame().getLevel());
		String lines = String.valueOf(MainController.getSingleton().getTetrisGame().getLines());
		String score = String.valueOf(MainController.getSingleton().getTetrisGame().getScore());
		
		g2d.setFont(g2d.getFont().deriveFont((float)48.0));
		g2d.setColor(Color.BLACK);
		
		Image levelText = Assets.getImageResource("text_level");
		Image linesText = Assets.getImageResource("text_lines");
		Image scoreText = Assets.getImageResource("text_score");
		g2d.drawImage(levelText, 544, 352 - levelText.getHeight(null), null);
		g2d.drawImage(linesText, 544, 480 - linesText.getHeight(null), null);
		g2d.drawImage(scoreText, 544, 608 - scoreText.getHeight(null), null);
		
		// Draw the actual scores. The -4 translation is just for centering
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(level, 704, 352 - 4);
		g2d.drawString(lines, 704, 480 - 4);
		g2d.drawString(score, 704, 608 - 4);
	}
}

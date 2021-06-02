package de.waffel.logic;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import de.waffel.gui.screens.GameEndScreen;
import de.waffel.main.MainController;

public class TetrisGame {

	private boolean running;
	
	private int level = 1;
	private int lines = 0;
	private int score = 0;
	
	private TetrisGrid tetrisGrid;
	private Piece currentPiece;
	private Piece nextPiece;
	
	private Random random;
	
	public TetrisGame() {
		tetrisGrid = new TetrisGrid();
		random = new Random();
		currentPiece = generateNewPiece();
		this.currentPiece.setStartingPosition();
		nextPiece = generateNewPiece();
	}
	
	public void startGameLoop() {
		running = true;
		Runnable gameLoop = new Runnable() {
			private long time;
			
			@Override
			public void run() {
				while(running) {
					try {
						Thread.sleep(100 + (1000 / level) - time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					time = System.currentTimeMillis();
					tryMoving(Direction.DOWN);
					time = System.currentTimeMillis() - time;
				}
			}
		};
		new Thread(gameLoop).start();
	}
	
	/**
	 * Tries to move by first checking for a collision.
	 * If there is a collision on a downward movement the next piece will be spawned
	 * @param direction
	 * @return true if no collision happened, false otherwise
	 */
	public boolean tryMoving(Direction direction) {
		boolean collides;
		// It would be really bad if another thread got in between the test and the action
		synchronized (MainController.getSingleton().getTetrisGame()) {
			collides = currentPiece.testCollisionMovement(direction);
			if(collides) {
				if(direction == Direction.LEFT || direction == Direction.RIGHT) {
					// If we just collide with the side wall we don't want to place the piece but instead do nothing
				} else {
					currentPiece.placeInGrid();
					tetrisGrid.checkLines();
					useNextPiece();
				}
			} else {
				currentPiece.move(direction);
			}
		}
		// Repaint the screen with a timeout of 10 milliseconds
		MainController.getSingleton().getMainWindow().getCurrentScreen().repaint(10);
		
		// Return true if we were able to move
		return !collides;
	}
	
	/**
	 * Tries to rotate by first checking for a collision.
	 * @param rotation
	 * @return true if no collision happened, false otherwise
	 */
	public boolean tryRotating(Rotation rotation) {
		boolean collides;
		// It would be really bad if another thread got in between the test and the action
		synchronized (MainController.getSingleton().getTetrisGame()) {
			collides = currentPiece.testCollisionRotation(rotation);
			if(collides) {
				// Do nothing, just prevent rotation
			} else {
				currentPiece.rotate(rotation);
			}
		}
		// Repaint the screen with a timeout of 10 milliseconds
		MainController.getSingleton().getMainWindow().getCurrentScreen().repaint(10);
		
		// Return true if we were able to move
		return !collides;
	}

	/**
	 * Use nextPiece as currentPiece
	 * If currentPiece instantly collides we have lost
	 */
	protected void useNextPiece() {
		this.currentPiece = this.nextPiece;
		this.currentPiece.setStartingPosition();
		this.nextPiece = generateNewPiece();
		if(currentPiece.testCollisionMovement(Direction.DOWN)) {
			lose();
		}
	}

	/**
	 * Ends the game, stopping the music and returning to the MenuScreen
	 */
	private void lose() {
		running = false;
		// Stop Music
		MainController.getSingleton().getAudioPlayer().stop();
		
		// Display EndGameScreen
		MainController.getSingleton().getMainWindow().setGuiScreen(new GameEndScreen());
	}

	/**
	 * Generates a random new piece and returns it
	 */
	protected Piece generateNewPiece() {
		// Generate a number between 0 and 6 to choose a piece
		int r = random.nextInt(7);
		switch (r) {
			case 0:
				return new IPiece();
			case 1:
				return new JPiece();
			case 2:
				return new LPiece();
			case 3:
				return new OPiece();
			case 4:
				return new SPiece();
			case 5:
				return new TPiece();
			case 6:
				return new ZPiece();
		}
		// never gets reached
		return null;
	}

	/**
	 * Paints the current piece into the grid box
	 * @param g The graphics object to paint with
	 */
	public void paintCurrentPiece(Graphics2D g) {
		ColoredRect[] rects = currentPiece.getCoveredRects();
		for(ColoredRect cr : rects) {
			cr.paint(g, new Point(32, 768), 1);
		}
	}

	/**
	 * Paints the next piece in the next piece box (upper right)
	 * @param g The graphics object to paint with
	 */
	public void paintNextPiece(Graphics2D g) {
		ColoredRect[] rects = nextPiece.getCoveredRects();
		for(ColoredRect cr : rects) {
			cr.paint(g, new Point(656 + (int)(64.0f * nextPiece.getNextBoxShiftX()),
					160 + (int)(64.0f * nextPiece.getNextBoxShiftY())), 2);
		}
	}

	public TetrisGrid getTetrisGrid() {
		return tetrisGrid;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void stop() {
		this.running = false;
	}

	public int getLevel() {
		return level;
	}
	
	public int getLines() {
		return lines;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int toAdd) {
		this.score += toAdd;
	}
	
	public void addLines(int lines) {
		this.lines += lines;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

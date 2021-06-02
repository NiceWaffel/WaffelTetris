package de.waffel.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.waffel.main.MainController;

public class TetrisGrid {
	
	private ColoredRect[][] grid;
	
	public TetrisGrid() {
		// The default Tetris grid is 14 x 23
		// This adds one extra line at the top for freshly spawned pieces
		grid = new ColoredRect[14][24];
	}
	
	/**
	 * Completely clear the grid (fills it will null)
	 */
	public void clearGrid() {
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid[0].length; y++) {
				// Letting the garbage collector clean those objects up
				grid[x][y] = null;
			}
		}
	}
	
	/**
	 * Checks for completely filled lines and removes them.
	 * Also adds points to the point counter
	 */
	public void checkLines() {
		boolean completeLine = true;
		List<Integer> toRemove = new ArrayList<Integer>();
		
		// Write y values of complete lines in toRemove
		for(int y = 0; y < grid[0].length; y++) {
			for(int x = 0; x < grid.length; x++) {
				if(grid[x][y] == null) {
					completeLine = false;
					break;
				}
			}
			if(completeLine)
				toRemove.add(y);
			completeLine = true;
		}
		
		// Add points according to the amount of lines completed in one move
		addScore(toRemove.size());
		
		// Remove completed lines from the grid
		// Doing this from top down so we don't need to adjust out y values
		Collections.sort(toRemove, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// Sort descending -> just switch the arguments
				return Integer.compare(o2, o1);
			}
		});
		for(Integer lineY : toRemove) {
			removeLine(lineY);
		}
	}
	
	/**
	 * Add different amounts of points to the score, depending on the current level and number
	 * of lines completed in this single move
	 * @param linesCompleted
	 */
	private void addScore(int linesCompleted) {
		// Add to score
		int level = MainController.getSingleton().getTetrisGame().getLevel();
		switch(linesCompleted) {
			case 0:
				break;
			case 1:
				MainController.getSingleton().getTetrisGame().addScore(40 * level);
				break;
			case 2:
				MainController.getSingleton().getTetrisGame().addScore(100 * level);
				break;
			case 3:
				MainController.getSingleton().getTetrisGame().addScore(300 * level);
				break;
			case 4:
				MainController.getSingleton().getTetrisGame().addScore(1200 * level);
				break;
			default:
				throw new IllegalArgumentException();
		}
		// Add to line counter
		MainController.getSingleton().getTetrisGame().addLines(linesCompleted);
		MainController.getSingleton().getTetrisGame().setLevel(
				MainController.getSingleton().getTetrisGame().getLines() / 10 + 1);
	}
	
	/**
	 * Removes the yth line counted from the bottom and moves the rest down
	 * @param y the zero based line index from the bottom
	 */
	public void removeLine(int lineY) {
		for(int x = 0; x < grid.length; x++) {
			for(int y = lineY; y < grid[0].length; y++) {
				if(y == grid[0].length - 1) {
					// If we are at the top line we just fill with null
					grid[x][y] = null;
				} else {
					// Move value one down
					grid[x][y] = grid[x][y + 1];
					if(grid[x][y] != null)
						grid[x][y].setY(y);
				}
			}
		}
	}
	
	public ColoredRect[][] getGrid() {
		return grid;
	}
}

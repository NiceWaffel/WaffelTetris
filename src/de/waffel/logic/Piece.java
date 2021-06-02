package de.waffel.logic;

import java.awt.Color;
import java.awt.Point;

import de.waffel.main.MainController;

public abstract class Piece {
	
	/**
	 * The x position of the center in the grid
	 */
	protected int x = 0;

	/**
	 * The y position of the center in the grid
	 */
	protected int y = 0;
	
	/**
	 * The direction this piece is facing (or its rotation)
	 * Default is UP
	 */
	protected Direction facing = Direction.RIGHT;
	
	/**
	 * The color of this piece
	 */
	protected Color color;
	
	public abstract void setStartingPosition();
	
	/**
	 * Checks whether the piece collides after this move would be made
	 * This collision detection includes the screen edges
	 * @param rotation
	 * @return true if a collision happens, false otherwise
	 */
	public boolean testCollisionRotation(Rotation rotation) {
		ColoredRect[][] grid = MainController.getSingleton().getTetrisGame().getTetrisGrid().getGrid();
		
		// Rotate piece, get rects and rotate back
		rotate(rotation);
		ColoredRect[] coveredRects = getCoveredRects();
		rotate(rotation.getOppositeRotation());
		
		for(ColoredRect cr : coveredRects) {
			if(cr.isOutOfBounds() || grid[cr.getX()][cr.getY()] != null) {
				// This grid cell is already occupied or out of bounds -> we have a collision
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the piece collides after this move would be made
	 * This collision detection includes the screen edges
	 * @param rotation
	 * @return true if a collision happens, false otherwise
	 */
	public boolean testCollisionMovement(Direction direction) {
		ColoredRect[][] grid = MainController.getSingleton().getTetrisGame().getTetrisGrid().getGrid();
		
		// Move piece, get rects and move back
		move(direction);
		ColoredRect[] coveredRects = getCoveredRects();
		move(direction.getOppositeDirection());

		for(ColoredRect cr : coveredRects) {
			if(cr.isOutOfBounds() || grid[cr.getX()][cr.getY()] != null) {
				// This grid cell is already occupied or out of bounds -> we have a collision
				return true;
			}
		}
		return false;
	}
	
	public void move(Direction direction) {
		switch (direction) {
			case DOWN:
				y--;
				break;
			case LEFT:
				x--;
				break;
			case RIGHT:
				x++;
				break;
			case UP:
				y++;
				break;
		}
	}

	public void rotate(Rotation rotation) {
		if(rotation == Rotation.CLOCKWISE) {
			switch (facing) {
				case DOWN:
					facing = Direction.LEFT;
					break;
				case LEFT:
					facing = Direction.UP;
					break;
				case RIGHT:
					facing = Direction.DOWN;
					break;
				case UP:
					facing = Direction.RIGHT;
					break;
			}
		} else {
			switch (facing) {
				case DOWN:
					facing = Direction.RIGHT;
					break;
				case LEFT:
					facing = Direction.DOWN;
					break;
				case RIGHT:
					facing = Direction.UP;
					break;
				case UP:
					facing = Direction.LEFT;
					break;
			}
		}
	}
	
	/**
	 * Get an array of ColoredRects covered by this piece
	 * @return a ColoredRect[] of length 4
	 */
	public abstract ColoredRect[] getCoveredRects();
	
	/**
	 * Places this piece in the grid by adding the necessary ColoredRect objects to it
	 */
	public void placeInGrid() {
		ColoredRect[][] grid = MainController.getSingleton().getTetrisGame().getTetrisGrid().getGrid();
		ColoredRect[] coveredRects = getCoveredRects();
		for(ColoredRect cr : coveredRects) {
			grid[cr.getX()][cr.getY()] = cr;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Direction getFacing() {
		return facing;
	}
	
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the amount of rects this piece needs to be shifted
	 * in the x direction in order to be centered
	 */
	protected abstract float getNextBoxShiftX();

	/**
	 * Returns the amount of rects this piece needs to be shifted
	 * in the y direction in order to be centered
	 */
	protected abstract float getNextBoxShiftY();

}

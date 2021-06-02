package de.waffel.logic;

public enum Direction {
	RIGHT,
	UP,
	LEFT,
	DOWN;
	
	public Direction getOppositeDirection() {
		switch(this) {
			case RIGHT: return LEFT;
			case UP: return DOWN;
			case LEFT: return RIGHT;
			case DOWN: return UP;
		}
		// hopefully we never get here
		return this;
	}
}

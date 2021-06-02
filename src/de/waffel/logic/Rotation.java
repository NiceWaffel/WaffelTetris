package de.waffel.logic;

public enum Rotation {
	CLOCKWISE,
	COUNTER_CLOCKWISE;
	
	public Rotation getOppositeRotation() {
		if(this == CLOCKWISE)
			return COUNTER_CLOCKWISE;
		return CLOCKWISE;
	}
}

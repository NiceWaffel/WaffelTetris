package de.waffel.logic;

import java.util.ArrayList;
import java.util.List;

public class OPiece extends Piece {

	public OPiece() {
		color = Colors.YELLOW;
	}

	@Override
	public void setStartingPosition() {
		x = 6;
		y = 22;
	}

	@Override
	public ColoredRect[] getCoveredRects() {
		List<ColoredRect> rects = new ArrayList<ColoredRect>();
		
		// Square Piece doesn't rotate, so the Rects are always the same
		rects.add(new ColoredRect(this.x, this.y, this.color));
		rects.add(new ColoredRect(this.x + 1, this.y, this.color));
		rects.add(new ColoredRect(this.x, this.y - 1, this.color));
		rects.add(new ColoredRect(this.x + 1, this.y - 1, this.color));
		
		return rects.toArray(new ColoredRect[4]);
	}

	@Override
	protected float getNextBoxShiftX() {
		return -0.5f;
	}

	@Override
	protected float getNextBoxShiftY() {
		return -0.5f;
	}
}

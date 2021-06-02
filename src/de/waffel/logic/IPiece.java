package de.waffel.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class IPiece extends Piece {
	
	public IPiece() {
		color = Colors.AQUA;
	}

	@Override
	public void setStartingPosition() {
		x = 6;
		y = 22;
	}

	@Override
	public ColoredRect[] getCoveredRects() {
		List<ColoredRect> rects = new ArrayList<ColoredRect>();
		
		// Center rect is always there, independent of the facing
		rects.add(new ColoredRect(this.x, this.y, this.color));
		
		switch(this.facing) {
			case UP:
				rects.add(new ColoredRect(this.x, this.y + 1, this.color));
				rects.add(new ColoredRect(this.x, this.y - 1, this.color));
				rects.add(new ColoredRect(this.x, this.y - 2, this.color));
				break;
			case DOWN:
				rects.add(new ColoredRect(this.x, this.y + 1, this.color));
				rects.add(new ColoredRect(this.x, this.y - 1, this.color));
				rects.add(new ColoredRect(this.x, this.y - 2, this.color));
				break;
			case LEFT:
				rects.add(new ColoredRect(this.x - 2, this.y, this.color));
				rects.add(new ColoredRect(this.x - 1, this.y, this.color));
				rects.add(new ColoredRect(this.x + 1, this.y, this.color));
				break;
			case RIGHT:
				rects.add(new ColoredRect(this.x - 1, this.y, this.color));
				rects.add(new ColoredRect(this.x + 1, this.y, this.color));
				rects.add(new ColoredRect(this.x + 2, this.y, this.color));
				break;
		}
		return rects.toArray(new ColoredRect[4]);
	}

	@Override
	protected float getNextBoxShiftX() {
		return -0.5f;
	}

	@Override
	protected float getNextBoxShiftY() {
		return 0.0f;
	}
}

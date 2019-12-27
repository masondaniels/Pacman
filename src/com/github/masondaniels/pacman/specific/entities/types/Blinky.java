package com.github.masondaniels.pacman.specific.entities.types;

import com.github.masondaniels.pacman.specific.GhostMode;
import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.specific.entities.SmartGhost;

public class Blinky extends SmartGhost {

	public Blinky(int x, int y, int width, int height, Tile[] tiles, boolean debug, Pacman pacman) {
		super(x, y, width, height, tiles, debug, pacman, 222, 0, 0);
	}

	@Override
	protected void updateGoal() {
		if (ghostMode == GhostMode.CHASE) {
			goalX = pacman.getX();
			goalY = pacman.getY();
		} else if (ghostMode == GhostMode.SCATTER) {
			goalX = 24 * 8;
			goalY = 7 * 8;
		}
	}
	
	
}
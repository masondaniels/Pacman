package com.github.masondaniels.pacman.specific.entities.types;

import com.github.masondaniels.pacman.specific.GhostMode;
import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.specific.entities.SmartGhost;

public class Clyde extends SmartGhost {

	public Clyde(int x, int y, int width, int height, Tile[] tiles, boolean debug, Pacman pacman) {
		super(x, y, width, height, tiles, debug, pacman, 255, 181, 33);
	}

	@Override
	protected void updateGoal() {
		boolean pacmanReachable = ((int) (Math.sqrt(Math.pow((10 * 8 - pacman.getX()), 2) + Math.pow((30 * 8 - pacman.getY()), 2))) < 8 * 10);
		if (ghostMode == GhostMode.CHASE) {
			if (pacmanReachable) {
				goalX = pacman.getX();
				goalY = pacman.getY();
			} else {
				goalX = 10 * 8;
				goalY = 30 * 8;
			}
		} else if (ghostMode == GhostMode.SCATTER) {
			goalX = 10 * 8;
			goalY = 30 * 8;
		}
	}

}

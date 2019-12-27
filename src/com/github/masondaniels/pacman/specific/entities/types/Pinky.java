package com.github.masondaniels.pacman.specific.entities.types;

import com.github.masondaniels.pacman.specific.GhostMode;
import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.specific.entities.SmartGhost;

public class Pinky extends SmartGhost {

	public Pinky(int x, int y, int width, int height, Tile[] tiles, boolean debug, Pacman pacman) {
		super(x, y, width, height, tiles, debug, pacman, 255, 181, 255);
	}

	@Override
	protected void updateGoal() {
		if (ghostMode == GhostMode.CHASE) {
			int fourTiles = 8 * 4;
			if (pacman.getDirection() == 0) {
				goalX = pacman.getX() - fourTiles;
				goalY = pacman.getY() - fourTiles;
			} else if (pacman.getDirection() == 1) {
				goalX = pacman.getX() - fourTiles;
				goalY = pacman.getY();
			} else if (pacman.getDirection() == 2) {
				goalX = pacman.getX();
				goalY = pacman.getY() + fourTiles;
			} else if (pacman.getDirection() == 3) {
				goalX = pacman.getX() + fourTiles;
				goalY = pacman.getY();
			}
		} else if (ghostMode == GhostMode.SCATTER) {
			goalX = 5 * 8;
			goalY = 6 * 8;
		}
	}
	
}
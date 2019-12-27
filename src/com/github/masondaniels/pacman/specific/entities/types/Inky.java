package com.github.masondaniels.pacman.specific.entities.types;

import java.awt.Graphics;

import com.github.masondaniels.pacman.specific.GhostMode;
import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.specific.entities.SmartGhost;

public class Inky extends SmartGhost {

	private Blinky blinky;
	private double cgx;
	private double cgy;

	public Inky(int x, int y, int width, int height, Tile[] tiles, boolean debug, Pacman pacman, Blinky blinky) {
		super(x, y, width, height, tiles, debug, pacman, 0, 222, 222);
		this.blinky = blinky;
	}

	@Override
	protected void updateGoal() {
		if (ghostMode == GhostMode.CHASE) {
			int twoTiles = 8 * 2;
			if (pacman.getDirection() == 0) {
				goalX = pacman.getX() - twoTiles;
				goalY = pacman.getY() - twoTiles;
			} else if (pacman.getDirection() == 1) {
				goalX = pacman.getX() - twoTiles;
				goalY = pacman.getY();
			} else if (pacman.getDirection() == 2) {
				goalX = pacman.getX();
				goalY = pacman.getY() + twoTiles;
			} else if (pacman.getDirection() == 3) {
				goalX = pacman.getX() + twoTiles;
				goalY = pacman.getY();
			}
			cgx = goalX;
			cgy = goalY;

			int xDis = (int) (goalX - blinky.getX());
			int yDis = (int) (goalY - blinky.getY());
			goalX += xDis;
			goalY += yDis;
		} else if (ghostMode == GhostMode.SCATTER) {
			goalX = 20 * 8;
			goalY = 30 * 8;
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (debug && ghostMode == GhostMode.CHASE) {
			g.setColor(white);
			g.drawRect((int) goalX, (int) goalY, 1, 1);
			g.drawLine((int) goalX, (int) goalY, (int) blinky.getX(), (int) blinky.getY());
			g.drawRect((int) cgx, (int) cgy, 1, 1);
		}
	}

}

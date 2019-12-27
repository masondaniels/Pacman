package com.github.masondaniels.pacman.specific.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import com.github.masondaniels.pacman.specific.GhostMode;
import com.github.masondaniels.pacman.specific.SpriteUtil;
import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.specific.entities.types.Pacman;

public abstract class SmartGhost extends Entity {

	protected Pacman pacman;
	protected double goalX = -1;
	protected double goalY = -1;
	protected Color debugColor;
	protected GhostMode ghostMode = GhostMode.SCATTER;

	public SmartGhost(int x, int y, int width, int height, Tile[] tiles, boolean debug, Pacman pacman, int arr, int gee,
			int bee) {
		super(x, y, width, height, tiles, debug, arr, gee, bee);
		debugColor = new Color(arr, gee, bee);
		this.pacman = pacman;
	}

	@Override
	protected void doCostumeChange(long costumeDiff, boolean moved) {
		if (costumeDiff > 60 && moved) {
			if (ghostMode == GhostMode.CHASE || ghostMode == GhostMode.SCATTER) {
				if (direction == 0) {
					currentSprite = (currentSprite != 6) ? 6 : 7;
				}
				if (direction == 1) {
					currentSprite = (currentSprite != 4) ? 4 : 5;
				}
				if (direction == 2) {
					currentSprite = (currentSprite != 2) ? 2 : 3;
				}
				if (direction == 3) {
					currentSprite = (currentSprite != 0) ? 0 : 1;
				}
				lastCostumeChange = System.currentTimeMillis();
			} else if (ghostMode == GhostMode.FRIGHTENED) {
				currentSprite = (currentSprite != 8) ? 8 : 9;
				
			}
		}

	}

	@Override
	protected void extraUpdates() {
		if (ghostMode == GhostMode.CHASE || ghostMode == GhostMode.SCATTER) {
			movePayload = 0.08;
		} else {
			movePayload = 0.04;
		}
		
	}

	@Override
	protected void initializeSprites(int arr, int gee, int bee) {
		for (int i = 0; i < 10; i++) {
			try {
				sprites.add(SpriteUtil.getGhost(arr, gee, bee, i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		super.draw(g);
		g.setColor(Color.WHITE);
		if (debug && keyPressed) {
			int distance = (int) (Math.sqrt(Math.pow((goalX - x), 2) + Math.pow((goalY - y), 2)));
			g.setColor(debugColor);
			g.drawString("Dist: " + distance, (int) x + 8, (int) y + 8);
			g.drawLine((int) x, (int) y, (int) goalX, (int) goalY);
//			g.drawLine((int) x, (int) y, (int) goalX, (int) y);
//			g.drawLine((int) x, (int) y, (int) x, (int) (int) goalY);
			g.drawRect((int) goalX, (int) goalY, 1, 1);
		}
	}

	private int lastBD = -1;
	private double lastX = -1;
	private double lastY = -1;
	private long last = System.currentTimeMillis();

	private static Random random = new Random();

	@Override
	protected void doSpecialMovement() {
		if (ghostMode == GhostMode.CHASE || ghostMode == GhostMode.SCATTER) {
			updateGoal();
		} else {
			goalX = random.nextInt(28 * 8);
			goalY = random.nextInt(36 * 8);
		}
		long diff = System.currentTimeMillis() - last;
		if (diff > (100 * (0.08 / movePayload))
				|| (((int) (x) / 8) + 28 * ((int) (y) / 8)) != (((int) (lastX) / 8) + 28 * ((int) (lastY) / 8))) {
			last = System.currentTimeMillis();
			int[] adjacentTiles = { (((int) (x) / 8) + 28 * ((int) (y) / 8)) - 28,
					(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 1, (((int) (x) / 8) + 28 * ((int) (y) / 8)) + 28,
					(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 1 };
			double payload = diff * 0.08;
			int[] predictedValues = new int[4];

			if (!(tiles[adjacentTiles[0]].getRect().intersects(x, y - payload, 0.01, 0.01))
					&& (tiles[adjacentTiles[0]].getType() == 8)) {
				predictedValues[0] = (int) (Math.sqrt(Math.pow((goalX - x), 2) + Math.pow((goalY - (y - payload)), 2)));
			} else {
				predictedValues[0] = 10000;
			}
			if (!(tiles[adjacentTiles[1]].getRect().intersects(x - payload, y, 0.01, 0.01))
					&& (tiles[adjacentTiles[1]].getType() == 8)) {
				predictedValues[1] = (int) (Math.sqrt(Math.pow((goalX - (x - payload)), 2) + Math.pow((goalY - y), 2)));
			} else {
				predictedValues[1] = 10000;
			}
			if (!(tiles[adjacentTiles[2]].getRect().intersects(x, y + payload, 0.01, 0.01))
					&& (tiles[adjacentTiles[2]].getType() == 8)) {
				predictedValues[2] = (int) (Math.sqrt(Math.pow((goalX - x), 2) + Math.pow((goalY - (y + payload)), 2)));
			} else {
				predictedValues[2] = 10000;
			}
			if (!(tiles[adjacentTiles[3]].getRect().intersects(x + payload, y, 0.01, 0.01))
					&& (tiles[adjacentTiles[3]].getType() == 8)) {
				predictedValues[3] = (int) (Math.sqrt(Math.pow((goalX - (x + payload)), 2) + Math.pow((goalY - y), 2)));
			} else {
				predictedValues[3] = 10000;
			}

			int bestDirection = -1;
			int bestValue = 10000;

			for (int i = 0; i < predictedValues.length; i++) {
				if (predictedValues[i] < bestValue) {
					if (lastX == x && lastY == y && lastBD == i) {
						continue;
					}
					bestDirection = i;
					bestValue = predictedValues[i];
				}
			}
			lastX = x;
			lastY = y;
			bufferDir = bestDirection;
			lastBD = bestDirection;
		}

	}

	protected abstract void updateGoal();

	public void setGhostMode(GhostMode ghostMode) {
		this.ghostMode = ghostMode;
	}

	public GhostMode getGhostMode() {
		return ghostMode;
	}

}

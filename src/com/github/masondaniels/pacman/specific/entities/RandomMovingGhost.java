package com.github.masondaniels.pacman.specific.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.github.masondaniels.pacman.specific.Tile;

public class RandomMovingGhost extends SmartGhost {

	private static Random random = new Random();

	public RandomMovingGhost(int x, int y, int width, int height, Tile[] tiles, int r, int g, int b) {
		super(x, y, width, height, tiles, false, null, r, g, b);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		super.draw(g);
	}

	private int lastBD = -1;
	private double lastX = -1;
	private double lastY = -1;
	private long last = System.currentTimeMillis();

	@Override
	protected void updateGoal() {
		goalX = random.nextInt(28 * 8);
		goalY = random.nextInt(36 * 8);

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

}

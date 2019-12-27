package com.github.masondaniels.pacman.specific.entities.types;

import java.awt.event.KeyEvent;
import java.io.IOException;

import com.github.masondaniels.pacman.specific.SpriteUtil;
import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.specific.entities.Entity;

public class Pacman extends Entity {

	public Pacman(int x, int y, int width, int height, Tile[] tiles, boolean debug) {
		super(x, y, width, height, tiles, debug, 0, 0, 0);
	}

	@Override
	protected void initializeSprites(int arr, int gee, int bee) {
		for (int i = 0; i < 8; i++) {
			try {
				sprites.add(SpriteUtil.getPacman(i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doCostumeChange(long costumeDiff, boolean moved) {
		if (costumeDiff > 60 && moved) {
			if (direction == 0) {
				currentSprite = (currentSprite != 3) ? 3 : 1;
			}
			if (direction == 1) {
				currentSprite = (currentSprite != 2) ? 2 : 0;
			}
			if (direction == 2) {
				currentSprite = (currentSprite != 7) ? 7 : 5;
			}
			if (direction == 3) {
				currentSprite = (currentSprite != 6) ? 6 : 4;
			}
			lastCostumeChange = System.currentTimeMillis();
		}
	}

	@Override
	protected void doSpecialMovement() {

	}
	
	/*
	 * Some notes for myself
	 * ----------------------------------
	 * Code		Key		Name	Direction
	 * ----------------------------------
	 * 87		up		W		0
	 * 65		left	A		1
	 * 83		down	S		2
	 * 68		right	D		3
	 * 
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		int d0 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) - 28;
		int d1 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) - 1;
		int d2 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) + 28;
		int d3 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) + 1;

		if (e.getKeyCode() == 87) {
			if (tiles[d0].getType() == 8) {
				direction = 0;
			} else {
				bufferDir = 0;
			}
		}
		if (e.getKeyCode() == 65) {
			if (tiles[d1].getType() == 8) {
				direction = 1;
			} else {
				bufferDir = 1;
			}
		}

		if (e.getKeyCode() == 83) {
			if (tiles[d2].getType() == 8) {
				direction = 2;
			} else {
				bufferDir = 2;
			}
		}

		if (e.getKeyCode() == 68) {
			if (tiles[d3].getType() == 8) {
				direction = 3;
			} else {
				bufferDir = 3;
			}
		}

		super.keyPressed(e);
	}

	@Override
	protected void extraUpdates() {
		tiles[((int) (x) / 8) + 28 * ((int) (y) / 8)].setPellet(false);

	}

}

package com.github.masondaniels.pacman.specific.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.github.masondaniels.pacman.specific.Tile;
import com.github.masondaniels.pacman.ui.AppObject;

public abstract class Entity extends AppObject {
	protected Tile[] tiles;
	protected int direction = 1;
	protected int bufferDir = -1;
	protected boolean debug = false;
	protected ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
	protected boolean keyPressed = false;

	protected int currentSprite = 0;

	public Entity(int x, int y, int width, int height, Tile[] tiles, boolean debug, int arr, int gee, int bee) {
		super(x, y, width, height);
		this.tiles = tiles;
		this.debug = debug;

		initializeSprites(arr, gee, bee);

	}

	protected abstract void initializeSprites(int arr, int gee, int bee);

	protected static Color cyan = new Color(0f, 1f, 1f, 0.2f);
	protected static Color orange = new Color(1f, (float) (200 / 255), 0f, 0.2f);
	protected static Color green = new Color(0f, 1f, 0f, 0.2f);
	protected static Color pink = new Color(1f, (float) (175 / 255), (float) (175 / 255), 0.2f);
	protected static Color white = new Color(1f, 1f, 1f, 0.2f);

	@Override
	public void draw(Graphics g) {
		g.drawImage(sprites.get(currentSprite), ((int) x) - sprites.get(currentSprite).getHeight() / 2,
				((int) (y)) - sprites.get(currentSprite).getWidth() / 2, null);

		if (debug) {
			g.setColor(cyan);
			if (tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 28].getType() != 8) {
				g.drawRect(tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 28].getX(),
						tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 28].getY(), 8, 8);
			}

			g.setColor(orange);
			if (tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 1].getType() != 8) {
				g.drawRect(tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 1].getX(),
						tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) - 1].getY(), 8, 8);
			}

			g.setColor(green);
			if (tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 28].getType() != 8) {
				g.drawRect(tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 28].getX(),
						tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 28].getY(), 8, 8);
			}

			g.setColor(pink);
			if (tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 1].getType() != 8) {
				g.drawRect(tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 1].getX(),
						tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8)) + 1].getY(), 8, 8);
			}

			g.setColor(white);
			if (tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8))].getType() == 8) {
				g.drawRect(tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8))].getX(),
						tiles[(((int) (x) / 8) + 28 * ((int) (y) / 8))].getY(), 8, 8);
			}
		}
	}

	protected long last = 0;
	protected boolean isMoving = false;
	protected long lastCostumeChange = 0;
	protected double movePayload = 0.08;

	@Override
	public void update(long current) {

		if (!keyPressed) {
			return;
		}

		if (last == 0) {
			last = current;
		}

		long diff = current - last;
		last = current;
		int d0 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) - 28;
		int d1 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) - 1;
		int d2 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) + 28;
		int d3 = (((int) (x) / 8) + 28 * ((int) (y) / 8)) + 1;
		if (bufferDir != -1) {
			if ((bufferDir == 0 && direction == 2) || (bufferDir == 2 && direction == 0)
					|| (bufferDir == 1 && direction == 3) || (bufferDir == 3 && direction == 1)) {
				bufferDir = -1;

			} else {
				if (bufferDir == 0 && tiles[d0].getType() == 8) {
					direction = 0;
					bufferDir = -1;
				}
				if (bufferDir == 1 && tiles[d1].getType() == 8) {
					direction = 1;
					bufferDir = -1;
				}
				if (bufferDir == 2 && tiles[d2].getType() == 8) {
					direction = 2;
					bufferDir = -1;
				}
				if (bufferDir == 3 && tiles[d3].getType() == 8) {
					direction = 3;
					bufferDir = -1;
				}
			}
		}
		double payload = (movePayload * diff);

		int centeredX = ((int) (x) / 8) * 8 + 4;
		int centeredY = ((int) (y) / 8) * 8 + 4;

		boolean moved = false;
		int amt = (int) (payload / 8) + 1;
		for (int i = 0; i < amt; i++) {
			if (direction == 0 && (tiles[d0 - 28 * i].getType() == 8)
					&& !(tiles[d0 - 28 * i].getRect().intersects(x, y - payload / amt, x, y))) {
				y -= payload / amt;
				x = centeredX;
				moved = true;
			} else if (direction == 1 && tiles[d1 - 1 * i].getType() == 8
					&& !(tiles[d1 - 1 * i].getRect().intersects(x - payload / amt, y, x, y))) {
				x -= payload / amt;
				y = centeredY;
				moved = true;
			} else if (direction == 2 && tiles[d2 + 28 * i].getType() == 8
					&& !(tiles[d2 + 28 * i].getRect().intersects(x, y + payload / amt, x, y))) {

				y += payload / amt;
				x = centeredX;
				moved = true;
			} else if (direction == 3 && tiles[d3 + 1 * i].getType() == 8
					&& !(tiles[d3 + 1 * i].getRect().intersects(x + payload / amt, y, x, y))) {
				x += payload / amt;
				y = centeredY;
				moved = true;
			}
			if (!moved) {
				break;
			}
		}

		if (!moved || payload < 0.2) {
			y = centeredY;
			x = centeredX;
		}

		if (((int) x / 8) == 27) {
			x = 8;
		} else if (((int) x / 8) == 0) {
			x = 26 * 8;
		}
		isMoving = moved;
		doSpecialMovement();

		long costumeDiff = current - lastCostumeChange;

		doCostumeChange(costumeDiff, moved);
		extraUpdates();

	}

	protected abstract void extraUpdates();

	protected abstract void doCostumeChange(long costumeDiff, boolean moved);

	protected abstract void doSpecialMovement();

	@Override
	protected void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed = true;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}

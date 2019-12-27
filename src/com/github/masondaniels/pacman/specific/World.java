package com.github.masondaniels.pacman.specific;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import com.github.masondaniels.pacman.specific.entities.Entity;
import com.github.masondaniels.pacman.specific.entities.SmartGhost;
import com.github.masondaniels.pacman.ui.AppObject;

public class World extends AppObject {

	private static BufferedImage[] images = new BufferedImage[9];
	private static BufferedImage pellet = null;
	private Tile[] tiles;
	private boolean debug;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	static {
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = SpriteUtil.getTileSprite(i + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		pellet = SpriteUtil.getPellet();
	}

	public World(Tile[] tiles, boolean debug) {
		super(0, 0, 0, 0);
		this.tiles = tiles;
		this.debug = debug;
	}

	@Override
	public void draw(Graphics g) {

		for (int i = 0; i < tiles.length; i++) {
			Tile tile = tiles[i];
			g.drawImage(images[tile.getType()], tile.getX(), tile.getY(), null);
			g.setColor(Color.WHITE);
			if (tile.hasPellet()) {
				g.drawImage(pellet, tile.getX(), tile.getY(), null);
			}
			if (debug) {
				g.setColor(new Color(1f, 0f, 0f, 0.2f));
				if (tiles[i].getType() != 8) {
					g.drawRect(tiles[i].getX(), tiles[i].getY(), 8, 8);
				}
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g);
		}

	}

	@Override
	protected void keyReleased(KeyEvent e) {

	}

	@Override
	protected void keyPressed(KeyEvent e) {
		for (Entity entity : entities) {
			entity.keyPressed(e);
		}
	}

	private long last = -1;
	private GhostMode ghostMode = GhostMode.SCATTER;

	@Override
	public void update(long current) {
		for (Entity entity : entities) {
			entity.update(current);
		}
		if (last == -1) {
			last = System.currentTimeMillis();
		}
		long difference = System.currentTimeMillis() - last;
		if (difference > 7000) {
			if (ghostMode == GhostMode.CHASE && difference > 20000) {
				ghostMode = GhostMode.SCATTER;
				last = -1;
				for (Entity entity : entities) {
					if (entity instanceof SmartGhost) {
						if (((SmartGhost) entity).getGhostMode() != GhostMode.EATEN
								&& ((SmartGhost) entity).getGhostMode() != GhostMode.FRIGHTENED) {
							((SmartGhost) entity).setGhostMode(ghostMode);
							((SmartGhost) entity).setDirection(((SmartGhost) entity).getDirection() == 0 ? 2
									: (((SmartGhost) entity).getDirection() == 2 ? 0
											: ((SmartGhost) entity).getDirection() == 1 ? 3
													: ((SmartGhost) entity).getDirection() == 3 ? 1 : -1));
						}
					}
				}
			} else if (ghostMode == GhostMode.SCATTER) {
				ghostMode = GhostMode.CHASE;
				last = -1;
				for (Entity entity : entities) {
					if (entity instanceof SmartGhost) {
						if (((SmartGhost) entity).getGhostMode() != GhostMode.EATEN
								&& ((SmartGhost) entity).getGhostMode() != GhostMode.FRIGHTENED) {
							((SmartGhost) entity).setGhostMode(ghostMode);
							((SmartGhost) entity).setDirection(((SmartGhost) entity).getDirection() == 0 ? 2
									: (((SmartGhost) entity).getDirection() == 2 ? 0
											: ((SmartGhost) entity).getDirection() == 1 ? 3
													: ((SmartGhost) entity).getDirection() == 3 ? 1 : -1));
						}
					}
				}
			}
		}

	}

	public void add(Entity entity) {
		entities.add(entity);
	}

}

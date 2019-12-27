package com.github.masondaniels.pacman.specific;

import java.awt.Rectangle;

public class Tile {

	private int x;
	private int y;
	private int type;
	private boolean containsPellet = true;
	private Rectangle rect = null;
	
	public Tile(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getRect() {
		if (rect == null) {
			rect = new Rectangle(x*8, y*8, 8, 8);
		}
		return rect;
	}
	
	public boolean hasPellet() {
		return containsPellet;
	}
	
	public void setPellet(boolean containsPellet) {
		this.containsPellet = containsPellet;
	}
	
}

package com.github.masondaniels.pacman.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AppObject extends MouseAdapter {

	/*
	 * AppObjects are pretty much an equivalent to sprites in games. Instead of
	 * relying on swing or fx entirely, I just draw everything and keep track of
	 * stuff myself.
	 */

	protected int mouseX;
	protected int mouseY;
	protected int lastMouseClickedX;
	protected int lastMouseClickedY;
	protected int width;
	protected int height;
	protected double y;
	protected double x;
	protected Rectangle hitbox;

	public abstract void draw(Graphics g);
	public abstract void update(long current);

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public AppObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hitbox = new Rectangle(x, y, width, height);
	}

	public boolean isFocused() {
		if (lastMouseClickedX != 0 && lastMouseClickedY != 0) {
			Point p = new Point((int) (lastMouseClickedX), (int) (lastMouseClickedY));
			if (new Rectangle((int) (x), (int) (y), width, height).contains(p))
				return true;
		}
		return false;
	}

	public boolean isHovered() {
		if (mouseX != 0 && mouseY != 0) {
			Point p = new Point((int) (mouseX), (int) (mouseY));
			if (new Rectangle((int) (x), (int) (y), width, height).contains(p))
				return true;
		}
		return false;
	}

	public void mouseClicked(MouseEvent e) {
		lastMouseClickedX = e.getX();
		lastMouseClickedY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseDragged(MouseEvent e) {
		lastMouseClickedX = e.getX();
		lastMouseClickedY = e.getY();
	}


	protected abstract void keyReleased(KeyEvent e);

	protected abstract void keyPressed(KeyEvent e);

}

package com.github.masondaniels.pacman.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class App extends JFrame implements Runnable {

	private ArrayList<AppObject> appObjs = new ArrayList<AppObject>();
	private static final long serialVersionUID = 1L;
	private Canvas canvas = new Canvas();

	private int canvasWidth = 0;
	private int canvasHeight = 0;

	public App(int windowWidth, int windowHeight, int canvasWidth, int canvasHeight) {

		this.canvasHeight = canvasHeight;
		this.canvasWidth = canvasWidth;
		Dimension dimension = new Dimension(windowWidth, windowHeight);
		setSize(dimension);
		canvas.setSize(dimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.setBackground(Color.BLACK);
		add(canvas);
		pack();

		setResizable(true);
		setVisible(true);

		canvas.createBufferStrategy(3);
		setLocationRelativeTo(null);
		canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// This method is left empty because it is useless
			}

			@Override
			public void keyReleased(KeyEvent e) {
				for (AppObject obj : appObjs) {
					obj.keyReleased(e);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				for (AppObject obj : appObjs) {
					obj.keyPressed(e);
				}
			}
		});
		new Thread(this).start();
	}

	/*
	 * The drawing runs on a different thread, of course.
	 */

	@Override
	public void run() {
		try {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				BufferedImage buff = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB_PRE);
				buff.createGraphics();
				BufferStrategy bs = canvas.getBufferStrategy();
				Graphics g = buff.getGraphics();
				Graphics finalg = bs.getDrawGraphics();
				g.setColor(canvas.getBackground());
				g.clearRect(0, 0, canvasWidth, canvasHeight);
				g.fillRect(0, 0, canvasWidth, canvasHeight);
				update();
				draw(g);
				g.setColor(Color.WHITE);
				g.dispose();

				/*
				 * This scaling I hate very much -- not the end result, but creating it. It took
				 * quite an amount of time to think how the scaling would work. Then I forgot
				 * about insets so I had to account for those. Int a represents the top left x
				 * coordinate where the game is drawn when scaled.
				 */

				double aspect = ((double) canvasWidth / (double) canvasHeight);
				int insets = getInsets().left + getInsets().right;
				int a = (int) (((getWidth() - insets) / 2)
						- (((aspect) * (getHeight() - getInsets().bottom - getInsets().top))) / 2);
				int scaledWidth = (getWidth() - insets) - 2 * a;
				finalg.setColor(Color.BLACK);
				finalg.fillRect(0, 0, getWidth(), getHeight());
				BufferedImage scaledInstance = BufferedImageUtil.resize(buff, scaledWidth,
						(getHeight() - getInsets().bottom - getInsets().top));
				finalg.drawImage(scaledInstance, a, 0, null);
				finalg.dispose();

				bs.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Draw objects here. AppObjs are basically the smallest units of things to draw
	 * -- sort of like sprites.
	 */

	private void draw(Graphics g) {
		synchronized (appObjs) {
			for (AppObject appObj : appObjs) {
				appObj.draw(g);
			}
		}
	}
	
	private void update() {
		synchronized (appObjs) {
			for (AppObject appObj : appObjs) {
				appObj.update(System.currentTimeMillis());
			}
		}
	}

	public void addAppObj(AppObject appObj) {
		synchronized (appObjs) {
			canvas.addMouseListener(appObj);
			canvas.addMouseMotionListener(appObj);
			appObjs.add(appObj);
		}
	}

	public void removeAppObj(AppObject appObj) {
		synchronized (appObjs) {
			canvas.removeMouseListener(appObj);
			canvas.removeMouseMotionListener(appObj);
			appObjs.remove(appObj);
		}
	}

}
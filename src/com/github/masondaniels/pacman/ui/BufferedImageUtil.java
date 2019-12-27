package com.github.masondaniels.pacman.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BufferedImageUtil {

	/*/
	 *  Source: https://stackoverflow.com/questions/9417356/bufferedimage-resize
	 *  Posted by https://stackoverflow.com/users/1132499/ocracoke
	 */
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}

}

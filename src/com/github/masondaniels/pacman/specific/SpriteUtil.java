package com.github.masondaniels.pacman.specific;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class SpriteUtil {

	/*
	 * This class is somewhat unconventional, I know. I'll probably fix the way I
	 * implement the images in the future.
	 */

	private static String tiles = "iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjEuNv1OCegAAAEqSURBVEhL7ZRbjsIwDEWRAmwAgRBIw6xg+GCxLIJFzNI8PkmdusUN8AHSICwdRXaufdv0MdOQFhq6xHvGPRolLFbexShJSjvlKw9hJace6XvNNKobNyZZLH5kvb7Idvubh7CSU4/MTNNCdb6pmKxWZ1kuTzKff+chrOTUIzPTtFBd38DxcOUMtWEMKfsp19kvxzg08vkEfcJZcpvdFWT8EOrsd2ceaiZB9AqI6vrUO/JJSnvZbNrPiH101jPUNPEJb93RvXWHPISVvLx1x6zzfaZpoTpvBMWMK+eYGMJKHpl0PPodGfwZ9kq5I9ZyXKGJ00yjuutGD0PGtTH3aJSwWPl/RoheAVFdb3+wh7zfPdyB5hZEbfgYGTasBVEbPkaGDWtB1IbnGcnsD8l9JSA7u9jQAAAAAElFTkSuQmCC";
	private static String pac = "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";
	private static String ghosts = "iVBORw0KGgoAAAANSUhEUgAAAHgAAAAMCAYAAACp13kFAAAABGdBTUEAALGPC/xhBQAAAAlwSFlzAAAOwgAADsIBFShKgAAAABh0RVh0U29mdHdhcmUAcGFpbnQubmV0IDQuMS42/U4J6AAAAVxJREFUWEftlG2OwjAMRHsV7v97r9OrZBnTybqD3Y9YIBZAeiQev6ohlphaayvmaWpEexFff5uz/uXSGvF5xp7fN/4gCh1P5BF1QeQRdUHkEXVB5BF1QeQRdUHkEXVB5BF1gR+U4j0SecR79hUdQvEPRX3l7fx5tsvDGtUVH/keo7592QGWQ3Sk5gMlf4OS795rfa2rfuRIXfH7UH5u6N4PDJzx15e5HABCVOOBYX/pGVFd8O19rqd11TfYp6P1oI93k62BWTbg/x0oOoTUeGDYl95dXfG1p3XVP8iI7weyx4j/8B/Q6+vl4aX+Uld11T/AK/r4TUcZ8f/9BUV5xiv6q4G4v1u/9v6A/3EXGuUZz/D7MJJh+aFdP6f9j7vQKM94hs9hHGHE7wdCAfQAPuM+y7WX5VnGfZZrL8uzjPss116WZxn3Wa49rH4Yvo4y7rFy73PtYbXiy7vSpl8+XsKLvX09vgAAAABJRU5ErkJggg==";
	private static String pellet = "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAYAAADED76LAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjEuNv1OCegAAABNSURBVChTY/j//z9ejFUQGWMVRMYoHCEenv+asrL/OdnYgFwsCgr8/f//2LDhv6maGpCLRYGlhsb/2sjI/9LCwkAuFgXYMFZBBP7PAACzULPV/QZ8/gAAAABJRU5ErkJggg==";

	public static BufferedImage getTileSprite(int sprite) throws IOException {
		byte[] data = Base64.getDecoder().decode(tiles);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage tileImage = ImageIO.read(bis);
		return tileImage.getSubimage((sprite % 3 == 0 ? 18 : (sprite % 2 == 0 ? 9 : 0)),
				(sprite / 3 % 3 == 0 ? 18 : (sprite / 3 % 2 == 0 ? 9 : 0)), 8, 8);
	}

	public static BufferedImage getPacman(int sprite) throws IOException {
		byte[] data = Base64.getDecoder().decode(pac);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(bis);
		return image.getSubimage(sprite * 12, 0, 12, 12);
	}

	public static BufferedImage getPellet() {
		byte[] data = Base64.getDecoder().decode(pellet);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage tileImage = null;
		try {
			tileImage = ImageIO.read(bis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tileImage;
	}

	public static BufferedImage getGhost(int arr, int gee, int bee, int sprite) throws IOException {
		byte[] data = Base64.getDecoder().decode(ghosts);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage large = ImageIO.read(bis);
		BufferedImage image = large.getSubimage(sprite * 12, 0, 12, 12);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int pixel = image.getRGB(i, j);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				if (red == 222 && green == 0 && blue == 0) {
					image.setRGB(i, j, new Color(arr, gee, bee).getRGB());
				}
			}
		}
		return image;
	}
}

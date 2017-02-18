package com.google.users.polymorpheus.experimental.arttree;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Random;

public class ArtTree {

	private static final int IMAGE_WIDTH = 2001;
	private static final int IMAGE_HEIGHT = 2001;
	private static final int NUM_IMAGES = 1000;

	private static String nameImageFile(int imageNum) {
		return String.format("Image%d.png", imageNum);
	}

	public static void main(String[] args) throws Exception {
		ArtTreeFormulaBuilder atfb = new ArtTreeFormulaBuilder(IMAGE_WIDTH, IMAGE_HEIGHT);
		for (int i = 0; i < NUM_IMAGES; i++) {
			File outFile = new File(nameImageFile(i));
			ImageIO.write(atfb.createArt(new Random(i)), "PNG", outFile);
		}
		System.exit(0);
	}

}

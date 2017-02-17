package com.google.users.polymorpheus.experimental.arttree.postprocess;

import com.google.users.polymorpheus.experimental.arttree.util.KernelWithFinalizer;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class RgbPainter extends KernelWithFinalizer {

	private final BufferedImage bitmap;
	private float[][] channelInputs;
	private final MinMaxCalculator[] minMaxCalculators = new MinMaxCalculator[3];
	private final float[] mins = new float[3];
	private final float[] scales = new float[3];
	private final int[] rgbPixels;
	private final int width;
	private final int height;
	private final int pixelCount;
	public RgbPainter(int width, int height) {
		this.width = width;
		this.height = height;
		pixelCount = width * height;
		rgbPixels = new int[pixelCount];
		for (int i=0; i<3; i++) {
			minMaxCalculators[i] = new MinMaxCalculator();
		}
		bitmap = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}

	public void init(float[]... channelInputs) {
		this.channelInputs = channelInputs;
		for (int i=0; i<3; i++) {
			minMaxCalculators[i].init(channelInputs[i]);
			mins[i] = minMaxCalculators[i].getMin();
			scales[i] = 255.0f/(minMaxCalculators[i].getMax() - mins[i]);
		}
	}
	@Override
	public void run() {
		int gid = getGlobalId();
		int rgb = 0;
		for (int i=0; i<3; i++) {
			rgb <<= 8;
			rgb += scales[i] * (channelInputs[i][gid] - mins[i]);
		}
		rgbPixels[gid] = rgb;
	}

	public RenderedImage paint() {
		put(channelInputs);
		execute(pixelCount);
		bitmap.setRGB(0, 0, width, height, rgbPixels, 0, width);
		return bitmap;
	}
}

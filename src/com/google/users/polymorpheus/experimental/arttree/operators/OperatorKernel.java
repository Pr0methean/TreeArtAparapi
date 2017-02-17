package com.google.users.polymorpheus.experimental.arttree.operators;

import com.google.users.polymorpheus.experimental.arttree.util.KernelWithFinalizer;

/**
 * Created by cryoc on 2016-07-05.
 */
public abstract class OperatorKernel extends KernelWithFinalizer {
	protected float[][] input;
	protected float[] output;
    protected int signum;

    public OperatorKernel() {
		super();
	}

	public synchronized void init(float[][] input, int pixels, int signum) {
		this.input = input;
        if (output == null) {
            output = new float[pixels];
        }
        this.signum = signum;
		put(input);
        for (float[] inputPage : input) {
            put(inputPage);
        }
		put(output);
	}

    public void cleanUp() {
        input = null;
    }

    @Override
    protected void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }

    @Override
	public void run() {
		int pixelId = getGlobalId();
		output[pixelId] = calculatePixelById(pixelId) * signum;
	}

	public synchronized float[] getOutput() {
        get(output);
		return output;
	}

	protected abstract float calculatePixelById(int pixelId);
}

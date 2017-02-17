package com.google.users.polymorpheus.experimental.arttree.postprocess;

import com.google.users.polymorpheus.experimental.arttree.util.KernelWithFinalizer;

public class MinMaxCalculator extends KernelWithFinalizer {

	private boolean finished;
	private float[] minsIn;
	private float[] maxsIn;
	private int inSize;
	private float[] minsOut;
	private float[] maxsOut;
	public MinMaxCalculator() {}

	public void init(float[] input) {
		minsIn = input;
		maxsIn = input;
		inSize = input.length;
		finished = false;
	}
	@Override
	public void run() {
		int gid = getGlobalId();
		if (gid * 2 + 1 >= inSize) {
			maxsOut[gid] = maxsIn[2 * gid];
			minsOut[gid] = minsIn[2 * gid];
		} else {
			maxsOut[gid] = max(maxsIn[2*gid], maxsIn[2*gid+1]);
			minsOut[gid] = min(minsIn[2*gid], minsIn[2*gid+1]);
		}
	}

	public float getMin() {
		execIfNecessary();
		return minsOut[0];
	}
	
	private void execIfNecessary() {
		if (!finished) {
			while (inSize > 1) {
				int outSize = (inSize + 1) / 2;
				minsOut = new float[outSize];
				maxsOut = new float[outSize];
				execute(outSize);
				minsIn = minsOut;
				maxsIn = maxsOut;
				inSize = outSize;
			}
			get(minsOut);
			get(maxsOut);
			finished = true;
		}
	}

	public float getMax() {
		execIfNecessary();
		return maxsOut[0];
	}
}

package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Objects;

public class Sine extends UnaryOperator {

    private static final ObjectPool<OperatorKernel> KERNEL_POOL = new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
        @Override
        public OperatorKernel create() throws Exception {
            return new SineKernel();
        }

        @Override
        public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
            return new DefaultPooledObject<>(obj);
        }
    }, POOL_CONFIG);
	private final float phaseShift;

	public Sine(OperatorCommonParams commonParams, float phaseShift) {
		super(commonParams, KERNEL_POOL);
		this.phaseShift = phaseShift;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sine sine = (Sine) o;
        return Float.compare(sine.phaseShift, phaseShift) == 0;
    }

    @Override
    public void extraKernelInit(OperatorKernel kernel) {
        ((SineKernel) kernel).phaseShift = phaseShift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phaseShift);
    }

    private static class SineKernel extends UnaryOperatorKernel {
		private float phaseShift;

        @Override
        public void init(float[][] input, int pixels, int signum) {
            super.init(input, pixels, signum);
        }

        @Override
        public void cleanUp() {
            super.cleanUp();
        }

        @Override
        public void run() {
            super.run();
        }

        @Override
        public float[] getOutput() {
            return super.getOutput();
        }

        @Override
        protected float calculatePixelById(int pixelId) {
            return super.calculatePixelById(pixelId);
        }

        @Override
        protected float calculatePixel(float input) {
            return sin((float) Math.PI * (input + phaseShift));
        }
	}
}

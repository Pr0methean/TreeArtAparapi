package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Objects;

public class Average extends BinaryOperator {

    private static final ObjectPool<OperatorKernel> KERNEL_POOL = new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
        @Override
        public OperatorKernel create() throws Exception {
            return new AverageKernel();
        }

        @Override
        public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
            return new DefaultPooledObject<>(obj);
        }
    }, POOL_CONFIG);

	private final float leftWeight;
	public Average(float weight, OperatorCommonParams operatorCommonParams) {
		super(operatorCommonParams, KERNEL_POOL);
		leftWeight = weight;
	}

	@Override
	public void extraKernelInit(OperatorKernel kernel) {
		AverageKernel k = (AverageKernel) kernel;
        k.leftWeight = leftWeight;
        k.rightWeight = 1.0f - leftWeight;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Average average = (Average) o;
        return Float.compare(average.leftWeight, leftWeight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), leftWeight);
    }

    private static class AverageKernel extends BinaryOperatorKernel {
		private float leftWeight;
		private float rightWeight;

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
        protected float calculatePixel(float leftInput, float rightInput) {
            return leftWeight * leftInput + rightWeight * rightInput;
        }
	}
}

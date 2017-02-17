package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Negative extends UnaryOperator {
    private static final ObjectPool<OperatorKernel> KERNEL_POOL = new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
        @Override
        public OperatorKernel create() throws Exception {
            return new NegationKernel();
        }

        @Override
        public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
            return new DefaultPooledObject<>(obj);
        }
    }, POOL_CONFIG);

	public Negative(OperatorCommonParams operatorCommonParams) {
		super(operatorCommonParams, KERNEL_POOL);
	}

    @Override
    public boolean equals(Object o) {
        return o instanceof Negative && ((Negative) o).inputOps[0].equals(inputOps[0]);
    }

    @Override
    public int hashCode() {
        return -inputOps[0].hashCode();
    }

    private static class NegationKernel extends UnaryOperatorKernel {
        @Override
        protected float calculatePixelById(int pixelId) {
            return super.calculatePixelById(pixelId);
        }

        @Override
        protected float calculatePixel(float input) {
            return input;
        }

        @Override
        public void init(float[][] input, int pixels, int signum) {
            super.init(input, pixels, signum);
        }

        @Override
		public void cleanUp() {
			input = null;
		}

        @Override
        public void run() {
            super.run();
        }

        @Override
        public float[] getOutput() {
            return super.getOutput();
        }
    }
}

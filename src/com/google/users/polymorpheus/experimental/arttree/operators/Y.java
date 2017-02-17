package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Y extends LeafOperator {

    private static final ObjectPool<OperatorKernel> KERNEL_POOL =
            new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
                @Override
                public YKernel create() throws Exception {
                    return new YKernel();
                }

                @Override
                public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
                    return new DefaultPooledObject<>(obj);
                }
            }, POOL_CONFIG);
	public Y(OperatorCommonParams operatorCommonParams) {
		super(operatorCommonParams, KERNEL_POOL);
	}

	@Override
	public void extraKernelInit(OperatorKernel kernel) {
		YKernel k = (YKernel) kernel;
        k.scale = 2.0f / (height - 1);
        k.width = width;
	}

    private static class YKernel extends OperatorKernel {
        private int width;
		private float scale;

        @Override
        public void init(float[][] input, int pixels, int signum) {
            super.init(input, pixels, signum);
        }

        /** No-op: This kernel's output is perpetually reusable. */
        @Override
        public void cleanUp() {}

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
            return floor(pixelId / width) * scale - 1.0f;
        }
	}
}

package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class X extends LeafOperator {

    private static final ObjectPool<OperatorKernel> KERNEL_POOL =
            new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
        @Override
        public XKernel create() throws Exception {
            return new XKernel();
        }

        @Override
        public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
            return new DefaultPooledObject<>(obj);
        }
    }, POOL_CONFIG);
	public X(OperatorCommonParams operatorCommonParams) {
		super(operatorCommonParams, KERNEL_POOL);
	}

	@Override
	public void extraKernelInit(OperatorKernel kernel) {
        XKernel xKernel = (XKernel) kernel;
		xKernel.width = width;
        xKernel.scale = 2.0f / (width - 1);
	}

    private static class XKernel extends OperatorKernel {
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
            return (pixelId % width) * scale - 1.0f;
        }
	}
}

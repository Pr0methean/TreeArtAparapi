package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class CubeRoot extends UnaryOperator {

	private static final ObjectPool<OperatorKernel> KERNEL_POOL = new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
		@Override
		public OperatorKernel create() throws Exception {
			return new CubeRootKernel();
		}

		@Override
		public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
			return new DefaultPooledObject<>(obj);
		}
	}, POOL_CONFIG);

	public CubeRoot(OperatorCommonParams operatorCommonParams) {
		super(operatorCommonParams, KERNEL_POOL);
	}

	private static class CubeRootKernel extends UnaryOperatorKernel {
		private static final float ONE_THIRD = 1.0f / 3.0f;

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
            return pow(abs(input), ONE_THIRD) * (input < 0 ? -1 : 1);
        }
	}
}

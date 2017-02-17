package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Product extends BinaryOperator {

	private static final ObjectPool<OperatorKernel> KERNEL_POOL = new GenericObjectPool<>(new BasePooledObjectFactory<OperatorKernel>() {
        @Override
        public OperatorKernel create() throws Exception {
            return new MultiplicationKernel();
        }

        @Override
        public PooledObject<OperatorKernel> wrap(OperatorKernel obj) {
            return new DefaultPooledObject<>(obj);
        }
    }, POOL_CONFIG);

	public Product(OperatorCommonParams operatorCommonParams) {
		super(operatorCommonParams, KERNEL_POOL);
	}

	private static class MultiplicationKernel extends BinaryOperatorKernel {
        @Override
        protected float calculatePixelById(int pixelId) {
            return super.calculatePixelById(pixelId);
        }

        @Override
        protected float calculatePixel(float leftInput, float rightInput) {
            return leftInput * rightInput;
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

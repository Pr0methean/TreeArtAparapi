package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;

public abstract class Operator {

    protected static final GenericObjectPoolConfig POOL_CONFIG = new GenericObjectPoolConfig();

    static {
        POOL_CONFIG.setMaxWaitMillis(100_000);
        POOL_CONFIG.setMaxTotal(8);
        POOL_CONFIG.setBlockWhenExhausted(true);
    }
	private final ObjectPool<OperatorKernel> kernelPool;

	private final int signum;
	protected final int width;
	protected final int height;
	protected final int pixels;
	protected final Deque<float[]> operandStack;
	protected float[][] inputs;

	private SoftReference<float[]> outputRef = new SoftReference<>(null);
	protected final Operator[] inputOps;

	public Operator(OperatorCommonParams parameterObject, ObjectPool<OperatorKernel> kernelPool) {
		operandStack = parameterObject.getOperandStack();
		width = parameterObject.getWidth();
		height = parameterObject.getHeight();
		signum = parameterObject.shallNegateOutput() ? -1 : 1;
		pixels = parameterObject.getWidth() * parameterObject.getHeight();
		inputOps = parameterObject.getInputOps();
        this.kernelPool = kernelPool;
    }

	public synchronized float[] getOutput() {
        float[] output = outputRef.get();
        if (output == null) {
            OperatorKernel kernel;
            try {
                kernel = kernelPool.borrowObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (Operator inputOp : inputOps) {
                operandStack.push(inputOp.getOutput());
            }
            popInputs();
            kernel.init(inputs, pixels, signum);
            extraKernelInit(kernel);
            kernel.execute(pixels);
            kernel.cleanUp();
            output = kernel.getOutput();
            operandStack.push(output);
            outputRef = new SoftReference<>(output);
            try {
                kernelPool.returnObject(kernel);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return output;
        /*
        float[] outputCopy = new float[pixels];
        System.arraycopy(output, 0, outputCopy, 0, pixels);
		return outputCopy;
		*/
	}

    /**
     * No-op by default. Override if the kernel takes a scalar parameter.
     * @param kernel  The kernel about to be executed.
     */
    protected void extraKernelInit(OperatorKernel kernel) {
    }

    protected abstract void popInputs();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operator operator = (Operator) o;

        return signum == operator.signum
                && width == operator.width
                && height == operator.height
                && Arrays.deepEquals(inputOps, operator.inputOps);

    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), signum, width, height, Arrays.hashCode(inputOps));
    }
}

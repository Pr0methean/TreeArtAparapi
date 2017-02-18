package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.ObjectPool;

public abstract class BinaryOperator extends Operator {

	public BinaryOperator(OperatorCommonParams commonParams, ObjectPool<OperatorKernel> kernelPool) {
		super(commonParams, kernelPool);
        inputs = new float[2][pixels];
		if (inputOps.length < 2) {
		  throw new RuntimeException(toString() + ": expected 2 inputs");
		}
	}

	@Override
	protected void popInputs() {
		System.arraycopy(operandStack.pop(), 0, inputs[0], 0, pixels);
		System.arraycopy(operandStack.pop(), 0, inputs[1], 0, pixels);
	}
}

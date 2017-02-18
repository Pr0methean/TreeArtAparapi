package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.ObjectPool;

public abstract class UnaryOperator extends Operator {

	public UnaryOperator(OperatorCommonParams operatorCommonParams, ObjectPool<OperatorKernel> kernelPool) {
		super(operatorCommonParams, kernelPool);
	    inputs = new float[1][pixels];
	    if (inputOps.length < 1) {
	        throw new RuntimeException(toString() + ": expected 1 inputs");
	    }
	}

	@Override
	protected void popInputs() {
		System.arraycopy(operandStack.pop(), 0, inputs[0], 0, pixels);
	}
}

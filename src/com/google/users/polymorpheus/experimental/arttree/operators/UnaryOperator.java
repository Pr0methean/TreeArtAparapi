package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.ObjectPool;

public abstract class UnaryOperator extends Operator {

	public UnaryOperator(OperatorCommonParams operatorCommonParams, ObjectPool<OperatorKernel> kernelPool) {
		super(operatorCommonParams, kernelPool);
	}

	@Override
	protected void popInputs() {
		inputs = new float[1][pixels];
		System.arraycopy(operandStack.pop(), 0, inputs[0], 0, pixels);
	}
}

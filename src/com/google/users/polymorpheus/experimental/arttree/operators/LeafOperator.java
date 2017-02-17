package com.google.users.polymorpheus.experimental.arttree.operators;

import org.apache.commons.pool2.ObjectPool;

public abstract class LeafOperator extends Operator {

    // private static final float[][] NO_INPUTS = new float[0][];
	@Override
	protected void popInputs() {
		inputs = new float[0][];
	}

	public LeafOperator(OperatorCommonParams operatorCommonParams, ObjectPool<OperatorKernel> kernelPool) {
		super(operatorCommonParams, kernelPool);
	}
}

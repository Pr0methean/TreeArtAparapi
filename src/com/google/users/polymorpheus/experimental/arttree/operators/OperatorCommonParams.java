package com.google.users.polymorpheus.experimental.arttree.operators;

import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;

public class OperatorCommonParams {
	private final int width;
	private final int height;
	private final boolean negateOutput;
	private final Operator[] inputOps;
	private final Deque<float[]> operandStack;

	public OperatorCommonParams(Deque<float[]> operandStack, int width, int height, boolean negateOutput,
			Operator... inputOps) {
		this.operandStack = operandStack;
		this.width = width;
		this.height = height;
		this.negateOutput = negateOutput;
		this.inputOps = inputOps;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean shallNegateOutput() {
		return negateOutput;
	}

	public Operator[] getInputOps() {
		return inputOps;
	}

	public Deque<float[]> getOperandStack() {
		return operandStack;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OperatorCommonParams that = (OperatorCommonParams) o;

		return width == that.width
				&& height == that.height
				&& negateOutput == that.negateOutput
				&& Arrays.deepEquals(inputOps, that.inputOps);

	}

	@Override
	public int hashCode() {
		return Objects.hash(width, height, negateOutput, Arrays.hashCode(inputOps));
	}
}
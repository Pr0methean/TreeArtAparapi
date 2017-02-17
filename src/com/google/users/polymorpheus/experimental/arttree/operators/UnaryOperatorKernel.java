package com.google.users.polymorpheus.experimental.arttree.operators;

/**
 * An {@link OperatorKernel} with 1 per-pixel operand.
 */
public abstract class UnaryOperatorKernel extends OperatorKernel {
    @Override
    protected float calculatePixelById(int pixelId) {
        return calculatePixel(input[0][pixelId]);
    }
    protected abstract float calculatePixel(float input);
}

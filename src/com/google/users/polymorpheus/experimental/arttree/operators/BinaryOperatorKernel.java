package com.google.users.polymorpheus.experimental.arttree.operators;

/**
 * An {@link OperatorKernel} with 2 per-pixel operands.
 */
public abstract class BinaryOperatorKernel extends OperatorKernel {
    @Override
    protected float calculatePixelById(int pixelId) {
        return calculatePixel(input[0][pixelId], input[1][pixelId]);
    }
    protected abstract float calculatePixel(float leftInput, float rightInput);
}

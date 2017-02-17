package com.google.users.polymorpheus.experimental.arttree;

import com.google.users.polymorpheus.experimental.arttree.operators.*;
import com.google.users.polymorpheus.experimental.arttree.postprocess.RgbPainter;

import java.awt.image.RenderedImage;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class ArtTreeFormulaBuilder {
	private final int width;
	private final int height;
    private final Operator[] xy;
    private final Operator[] negXy;
	private final RgbPainter painter;
	private final Deque<float[]> operandStack = new ArrayDeque<>(20);

	public ArtTreeFormulaBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		X x = new X(leafParams(false));
		Y y = new Y(leafParams(false));
		xy = new Operator[]{x, y};
		X minusX = new X(leafParams(true));
		Y minusY = new Y(leafParams(true));
		negXy = new Operator[]{minusX, minusY};
		painter = new RgbPainter(width, height);
	}

	private OperatorCommonParams leafParams(boolean negate) {
		return new OperatorCommonParams(operandStack, this.width, this.height, negate);
	}

	private double probabilityOfTerminal(int depth) {
		if (depth < 2) {
			return 0;
		} else if (depth < 10) {
			return 0.1f*(depth - 1);
		} else {
			return 0.95f;
		}
	}

	private Operator createNode(Random prng, int depth, Operator[] terminals, Operator[] negTerminals) {
		boolean negate = prng.nextBoolean();
		if (prng.nextDouble() < probabilityOfTerminal(depth)) {
			int terminalIndex = prng.nextInt(terminals.length);
			return negate ? negTerminals[terminalIndex] : terminals[terminalIndex];
		} else {
			switch (prng.nextInt(6)) {
				case 0:
				case 1:
					return new Sine(unaryParams(prng, depth, terminals, negTerminals, negate), prng.nextFloat());
				case 2:
				case 3:
					return new CubeRoot(unaryParams(prng, depth, terminals, negTerminals, negate));
				case 4:
					return new Average(prng.nextFloat() * 0.7f + 0.15f, binaryParams(prng, depth, terminals, negTerminals, negate));
				case 5:
					return new Product(binaryParams(prng, depth, terminals, negTerminals, negate));
			}
		}
		throw new RuntimeException("Unreachable code");
	}

	private OperatorCommonParams binaryParams(Random prng, int depth, Operator[] terminals, Operator[] negTerminals,
                                              boolean negate) {
		return new OperatorCommonParams(operandStack, width, height, negate, createNode(prng, depth + 1, terminals, negTerminals), createNode(prng, depth + 1, terminals, negTerminals));
	}

	private OperatorCommonParams unaryParams(Random prng, int depth, Operator[] terminals, Operator[] negTerminals, boolean negate) {
		return new OperatorCommonParams(operandStack, width, height, negate, createNode(prng, depth + 1, terminals, negTerminals));
	}

	public RenderedImage createArt(Random prng) {
		Operator[] uvw = new Operator[3];
		Operator[] negUvw = new Operator[3];
		for (int i=0; i<3; i++) {
			uvw[i] = createNode(prng, 0, xy, negXy);
			negUvw[i] = new Negative(new OperatorCommonParams(operandStack, width, height, true, uvw[i]));
		}
		painter.init(
				createNode(prng, 2, uvw, negUvw).getOutput(),
				createNode(prng, 2, uvw, negUvw).getOutput(),
				createNode(prng, 2, uvw, negUvw).getOutput());
		return painter.paint();
	}
}

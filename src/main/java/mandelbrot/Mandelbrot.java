/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 */

package mandelbrot;

import java.util.function.DoubleBinaryOperator;

/**
 * Computes the Mandelbrot function that associates, to each point x+i*y of the complex plane, an integer value as explained in the associate TP sheet
 * This record class implements the functional interface DoubleBinaryOperator whose functional method is applyAsDouble
 * @param threshold represents the maximal number of iterations before we consider it will never diverge
 * @author Matthieu Perrin
 */
public record Mandelbrot(int threshold) implements DoubleBinaryOperator {
	
	@Override
	public double applyAsDouble(double x, double y) {
		if(isInMainCardioid(x, y) || isInMainCircle(x, y)) return -1;
		double x1 = x, y1 = y;
		for(int k = 0; k < threshold; k++) {
			double x2 = x1 * x1 - y1 * y1 + x;
			y1 = 2 * x1 * y1 + y;
			x1 = x2;
			if(x1 * x1 + y1 * y1 >= 4) return k;
		}
		return -1;
	}

	private boolean isInMainCardioid(double x, double y){
		double p2 = (x - .25) * (x - .25) + y*y;
		double p = Math.sqrt(p2);
		return x < p - 2*p2 + .25;
	}

	private boolean isInMainCircle(double x, double y){
		return (x+1)*(x+1) + y*y < .25*.25;
	}

	public static Area sideOfCardioid() {
		return new Area(-0.65, -0.72, 0.33, 0.22);
	}

	public static Area biggestCircle() {
		return new Area(-1.5, -0.1, 0.3, 0.2);
	}

	public static Area leftReplicate() {
		return new Area(-1.5, -0.01, 0.03, 0.02);
	}

	public static Area fullSet() {
		return new Area(-2.25, -1, 3, 2);
	}

	public static Area squeezed() {
		return new Area(-0.122, 0.643, 0.03, 0.02);
	}

}

/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 */

package mandelbrot;

import java.awt.Color;
import java.util.function.UnaryOperator;

/**
 * Represents a function in charge of mapping double values to colors on a screen
 * 
 * This is a functional interface whose functional method is Color colorize(double).
 * @author Matthieu Perrin
 */
@FunctionalInterface
public interface ColorPalette {

	/**
	 * Associates a color to a double value
	 * @param i value that was typically obtained by evaluating a ComplexFunction object at a point on the screen
	 * @return the color in which the data will be drawn on the screen
	 */
	Color colorize(double i);

	/**
	 * Provides a new ColorPalette object that behaves like this on positive inputs, 
	 * and returns the specified color for negative inputs
	 * @param colorNegative the color to use when the double input value is negative
	 * @return a new ColorPalette object
	 */
	default ColorPalette withNegative(Color colorNegative) {
		return i -> i < 0 ? colorNegative : colorize(i);
	}

	/**
	 * Provides a new ColorPalette object that behaves like this on positive inputs, 
	 * and returns the specified color for negative output
	 * @param colorNegative the color to use when the double input value is negative
	 * @return a new ColorPalette object
	 */
	default ColorPalette withFilter(UnaryOperator<Color> filter) {
		return i-> filter.apply(colorize(i));
	}

	/**
	 * Creates a new ColorPalette that is a brighter version of this ColorPalette.
	 * @see Color.brighter for more information
	 */
	default ColorPalette brighter() {
		return withFilter(Color::brighter);
	}

	/**
	 * Creates a new ColorPalette that is a darker version of this ColorPalette.
	 * @see Color.darker for more information
	 */
	default ColorPalette darker() {
		return withFilter(Color::darker);
	}

	/**
	 * Provides a new ColorPalette object that returns black on negative input, 
	 * and a gradient from the specified color to black on positive values, 
	 * such that the specified color is used for 0, 
	 * and the color tends to black when the input tends to infinity
	 * @param color The color to use when the input is 0
	 * @return a new ColorPalette object
	 */
	static ColorPalette gradient(Color color) {
		return i -> {
			if (i < 0) return Color.BLACK;
			int red = (int) Math.min(255, color.getRed() * i / 50);
			int green = (int) Math.min(255, color.getGreen() * i / 50);
			int blue = (int) Math.min(255, color.getBlue()  * i / 50);
			return new Color(red, green, blue);
		};
	}

	/**
	 * Provides a new ColorPalette object that cycles through the given list of colors depending on the integer part of the input value
	 * @param map a variadic list of colors the palette cycles through
	 * @return a new ColorPalette object
	 */
	static ColorPalette mapping(Color... map) {
		return i -> {
			double decimal = i-(int)i;
			var color1 = map[(int)i%map.length];
			var color2 = map[(int)i%map.length];
			int red = (int) (color1.getRed() * (1-decimal) + color2.getRed() * decimal);
			int green = (int) (color1.getGreen() * (1-decimal) + color2.getGreen() * decimal);
			int blue = (int) (color1.getBlue() * (1-decimal) + color2.getBlue() * decimal);
			return new Color(red, green, blue);
			
		};
	}

	/**
	 * A default ColorPalette object often used to represent the Mandelbrot set
	 */
	public static ColorPalette ULTRA = 
			mapping(
					new Color(66, 30, 15),
					new Color(25, 7, 26),
					new Color(9, 1, 47),
					new Color(4, 4, 73),
					new Color(0, 7, 100),
					new Color(12, 44, 138),
					new Color(24, 82, 177),
					new Color(57, 125, 209),
					new Color(134, 181, 229),
					new Color(211, 236, 248),
					new Color(241, 233, 191),
					new Color(248, 201, 95),
					new Color(255, 170, 0),
					new Color(204, 128, 0),
					new Color(153, 87, 0),
					new Color(106, 52, 3))
			.withNegative(Color.BLACK);

}

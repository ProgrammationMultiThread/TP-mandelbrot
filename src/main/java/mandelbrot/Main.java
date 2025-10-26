/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 */

package mandelbrot;

import java.awt.*;

/**
 * @author Matthieu Perrin
 */
public class Main {	

	public static void main(String[] args) {

		// TODO: Tweak the following settings to find a difficulty that 
		// makes the computation more interesting to observe on your machine
		// (between 10 and 30 seconds)

		// the math function that needs to be computed
		// Keep the threshold low for the naive server below
		// Increase the threshold to 100000 after parallelization for a prettier image
		var function = new Mandelbrot(1000);

		// size of a square block in pixel
		// affects the number of tasks for parallelization
		int blockSize = 1000;

		// area of the complex state that will be drawn
		// divided into a collection of smaller areas to allow parallelism
		// @see Mandelbrot for other areas of interest
		var areas = Mandelbrot.fullSet().split(blockSize, blockSize);

		// affects the picture's colors at the end
		// should barely impact the computation time
		// @see ColorPalette for other palettes
		var palette = ColorPalette.gradient(new Color(240, 160, 80)).brighter().brighter();

		// A naive, *barely working* synchronous implementation of Drawer, 
		// that must only be used as a place holder for an asynchronous, parallel, implementation
		// TODO: This naive drawer has to be replaced as explained in the assignment document
		// create three new classes that implement the Drawer interface:
		//   - one that computes the image in a separate thread
		//   - one that parallelizes the computation into one thread per Area object
		//   - one that parallelizes the computation into n threads
		Drawer drawer = new SequentialDrawer(function, areas, palette);
		
		// Creates the window on which the picture will be drawn
		new Client(drawer).setVisible(true);
	}

}

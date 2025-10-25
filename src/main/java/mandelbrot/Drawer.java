/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 */

package mandelbrot;

import java.awt.Graphics2D;

/**
 * Manages the computation of the images, and the drawing of the image to the panel
 * @author Matthieu Perrin
 * TODO: That is the interface that must be implemented twice in the exercise
 */
@FunctionalInterface
public interface Drawer {

	/**
	 * Draws the represented image on a given panel
	 * This method is called each time the panel is refreshed
	 * @param graphics object that describes the panel on which to draw
	 * @return true if the drawing was successful, false otherwise
	 * In an asynchronous implementation, it is expected that the return value remains false until the image has been fully rendered
	 */
	public boolean draw(Graphics2D graphics) throws InterruptedException ;

}

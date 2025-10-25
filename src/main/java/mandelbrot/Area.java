/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 */

package mandelbrot;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.DoubleBinaryOperator;

/**
 * Represents the following pair of rectangles:
 *   - pixels: a rectangle integer coordinates representing of set of pixels on the screen;
 *   - geometry: a rectangle of double coordinates representing a subset of the complex plane.
 * This class contains methods to convert coordinates from the screen plane to the complex plane, and back. 
 * @author Matthieu Perrin
 */
public record Area(Rectangle pixels, Rectangle2D geometry) {

	/**
	 * Represents the size of the screen, in pixels
	 */
	public final static Rectangle SCREEN_RECTANGLE;

	static {
		Rectangle envDimension = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		SCREEN_RECTANGLE = new Rectangle(0, 0, (int)envDimension.getWidth(), (int)envDimension.getHeight());
	}

	/**
	 * Creates an area representing the specified portion of the complex plane, with maximal dimension on the screen
	 * @param x minimal abscissa on the complex plane
	 * @param y minimal ordinate on the complex plane
	 * @param width width on the complex plane
	 * @param height height on the complex plane
	 */
	public Area(double x, double y, double width, double height) {
		this(SCREEN_RECTANGLE, new Rectangle2D.Double(x, y, width, height));
	}

	public double xPixelToGeometry(int abscissa) {
		return geometry.getX() + (abscissa - pixels.x) * geometry.getWidth() / pixels.getWidth();	
	}

	public double yPixelToGeometry(int ordinate) {
		return geometry.getY() + (ordinate - pixels.y) * geometry.getHeight() / pixels.getHeight();	
	}

	public int xGeometryToPixel(double abscissa) {
		return (int) (pixels.x + (abscissa - geometry.getX()) * pixels.getWidth() / geometry.getWidth());
	}

	public int yGeometryToPixel(double ordinate) {
		return (int) (pixels.y + (ordinate - geometry.getY()) * pixels.getHeight() / geometry.getHeight());
	}
	
	/**
	 * Divides the area into a collection of smaller areas of the specified size. 
	 * @param width width of the smaller areas, in pixels
	 * @param height height of the smaller areas, in pixels
	 * @return a collection of non-overlapping areas that occupy the specified size on the screen, 
	 *         and that form a partition of the initial rectangle of the complex plane 
	 *         (except on the sides if the division is not exact)
	 */
	public Collection<Area> split(int width, int height) {
		Collection<Area> areas = new ArrayList<>();
		for(int i = pixels.x; i < pixels.getMaxX(); i+=width) {
			for(int j = pixels.y; j < pixels.getMaxY(); j+=height) {
				Rectangle newPixels = new Rectangle(i, j, width, height);
				double gWidth = geometry.getWidth() * width / pixels.width;
				double gHeight = geometry.getHeight() * height / pixels.height;
				Rectangle2D newGeometry = new Rectangle2D.Double(xPixelToGeometry(i), yPixelToGeometry(j), gWidth, gHeight);
				areas.add(new Area(newPixels, newGeometry));
			}
		}
		return areas;
	}

	/**
	 * produces an image representing the values taken by a given function at all points of the area
	 * @param function the complex function to draw
	 * @param colorPalette the ColorPalette object defining which color must be drawn on the final picture, depending on the value of the function
	 * @return an image representing the function
	 */
	public Image getImage(DoubleBinaryOperator function, ColorPalette colorPalette) {
		BufferedImage image = new BufferedImage(pixels.width, pixels.height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i < pixels.width; ++i) {
			for(int j = 0; j < pixels.height; ++j) {
				double x = xPixelToGeometry(i+pixels.x);
				double y = yPixelToGeometry(j+pixels.y);
				var color = colorPalette.colorize(function.applyAsDouble(x, y));
				image.setRGB(i, j, color.getRGB());
			}
		}
		return image;
	}
	
	/**
	 * Draws a given image to a given Graphics2D canvas, at the position of the area
	 * @param graphics the canvas where to draw the image
	 * @param image the image to draw
	 * @return true if the drawing was successful, false otherwise
	 */
	public boolean drawImage(Graphics2D graphics, Image image) {
		return graphics.drawImage(image, pixels.x, pixels.y, null);
	}
	
	
}


/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 */

package mandelbrot;

import java.awt.*;

import javax.swing.*;

/**
 * @author Matthieu Perrin
 */
public class Client extends JFrame {	

	private volatile boolean isDone = false;
	private final long startTime;
	private static final long serialVersionUID = 1L;

	public Client(Drawer drawer) {
		startTime = System.currentTimeMillis();
		var timer = new Timer(100, event -> repaint());
		timer.start();
		setSize(Area.SCREEN_RECTANGLE.width, Area.SCREEN_RECTANGLE.height);
		setTitle("The Mandelbrot Set");
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics graphics) {
				try {
					if(drawer.draw((Graphics2D) graphics) && !isDone) {
						isDone = true;
						long endTime = System.currentTimeMillis();
						System.out.println("execution time = " + (endTime - startTime)/1000. + " seconds");
					}
				} catch (InterruptedException e) { }
			}
		});
	}


}

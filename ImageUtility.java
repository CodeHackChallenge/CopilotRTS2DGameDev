package demo.main;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

public class ImageUtility {

	public ImageUtility() {
		// TODO Auto-generated constructor stub
	}

	public static Image createCompatibleImage(int width, int height, int alpha) {
		   GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
	                .getDefaultScreenDevice()
	                .getDefaultConfiguration();

	        return graphicsConfiguration.createCompatibleImage(width, height, alpha);

		}

}


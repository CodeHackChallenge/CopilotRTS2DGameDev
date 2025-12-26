package demo.main;

import java.awt.Color;
import java.awt.Image; 
 

public class Sprite implements Component {

	//private int width, height;
	private Image image;
	
	public Sprite(Color color, int width, int height) {
		// this.width = width;
		 //this.height = height;
		 
		 loadImage(color, width, height, ImageLoader.ALPHA_OPAQUE);
	}

	private void loadImage(Color color, int width, int height, int alphaOpaque) {
		 ImageLoader image = new ImageLoader();
		 this.image = image.createImage(color, width, height, alphaOpaque);
		
	}
	
	public Image getImage() {
		return image;
	}

}
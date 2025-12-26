package demo.main;
 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public class ImageLoader {
    public static final int ALPHA_OPAQUE = 1;
    public static final int ALPHA_BIT_MASKED = 2;
    public static final int ALPHA_BLEND = 3;
    
	public ImageLoader() {
		 
	}
	
	public Image createImage(Color color, int width, int height, int alpha) {
		Image image = ImageUtility.createCompatibleImage(width, height, alpha);
		Graphics2D graphics2D = (Graphics2D) image.getGraphics();
		
		//graphics.drawImage(" ", 0, 0, null);
		
		graphics2D.setColor(color);
		graphics2D.fillRect(0, 0, width, height);
		
		graphics2D.dispose();
		
		return image;
	}
	
	
	public static Image createBar(Color color) { //TODO: add color to this argument 
		Image bar = ImageUtility.createCompatibleImage(4, 6, ALPHA_OPAQUE);
		Graphics2D graphics2D = (Graphics2D) bar.getGraphics(); 
		 
		graphics2D.setColor(color); 
		graphics2D.fillRect(0, 0, bar.getWidth(null), bar.getHeight(null)); 
		 		
		graphics2D.dispose();
		
		return bar;
		
	}
	
	 
	
}

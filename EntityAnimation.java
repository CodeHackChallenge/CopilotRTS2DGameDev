package demo.main;

import java.awt.image.BufferedImage;
 

public class EntityAnimation implements Component {

	private BufferedImage[] frames;
	private Animation animation;
	private int delay;
	
	public EntityAnimation(String path, int delay) {
		this.delay = delay;
		
		frames = new BufferedImage[13];
		
		for(int i = 0; i < 13; i++) {
			frames[i] = SpritesheetManager.getInstance()
					                      .getSubImage(path, i * 64, 0, 64, 64);
			
		} 
		
		animation = new Animation(frames, delay);
	}
	
	public void delay(int delay) { this.delay = delay;}
	public Animation getAnimation() { return animation;}
	public void setAnimation() { animation.update();}
	public BufferedImage[] getFrame() { return frames;}
	public BufferedImage getFrame(int index) { return frames[index];}
}

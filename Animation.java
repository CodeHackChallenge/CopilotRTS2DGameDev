package demo.main;

import java.awt.image.BufferedImage;
 
public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private int frameCount;
    private int frameDelay;
    private int tick; 

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frames = frames;
        this.frameCount = frames.length;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.tick = 0;
    }

    public void update() {  
        tick++;
        if (tick >= frameDelay) {  
            tick = 0; 
            currentFrame = (currentFrame + 1) % frameCount;  
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    } 
}
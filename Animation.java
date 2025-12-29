package demo.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    public int totalFrames;
    public int currentFrame = 0;

    public float frameDelay;   // time per frame
    public float timer = 0f;   // accumulates delta

    public List<BufferedImage> frames = new ArrayList<>();
    
    public Animation(int totalFrames, float frameDelay) {
        this.totalFrames = totalFrames;
        this.frameDelay = frameDelay;
    }

    // Reset animation to the beginning
    public void reset() {
        currentFrame = 0;
        timer = 0f;
    }

    // Advance animation by delta time
    public void update(float delta) {
        timer += delta;

        if (timer >= frameDelay) {
            timer -= frameDelay;
            currentFrame++;

            if (currentFrame >= totalFrames) {
                currentFrame = 0;
            }
        }
    }
    
    public BufferedImage getFrame() {
    	if(frames.isEmpty()) { return null;}
    	return frames.get(currentFrame);
    }
}
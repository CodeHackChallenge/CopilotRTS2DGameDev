import java.awt.image.BufferedImage;

/**
 * Refactored: Animation now supports freezing at a standing frame
 * when the entity stops moving, and resuming when moving.
 */
public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private int frameCount;
    private int frameDelay;
    private int tick;
    private boolean playing = true; // NEW: controls whether animation advances

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frames = frames;
        this.frameCount = frames.length;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.tick = 0;
    }

    public void update() {
        if (!playing) return; // NEW: freeze animation when not playing
        tick++;
        if (tick >= frameDelay) {
            tick = 0;
            currentFrame = (currentFrame + 1) % frameCount;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    // NEW: freeze at standing frame (default = frame 0)
    public void freezeStanding() {
        playing = false;
        currentFrame = 0;
    }

    // NEW: resume animation when moving
    public void play() {
        playing = true;
    }
}

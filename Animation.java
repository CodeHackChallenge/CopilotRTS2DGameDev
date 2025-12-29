public class Animation {
    public BufferedImage[] frames;
    public int totalFrames;
    public int currentFrame = 0;

    public float frameDelay;   // seconds per frame
    public float timer = 0f;

    public Animation(BufferedImage[] frames, float frameDelay) {
        this.frames = frames;
        this.totalFrames = frames.length;
        this.frameDelay = frameDelay;
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }
}

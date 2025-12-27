import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class EntityAnimation implements Component {

    private Animation animation;

    public EntityAnimation(String spritePath, int frameCount) {
        try {
            BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream(spritePath));

            int frameWidth = sheet.getWidth() / frameCount;
            int frameHeight = sheet.getHeight();

            BufferedImage[] frames = new BufferedImage[frameCount];

            for (int i = 0; i < frameCount; i++) {
                frames[i] = sheet.getSubimage(
                        i * frameWidth,
                        0,
                        frameWidth,
                        frameHeight
                );
            }

            animation = new Animation(frames, 0.08f); // 12.5 FPS default

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Animation getAnimation() {
        return animation;
    }
}

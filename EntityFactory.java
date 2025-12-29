package demo.main;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class EntityAnimation implements Component {

    private Map<String, Animation> animations = new HashMap<>();
    private Animation current;
    private String currentName;

    public EntityAnimation() {}

    // ---------------------------------------------------------
    // Add a named animation
    // ---------------------------------------------------------
    public void addAnimation(String name, Animation anim) {
        animations.put(name, anim);

        // If this is the first animation added, set it as default
        if (current == null) {
            current = anim;
            currentName = name;
        }
    }

    // ---------------------------------------------------------
    // Switch animation by name
    // ---------------------------------------------------------
    public void play(String name) {
        if (!name.equals(currentName)) {
            current = animations.get(name);
            currentName = name;

            // Reset animation state
            current.currentFrame = 0;
            current.timer = 0f;
        }
    }

    // ---------------------------------------------------------
    // Get current animation
    // ---------------------------------------------------------
    public Animation getAnimation() {
        return current;
    }

    public String getCurrentName() {
        return currentName;
    }
}

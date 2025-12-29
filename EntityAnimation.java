package demo.main;

import java.util.HashMap;
import java.util.Map;

public class EntityAnimation implements Component {

    private Map<String, Animation> animations = new HashMap<>();
    private Animation current;
    private String currentName;

    public EntityAnimation() {}

    // Add an animation to the set
    public void addAnimation(String name, Animation anim) {
        animations.put(name, anim);
        if (current == null) {
            current = anim;
            currentName = name;
        }
    }

    // Switch to a different animation
    public void setAnimation(String name) {
        // If already playing this animation → do nothing
        if (name.equals(currentName)) return;

        Animation next = animations.get(name);
        if (next == null) {
            System.out.println("Warning: animation '" + name + "' not found.");
            return;
        }

        // Switch animation
        current = next;
        currentName = name;

        // Reset animation state
        current.currentFrame = 0;
        current.timer = 0f;
    }

    // Get the currently active animation
    public Animation getAnimation() {
        return current;
    }

    // Optional: check current animation name
    public String getCurrentAnimationName() {
        return currentName;
    }
}
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private boolean showBounds = false;
    private boolean f2Pressed = false;

    public boolean isShowBounds() { return showBounds; }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            if (!f2Pressed) { // only register once per press
                showBounds = !showBounds;
                System.out.println("Bounds visibility toggled: " + showBounds);
                f2Pressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            f2Pressed = false; // reset so next press counts
        }
    }
}

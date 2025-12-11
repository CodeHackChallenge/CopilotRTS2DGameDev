import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyInput extends KeyAdapter {
    private boolean debugOn = false;
    private boolean f2Latch = false;
    private final Set<Integer> pressed = new HashSet<>();

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        pressed.add(code);

        if (code == KeyEvent.VK_F2 && !f2Latch) {
            debugOn = !debugOn;
            f2Latch = true;
            System.out.println("Debug overlay: " + (debugOn ? "ON" : "OFF"));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        pressed.remove(code);
        if (code == KeyEvent.VK_F2) f2Latch = false;
    }

    public boolean isDebugOn() { return debugOn; }

    public boolean isUp()    { return pressed.contains(KeyEvent.VK_W); }
    public boolean isDown()  { return pressed.contains(KeyEvent.VK_S); }
    public boolean isLeft()  { return pressed.contains(KeyEvent.VK_A); }
    public boolean isRight() { return pressed.contains(KeyEvent.VK_D); }
}

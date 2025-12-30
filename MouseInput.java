import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    public double clickX = -1;
    public double clickY = -1;
    public boolean clicked = false;

    @Override
    public void mousePressed(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        clicked = true;
    }
}

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseInput extends MouseAdapter {
    private final List<Entity> entities;
    private Entity selected;
    private Position moveTarget;

    // Hover
    private Point mousePoint = new Point(0,0);

    public MouseInput(List<Entity> entities) { this.entities = entities; }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePoint = e.getPoint();
        boolean hit = false;
        for (Entity entity : entities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && bc.bounds.contains(mousePoint)) {
                selected = entity;
                hit = true;
                System.out.println("Selected entity " + entity.getId());
            }
        }
        if (!hit && selected != null) {
            moveTarget = new Position(e.getX(), e.getY());
            System.out.println("Move command to " + moveTarget.x + "," + moveTarget.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) { mousePoint = e.getPoint(); }
    @Override
    public void mouseDragged(MouseEvent e) { mousePoint = e.getPoint(); }

    public Entity getSelected() { return selected; }
    public Position getMoveTarget() { return moveTarget; }
    public void clearMoveTarget() { moveTarget = null; }
    public Point getMousePoint() { return mousePoint; }
}

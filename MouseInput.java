import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseInput extends MouseAdapter {
    private final List<Entity> entities;
    private Entity selectedEntity;
    private Position moveTarget;
    private Point mousePoint = new Point(0,0);

    public MouseInput(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePoint = e.getPoint();
        boolean clickedOnEntity = false;

        for (Entity entity : entities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && bc.bounds.contains(mousePoint)) {
                selectedEntity = entity;
                clickedOnEntity = true;
                System.out.println("Selected entity " + entity.getId());
            }
        }

        // If clicked empty space and we have a selected entity → move directly to pixel coords
        if (!clickedOnEntity && selectedEntity != null) {
            moveTarget = new Position(mousePoint.x, mousePoint.y);
            System.out.println("Move command to " + moveTarget.x + "," + moveTarget.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) { mousePoint = e.getPoint(); }
    @Override
    public void mouseDragged(MouseEvent e) { mousePoint = e.getPoint(); }

    public Entity getSelectedEntity() { return selectedEntity; }
    public Position getMoveTarget() { return moveTarget; }
    public void clearMoveTarget() { moveTarget = null; }
    public Point getMousePoint() { return mousePoint; }
}

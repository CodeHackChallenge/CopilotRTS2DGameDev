import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseInput extends MouseAdapter {
    private List<Entity> entities;
    private Entity selectedEntity;
    private Position moveTarget;

    public MouseInput(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        boolean clickedOnEntity = false;

        for (Entity entity : entities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && bc.bounds.contains(click)) {
                selectedEntity = entity;
                clickedOnEntity = true;
                System.out.println("Selected entity " + entity.getId());
            }
        }

        if (!clickedOnEntity && selectedEntity != null) {
            moveTarget = new Position(click.x, click.y);
            System.out.println("Move command to " + moveTarget.x + "," + moveTarget.y);
        }
    }

    public Entity getSelectedEntity() { return selectedEntity; }
    public Position getMoveTarget() { return moveTarget; }
    public void clearMoveTarget() { moveTarget = null; }
}

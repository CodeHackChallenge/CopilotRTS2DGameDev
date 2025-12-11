import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseInput extends MouseAdapter {
    private final List<Entity> entities;
    private final TileMapComponent tileMap;
    private final int[] offsetXRef;
    private final int[] offsetYRef;

    private Entity selectedEntity;
    private Position moveTarget;
    private Point mousePoint = new Point(0,0); // <-- restore hover tracking

    public MouseInput(List<Entity> entities, TileMapComponent tileMap,
                      int[] offsetXRef, int[] offsetYRef) {
        this.entities = entities;
        this.tileMap = tileMap;
        this.offsetXRef = offsetXRef;
        this.offsetYRef = offsetYRef;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePoint = e.getPoint(); // update hover point on press too
        boolean clickedOnEntity = false;

        for (Entity entity : entities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && bc.bounds.contains(mousePoint)) {
                selectedEntity = entity;
                clickedOnEntity = true;
                System.out.println("Selected entity " + entity.getId());
            }
        }

        if (!clickedOnEntity && selectedEntity != null && tileMap != null) {
            MapConfig cfg = tileMap.getConfig();
            int ts = cfg.tileSize();

            int tileX = (mousePoint.x + offsetXRef[0]) / ts;
            int tileY = (mousePoint.y + offsetYRef[0]) / ts;

            Sprite sprite = selectedEntity.getComponent(Sprite.class);
            Position pos = selectedEntity.getComponent(Position.class);
            BoundsComponent bc = selectedEntity.getComponent(BoundsComponent.class);

            if (sprite != null && pos != null && bc != null) {
                int targetX = tileX * ts + (ts - sprite.width) / 2;
                int targetY = tileY * ts + (ts - sprite.height) / 2;

                moveTarget = new Position(targetX, targetY);
                System.out.println("Move command to tile (" + tileX + "," + tileY +
                                   ") → world (" + targetX + "," + targetY + ")");
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePoint = e.getPoint(); // update hover point continuously
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePoint = e.getPoint();
    }

    public Entity getSelectedEntity() { return selectedEntity; }
    public Position getMoveTarget() { return moveTarget; }
    public void clearMoveTarget() { moveTarget = null; }
    public Point getMousePoint() { return mousePoint; } // <-- restore getter
}

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseInput extends MouseAdapter {
    private final List<Entity> entities;
    private final TileMapComponent tileMap;
    private final int offsetXRef[];
    private final int offsetYRef[];

    private Entity selectedEntity;
    private Position moveTarget;

    public MouseInput(List<Entity> entities, TileMapComponent tileMap,
                      int[] offsetXRef, int[] offsetYRef) {
        this.entities = entities;
        this.tileMap = tileMap;
        this.offsetXRef = offsetXRef; // pass array reference so we can read live offsets
        this.offsetYRef = offsetYRef;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        boolean clickedOnEntity = false;

        // Check if clicked on an entity
        for (Entity entity : entities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && bc.bounds.contains(click)) {
                selectedEntity = entity;
                clickedOnEntity = true;
                System.out.println("Selected entity " + entity.getId());
            }
        }

        // If clicked empty space and we have a selected entity → move to tile
        if (!clickedOnEntity && selectedEntity != null && tileMap != null) {
            MapConfig cfg = tileMap.getConfig();
            int ts = cfg.tileSize();

            // Convert screen click → tile coordinates using current offsets
            int tileX = (click.x + offsetXRef[0]) / ts;
            int tileY = (click.y + offsetYRef[0]) / ts;

            // Center soldier inside tile
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

    public Entity getSelectedEntity() { return selectedEntity; }
    public Position getMoveTarget() { return moveTarget; }
    public void clearMoveTarget() { moveTarget = null; }
}

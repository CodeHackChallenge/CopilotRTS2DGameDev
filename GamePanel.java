import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;

    private final List<Entity> entities = new ArrayList<>();
    private final RenderSystem renderSystem = new RenderSystem();
    private final TileMapSystem tileMapSystem = new TileMapSystem();
    private final MovementSystem movementSystem = new MovementSystem();

    private TileMapComponent tileMap;
    private MouseInput mouseInput;
    private KeyInput keyInput;

    // Viewport offsets
    private int offsetX = 0;
    private int offsetY = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(728, 728));
        setBackground(Color.BLACK);

        // Soldier entity
        Entity soldier = new Entity(1);
        Position pos = new Position(64, 64); // starting pixel position
        Sprite sprite = new Sprite(Color.WHITE, 64, 64);
        BoundsComponent bounds = new BoundsComponent(pos.x, pos.y, sprite.width, sprite.height);
        soldier.addComponent(pos);
        soldier.addComponent(sprite);
        soldier.addComponent(bounds);
        entities.add(soldier);

        // Input
        mouseInput = new MouseInput(entities);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);

        keyInput = new KeyInput();
        addKeyListener(keyInput);
        setFocusable(true);
        requestFocus();

        // Load map
        try {
            MapConfig cfg = MapConfig.defaultConfig();
            tileMap = TileMapLoader.loadFromFile("map.txt", cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            update();
            render();
            try { Thread.sleep(16); } catch (InterruptedException ignored) {}
        }
    }

    private void update() {
        // WASD panning
        int panSpeed = 8;
        if (keyInput.isLeft())  offsetX = Math.max(0, offsetX - panSpeed);
        if (keyInput.isRight()) offsetX = Math.min(tileMap.getConfig().widthTiles() * tileMap.getConfig().tileSize() - 728, offsetX + panSpeed);
        if (keyInput.isUp())    offsetY = Math.max(0, offsetY - panSpeed);
        if (keyInput.isDown())  offsetY = Math.min(tileMap.getConfig().heightTiles() * tileMap.getConfig().tileSize() - 728, offsetY + panSpeed);

        // Move selected entity toward target
        Entity selected = mouseInput.getSelectedEntity();
        Position target = mouseInput.getMoveTarget();
        if (selected != null && target != null && tileMap != null) {
            // Convert screen target → world target
            Position worldTarget = new Position(target.x + offsetX, target.y + offsetY);
            movementSystem.moveTowards(selected, worldTarget, tileMap);

            Position pos = selected.getComponent(Position.class);
            if (pos.x == worldTarget.x && pos.y == worldTarget.y) {
                mouseInput.clearMoveTarget();
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw map
        if (tileMap != null) {
            tileMapSystem.render(g, tileMap, offsetX, offsetY);
        }

        // Draw entities
        renderSystem.render(g, entities, offsetX, offsetY);

        // Debug overlay
        if (keyInput.isDebugOn() && tileMap != null) {
            tileMapSystem.renderGrid(g, tileMap, offsetX, offsetY);

            Point mp = mouseInput.getMousePoint();
            MapConfig cfg = tileMap.getConfig();
            int ts = cfg.tileSize();
            int tileX = (mp.x + offsetX) / ts;
            int tileY = (mp.y + offsetY) / ts;

            if (tileX >= 0 && tileY >= 0 && tileX < cfg.widthTiles() && tileY < cfg.heightTiles()) {
                boolean walk = tileMap.isWalkable(tileX, tileY);
                g.setColor(walk ? Color.YELLOW : Color.RED);

                int screenX = tileX * ts - offsetX;
                int screenY = tileY * ts - offsetY;

                g.drawLine(screenX, screenY, screenX + ts, screenY);             // top
                g.drawLine(screenX, screenY + ts, screenX + ts, screenY + ts);   // bottom
                g.drawLine(screenX, screenY, screenX, screenY + ts);             // left
                g.drawLine(screenX + ts, screenY, screenX + ts, screenY + ts);   // right
            }
        }

        g.dispose();
        bs.show();
    }
}

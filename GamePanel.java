import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Refactored GamePanel:
 * - Window size increased to 1200x720
 * - Uses TextureManager with one spritesheet
 * - Soldier entity uses SoldierAnimation (8 directions)
 * - Animations freeze when soldier stops, play when moving
 */
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
        // Refactored: window size increased
        setPreferredSize(new Dimension(1200, 720));
        setBackground(Color.BLACK);

        // Refactored: preload spritesheet once
        TextureManager.getInstance(); // loads /resources/spritesheet.png

        // Refactored: Soldier entity setup with SoldierAnimation
        Entity soldier = new Entity(1);
        Position pos = new Position(64, 64);

        // Build animations for all 8 directions
        EnumMap<Direction, Animation> soldierAnims = new EnumMap<>(Direction.class);
        for (int dirIndex = 0; dirIndex < 8; dirIndex++) {
            BufferedImage[] frames = new BufferedImage[8]; // 8 frames per direction
            for (int i = 0; i < 8; i++) {
                frames[i] = TextureManager.getInstance().getSubImage(i * 64, dirIndex * 64, 64, 64);
            }
            soldierAnims.put(Direction.values()[dirIndex], new Animation(frames, 8));
        }

        SoldierAnimation soldierAnim = new SoldierAnimation(soldierAnims);
        BoundsComponent bounds = new BoundsComponent(pos.x, pos.y, 64, 64);

        soldier.addComponent(pos);
        soldier.addComponent(bounds);
        soldier.addComponent(soldierAnim);
        entities.add(soldier);

        // Input setup
        mouseInput = new MouseInput(entities);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);

        keyInput = new KeyInput();
        addKeyListener(keyInput);
        setFocusable(true);
        requestFocus();

        // Load map (space-separated IDs)
        try {
            MapConfig cfg = MapConfig.defaultConfig();
            tileMap = TileMapLoader.loadFromFile("/maps/map.txt", cfg);
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
        if (keyInput.isRight()) offsetX = Math.min(tileMap.getConfig().widthTiles() * tileMap.getConfig().tileSize() - 1200, offsetX + panSpeed);
        if (keyInput.isUp())    offsetY = Math.max(0, offsetY - panSpeed);
        if (keyInput.isDown())  offsetY = Math.min(tileMap.getConfig().heightTiles() * tileMap.getConfig().tileSize() - 720, offsetY + panSpeed);

        // Move selected entity toward target
        Entity selected = mouseInput.getSelectedEntity();
        Position target = mouseInput.getMoveTarget();
        if (selected != null && target != null && tileMap != null) {
            movementSystem.moveTowards(selected, target, tileMap);

            Position pos = selected.getComponent(Position.class);
            if (pos.x == target.x && pos.y == target.y) {
                mouseInput.clearMoveTarget();
            }
        }

        // Update animations (only those that are playing)
        for (Entity e : entities) {
            SoldierAnimation soldierAnim = e.getComponent(SoldierAnimation.class);
            if (soldierAnim != null) {
                soldierAnim.getCurrentAnimation().update();
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

        // Draw entities (RenderSystem supports SoldierAnimation)
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

                g.drawRect(screenX, screenY, ts, ts);
            }
        }

        g.dispose();
        bs.show();
    }
}


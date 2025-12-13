import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Refactored GamePanel:
 * - Window size 1200x720
 * - Uses SpritesheetManager for both tiles and soldiers
 * - Soldier entity uses SoldierAnimation with 8 directions
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

    private int offsetX = 0;
    private int offsetY = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(1200, 720));
        setBackground(Color.BLACK);

        // Soldier entity setup with SoldierAnimation
        Entity soldier = new Entity(1);
        Position pos = new Position(64, 64);

        // Build animations for all 8 directions from soldier spritesheet
        EnumMap<Direction, Animation> soldierAnims = new EnumMap<>(Direction.class);
        for (int dirIndex = 0; dirIndex < 8; dirIndex++) {
            BufferedImage[] frames = new BufferedImage[8];
            for (int i = 0; i < 8; i++) {
                frames[i] = SpritesheetManager.getInstance()
                        .getSubImage("/soldier_sheet.png", i * 64, dirIndex * 64, 64, 64);
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
        int panSpeed = 8;
        if (keyInput.isLeft())  offsetX = Math.max(0, offsetX - panSpeed);
        if (keyInput.isRight()) offsetX = Math.min(tileMap.getConfig().widthTiles() * tileMap.getConfig().tileSize() - 1200, offsetX + panSpeed);
        if (keyInput.isUp())    offsetY = Math.max(0, offsetY - panSpeed);
        if (keyInput.isDown())  offsetY = Math.min(tileMap.getConfig().heightTiles() * tileMap.getConfig().tileSize() - 720, offsetY + panSpeed);

        Entity selected = mouseInput.getSelectedEntity();
        Position target = mouseInput.getMoveTarget();
        if (selected != null && target != null && tileMap != null) {
            movementSystem.moveTowards(selected, target, tileMap);

            Position pos = selected.getComponent(Position.class);
            if (pos.x == target.x && pos.y == target.y) {
                mouseInput.clearMoveTarget();
            }
        }

        // Update animations
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

        if (tileMap != null) {
            tileMapSystem.render(g, tileMap, offsetX, offsetY);
        }

        renderSystem.render(g, entities, offsetX, offsetY);

        g.dispose();
        bs.show();
    }
}

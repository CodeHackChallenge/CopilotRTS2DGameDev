import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;

    private Camera camera;
    private TileMapComponent tileMap;
    private RenderSystem renderSystem;
    private SelectionSystem selectionSystem;

    private List<Entity> entities = new ArrayList<>();

    public GamePanel(TileMapComponent map, List<Entity> entities, Camera camera) {
        this.tileMap = map;
        this.entities = entities;
        this.camera = camera;
        this.renderSystem = new RenderSystem(camera);
        this.selectionSystem = new SelectionSystem(entities, camera);

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        // Input listeners
        addKeyListener(new CameraController(camera));
        addMouseListener(selectionSystem);
        addMouseMotionListener(selectionSystem);
        setFocusable(true);
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
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // Update game logic here (movement, pathfinding, etc.)
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render map + entities
        renderSystem.renderMap(tileMap, g);
        renderSystem.renderEntities(entities, g);

        g.dispose();
        bs.show();
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;

    private List<Entity> entities = new ArrayList<>();
    private RenderSystem renderSystem = new RenderSystem();
    private MovementSystem movementSystem = new MovementSystem();
    private MouseInput mouseInput;

    public GamePanel() {
        setPreferredSize(new Dimension(728, 728));
        setBackground(Color.BLACK);

        // Create soldier
        Entity soldier = new Entity(1);
        Position pos = new Position(100, 100);
        Sprite sprite = new Sprite(Color.WHITE, 64, 64);
        BoundsComponent bounds = new BoundsComponent(pos.x, pos.y, sprite.width, sprite.height);

        soldier.addComponent(pos);
        soldier.addComponent(sprite);
        soldier.addComponent(bounds);
        entities.add(soldier);

        mouseInput = new MouseInput(entities);
        addMouseListener(mouseInput);
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
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    private void update() {
        Entity selected = mouseInput.getSelectedEntity();
        Position target = mouseInput.getMoveTarget();
        if (selected != null && target != null) {
            movementSystem.moveTowards(selected, target);
            Position pos = selected.getComponent(Position.class);
            if (pos.x == target.x && pos.y == target.y) {
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

        renderSystem.render(g, entities);

        g.dispose();
        bs.show();
    }
}

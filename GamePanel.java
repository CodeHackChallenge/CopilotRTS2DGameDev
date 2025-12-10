import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas implements Runnable, MouseListener {
    private Thread thread;
    private boolean running = false;

    private List<Entity> entities = new ArrayList<>();
    private Entity selectedEntity = null;
    private Position moveTarget = null;

    public GamePanel() {
        setPreferredSize(new Dimension(728, 728));
        setBackground(Color.BLACK);
        addMouseListener(this);

        // Create one soldier at (100,100)
        Position pos = new Position(100, 100);
        Sprite sprite = new Sprite(Color.WHITE, 64, 64);
        BoundsComponent bounds = new BoundsComponent(pos.x, pos.y, sprite.width, sprite.height);
        Entity soldier = new Entity(1, pos, bounds, sprite);
        entities.add(soldier);
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
        if (selectedEntity != null && moveTarget != null) {
            Position pos = selectedEntity.getPosition();
            // Simple linear movement toward target
            if (pos.x < moveTarget.x) pos.x++;
            if (pos.x > moveTarget.x) pos.x--;
            if (pos.y < moveTarget.y) pos.y++;
            if (pos.y > moveTarget.y) pos.y--;

            selectedEntity.getBounds().update(pos,
                    selectedEntity.getSprite().width,
                    selectedEntity.getSprite().height);

            // Stop when reached
            if (pos.x == moveTarget.x && pos.y == moveTarget.y) {
                moveTarget = null;
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

        for (Entity e : entities) {
            Position pos = e.getPosition();
            Sprite sprite = e.getSprite();

            g.setColor(sprite.color);
            g.fillRect(pos.x, pos.y, sprite.width, sprite.height);

            // Highlight if selected
            if (e == selectedEntity) {
                g.setColor(Color.YELLOW);
                g.drawRect(pos.x, pos.y, sprite.width, sprite.height);
            }
        }

        g.dispose();
        bs.show();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        boolean clickedOnEntity = false;

        for (Entity e1 : entities) {
            if (e1.getBounds().bounds.contains(click)) {
                selectedEntity = e1;
                clickedOnEntity = true;
                System.out.println("Selected entity " + e1.getId());
            }
        }

        if (!clickedOnEntity && selectedEntity != null) {
            moveTarget = new Position(click.x, click.y);
            System.out.println("Move command to " + moveTarget.x + "," + moveTarget.y);
        }
    }

    // Unused
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}

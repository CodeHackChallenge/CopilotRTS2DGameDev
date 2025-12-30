public class MovementSystem {

    private MouseInput mouse;

    public MovementSystem(MouseInput mouse) {
        this.mouse = mouse;
    }

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            MovementTarget mt = e.getComponent(MovementTarget.class);

            if (pos == null || mt == null) continue;

            // ---------------------------------------------------------
            // If mouse clicked, set new movement target
            // ---------------------------------------------------------
            if (mouse.clicked) {
                mt.x = mouse.clickX;
                mt.y = mouse.clickY;
                mt.hasTarget = true;
                mouse.clicked = false;
            }

            if (!mt.hasTarget) continue;

            // ---------------------------------------------------------
            // Move toward target
            // ---------------------------------------------------------
            double dx = mt.x - pos.x;
            double dy = mt.y - pos.y;

            Vector2D dir = new Vector2D(dx, dy);
            double dist = dir.length();

            // If close enough, stop
            if (dist < 2) {
                mt.hasTarget = false;
                continue;
            }

            dir.normalize();

            double speed = 120; // pixels per second
            pos.x += dir.x * speed * delta;
            pos.y += dir.y * speed * delta;
        }
    }
}

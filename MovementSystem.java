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
            // Compute direction
            // ---------------------------------------------------------
            double dx = mt.x - pos.x;
            double dy = mt.y - pos.y;

            // ---------------------------------------------------------
            // ⭐ FIX 1: Remove tiny vertical drift
            // If dy is extremely small, treat it as zero
            // ---------------------------------------------------------
            if (Math.abs(dy) < 0.0001) {
                dy = 0;
            }

            Vector2D dir = new Vector2D(dx, dy);
            double dist = dir.length();

            double speed = 120; // pixels per second

            // ---------------------------------------------------------
            // ⭐ FIX 2: Snap to target if close enough
            // Prevents oscillation and jitter
            // ---------------------------------------------------------
            if (dist <= speed * delta) {
                pos.x = mt.x;
                pos.y = mt.y;
                mt.hasTarget = false;
                continue;
            }

            // ---------------------------------------------------------
            // Normalize direction
            // ---------------------------------------------------------
            dir.normalize();

            // ---------------------------------------------------------
            // Apply movement
            // ---------------------------------------------------------
            pos.x += dir.x * speed * delta;
            pos.y += dir.y * speed * delta;

            // ---------------------------------------------------------
            // ⭐ FIX 3: Clamp Y when movement is horizontal
            // If target Y == start Y, keep Y perfectly stable
            // ---------------------------------------------------------
            if (dy == 0) {
                pos.y = Math.round(pos.y);
            }
        }
    }
}

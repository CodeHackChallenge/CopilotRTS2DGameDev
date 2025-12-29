public class HealthBarSystem {

    private static final Color HP_GREEN = new Color(46, 204, 113);
    private static final Color BG_COLOR = new Color(0, 0, 0, 120);

    public void update(List<Entity> entities, float delta) {
        // no logic needed here yet
    }

    public void render(Graphics2D g, List<Entity> entities) {

        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            Collision col = e.getComponent(Collision.class);
            Health hp = e.getComponent(Health.class);
            HealthBar bar = e.getComponent(HealthBar.class);

            if (pos == null || col == null || hp == null || bar == null)
                continue;

            // ---------------------------------------------------------
            // Calculate bar position relative to hurtbox
            // ---------------------------------------------------------
            int barX = (int)(pos.x + col.offsetX + bar.offsetX);
            int barY = (int)(pos.y + col.offsetY + col.height + bar.offsetY);

            // HP percentage
            float pct = (float) hp.current / hp.max;
            pct = Math.max(0f, Math.min(1f, pct));

            int filledWidth = (int)(bar.width * pct);

            // ---------------------------------------------------------
            // Draw background
            // ---------------------------------------------------------
            g.setColor(BG_COLOR);
            g.fillRect(barX, barY, bar.width, bar.height);

            // ---------------------------------------------------------
            // Draw HP fill
            // ---------------------------------------------------------
            g.setColor(HP_GREEN);
            g.fillRect(barX, barY, filledWidth, bar.height);

            // ---------------------------------------------------------
            // Draw border
            // ---------------------------------------------------------
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, bar.width, bar.height);
        }
    }
}

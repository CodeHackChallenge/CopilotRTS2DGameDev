package demo.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class HealthBarSystem {

    private static final Color HP_GREEN = new Color(46, 204, 113);
    private static final Color HP_ORANGE = new Color(243, 156, 18);
    private static final Color HP_RED = new Color(231, 76, 60);
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
            //clamp HP to sane range
            int current = Math.max(0, hp.current);
            int max = Math.max(1, hp.max); //avoid division by 0
            
            // HP percentage
            float pct = (float) hp.current / hp.max;
            pct = Math.max(0f, Math.min(1f, pct));

            //if still alive but extremely low HP, force a visible sliver
            if(current > 0 && pct < 0.10f) { //10% minimum visual size
            	pct = 0.10f;            	
            }
            int filledWidth = (int)(bar.width * pct);
//            //ensure bar never disappears unless HP is 0
//            if(hp.current > 0) {
//            	filledWidth = Math.max(1, filledWidth);
//            }
            // ---------------------------------------------------------
            // Choose color based on HP%
            // ---------------------------------------------------------
            Color hpColor;
            if(pct > 0.50f) {
        	    hpColor = HP_GREEN;
            } else if(pct > 0.25f) {
        	    hpColor = HP_ORANGE;
            } else {
        	   hpColor = HP_RED;
            }
            
            // ---------------------------------------------------------
            // Draw background
            // ---------------------------------------------------------
            g.setColor(BG_COLOR);
            g.fillRect(barX, barY, bar.width, bar.height);

            // ---------------------------------------------------------
            // Draw HP fill
            // ---------------------------------------------------------
            g.setColor(hpColor);
            g.fillRect(barX, barY, filledWidth, bar.height);

            // ---------------------------------------------------------
            // Draw border
            // ---------------------------------------------------------
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, bar.width, bar.height);
        }
    }
}
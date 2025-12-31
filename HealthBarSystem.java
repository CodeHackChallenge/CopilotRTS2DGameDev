package demo.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

public class HealthBarSystem {

    private static final Color HP_GREEN  = new Color(46, 204, 113);
    private static final Color HP_ORANGE = new Color(243, 156, 18);
    private static final Color HP_RED    = new Color(231, 76, 60);
    private static final Color BG_COLOR  = new Color(0, 0, 0, 120);

    public void render(Graphics2D g, List<Entity> entities) {

    	//disable ati-aliasing
    	Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            RenderPosition rp = e.getComponent(RenderPosition.class);
            Collision col = e.getComponent(Collision.class);
            Health hp = e.getComponent(Health.class);
            HealthBar bar = e.getComponent(HealthBar.class);

            if (pos == null || rp == null || col == null || hp == null || bar == null)
                continue;

            // ---------------------------------------------------------
            // Use the SAME rounded base position as RenderSystem
            // ---------------------------------------------------------
            int barX = rp.x + col.offsetX + bar.offsetX;
            int barY = rp.y + col.offsetY + col.height + bar.offsetY;

            // HP percentage
            float pct = (float) hp.current / hp.max;
            pct = Math.max(0f, Math.min(1f, pct));

            // ⭐ Minimum visible sliver if alive
            if (hp.current > 0 && pct < 0.10f) {
                pct = 0.10f;
            }

            int filledWidth = (int)(bar.width * pct);

            // Color thresholds
            Color hpColor =
                pct > 0.50f ? HP_GREEN :
                pct > 0.25f ? HP_ORANGE :
                              HP_RED;

            // Background
            g.setColor(BG_COLOR);
            g.fillRect(barX, barY, bar.width, bar.height);

            // Fill
            g.setColor(hpColor);
            g.fillRect(barX, barY, filledWidth, bar.height);

            // Border
            g.setColor(Color.BLACK);
            g.drawRect(barX, barY, bar.width, bar.height);
            
            System.out.println("rp.x="+rp.x+" "
            		+ "col.offsetX="+col.offsetX+" "
            				+ "col.width="+col.width+" "
            						+ "bar.offsetX="+bar.offsetX);
            						 
        }//foreach
        
        //disable anti-alising
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }//render
}
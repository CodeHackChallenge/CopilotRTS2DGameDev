import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

public class RenderSystem {

    private boolean debugCollision = false; // toggle for debugging hitboxes

    public void render(Graphics2D graphics2D, List<Entity> entities) {

        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            if (pos == null) continue;

            EntityAnimation anim = e.getComponent(EntityAnimation.class);
            Sprite sprite = e.getComponent(Sprite.class);

            // ---------------------------------------------------------
            // SPRITE / ANIMATION
            // ---------------------------------------------------------
            if (anim != null) {
                Animation a = anim.getAnimation();
                if (a != null) {
                    graphics2D.drawImage(a.getCurrentFrame(),
                            (int) pos.x,
                            (int) pos.y,
                            null);
                }
            } else if (sprite != null) {
                graphics2D.setColor(sprite.color);
                graphics2D.fillRect((int) pos.x, (int) pos.y, sprite.width, sprite.height);
            }

            // ---------------------------------------------------------
            // HEALTH BAR (Collision‑anchored)
            // ---------------------------------------------------------
            Health hp = e.getComponent(Health.class);
            Collision col = e.getComponent(Collision.class);

            if (hp != null && col != null) {

                float pct = hp.getDisplayedPrecent();

                // Collision bounds determine HP bar position
                Rectangle bounds = col.getBounds(pos);

                int barWidth = bounds.width;
                int barHeight = 6;

                int screenX = bounds.x;
                int screenY = bounds.y - barHeight - 2;

                // Lerp color (your original logic preserved)
                Color green = Color.GREEN;
                Color orange = Color.ORANGE;
                Color red = Color.RED;

                Color barColor;
                if (pct > 0.5f) {
                    float t = (pct - 0.5f) / 0.5f;
                    barColor = lerpColor(orange, green, t);
                } else if (pct > 0.25f) {
                    float t = (pct - 0.25f) / 0.25f;
                    barColor = lerpColor(red, orange, t);
                } else {
                    barColor = Color.RED;
                }

                int filled = (int) (barWidth * pct);
                if (hp.current > 0) filled = Math.max(1, filled);

                // Draw bar
                graphics2D.setColor(barColor);
                graphics2D.fillRect(screenX, screenY, filled, barHeight);
            }

            // ---------------------------------------------------------
            // DAMAGE TEXT (your original code)
            // ---------------------------------------------------------
            RenderTextComponent rtc = e.getComponent(RenderTextComponent.class);
            DamageTextComponent dt = e.getComponent(DamageTextComponent.class);

            if (rtc != null && dt != null) {

                int baseX = (int) (pos.x + dt.offsetX);
                int baseY = (int) (pos.y + dt.offsetY);

                int alpha = (int) (dt.alpha * 255);
                alpha = Math.max(0, Math.min(255, alpha));

                Color shadowColor = new Color(0, 0, 0, alpha);
                Color mainColor = new Color(
                        rtc.color.getRed(),
                        rtc.color.getGreen(),
                        rtc.color.getBlue(),
                        alpha
                );

                Graphics2D gText = (Graphics2D) graphics2D.create();
                gText.translate(baseX, baseY);
                gText.scale(dt.scale, dt.scale);

                if (!dt.isCrit) {
                    gText.setColor(shadowColor);
                    gText.drawString(dt.text, rtc.shadowOffsetX, rtc.shadowOffsetY);
                }

                gText.setColor(mainColor);
                gText.setFont(new Font("Arial", Font.BOLD, rtc.size));
                gText.drawString(dt.text, 0, 0);

                gText.dispose();
            }

            // ---------------------------------------------------------
            // DEBUG COLLISION BOX
            // ---------------------------------------------------------
            if (debugCollision && col != null) {
                Rectangle r = col.getBounds(pos);
                graphics2D.setColor(new Color(0, 255, 0, 150));
                graphics2D.drawRect(r.x, r.y, r.width, r.height);
            }
        }
    }

    private Color lerpColor(Color a, Color b, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bc = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        return new Color(r, g, bc);
    }
}

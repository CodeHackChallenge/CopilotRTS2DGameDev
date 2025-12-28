public class RenderSystem {

    public void render(Graphics2D g, List<Entity> entities) {

        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            if (pos == null) continue;

            // ---------------------------------------------------------
            // DRAW ANIMATION FRAME
            // ---------------------------------------------------------
            EntityAnimation animComp = e.getComponent(EntityAnimation.class);
            if (animComp != null) {

                Animation anim = animComp.getAnimation();
                if (anim != null) {

                    BufferedImage frame = anim.getFrame();
                    if (frame != null) {
                        g.drawImage(frame, (int) pos.x, (int) pos.y, null);
                    }
                }
            }

            // ---------------------------------------------------------
            // DRAW DAMAGE TEXT
            // ---------------------------------------------------------
            DamageTextComponent dt = e.getComponent(DamageTextComponent.class);
            if (dt != null) {
                RenderTextComponent rtc = e.getComponent(RenderTextComponent.class);
                if (rtc != null) {
                    g.setColor(rtc.color);
                    g.setFont(rtc.font);

                    int drawX = (int) (pos.x + dt.offsetX);
                    int drawY = (int) (pos.y + dt.offsetY);

                    g.drawString(dt.text, drawX, drawY);
                }
            }

            // ---------------------------------------------------------
            // DEBUG: DRAW COLLISION BOX
            // ---------------------------------------------------------
            Collision col = e.getComponent(Collision.class);
            if (col != null) {
                g.setColor(Color.GREEN);
                g.drawRect(
                    (int) (pos.x + col.offsetX),
                    (int) (pos.y + col.offsetY),
                    col.width,
                    col.height
                );
            }

            // ---------------------------------------------------------
            // DEBUG: DRAW ATTACK HITBOX
            // ---------------------------------------------------------
            AttackHitbox hit = e.getComponent(AttackHitbox.class);
            if (hit != null && hit.width > 0 && hit.height > 0) {
                g.setColor(Color.RED);
                g.drawRect(
                    (int) (pos.x + hit.offsetX),
                    (int) (pos.y + hit.offsetY),
                    hit.width,
                    hit.height
                );
            }
        }
    }
}

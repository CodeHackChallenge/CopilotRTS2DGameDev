public class EntityFactory {

    public static Entity createHero(double x, double y) {

        Entity e = new Entity();

        // Position
        e.addComponent(new Position(x, y));

        // Load sprite sheet
        BufferedImage sheet = TextureManager.load("/sprite/hero.png");

        // Animation component
        EntityAnimation animComp = new EntityAnimation();

        // Idle animation
        Animation idle = new Animation(4, 0.20f);
        for (int i = 0; i < 4; i++) {
            idle.frames.add(sheet.getSubimage(i * 32, 0, 32, 32));
        }
        animComp.addAnimation("idle", idle);

        // Attack animation
        Animation attack = new Animation(6, 0.17f);
        for (int i = 0; i < 6; i++) {
            attack.frames.add(sheet.getSubimage(i * 32, 32, 32, 32));
        }
        animComp.addAnimation("attack", attack);

        e.addComponent(animComp);

        // Combat components
        e.addComponent(new Attack(3, 6));
        e.addComponent(new Accuracy(0.8f));
        e.addComponent(new AttackCoolDown(1f));
        e.addComponent(new AttackState());
        e.addComponent(new AttackProfile(0.51f, 0.17f, 0.34f));

        // Collision + hitbox
        e.addComponent(new Collision(0, 0, 32, 32));
        e.addComponent(new AttackHitbox());

        // Health
        e.addComponent(new Health(100));

        return e;
    }
}

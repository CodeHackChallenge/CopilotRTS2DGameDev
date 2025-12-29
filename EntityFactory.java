package demo.main;

import java.awt.image.BufferedImage;

public class EntityFactory {

    // =========================================================
    // HERO
    // =========================================================
    public static Entity createHero(double x, double y) {

        Entity e = new Entity(0);

        // -----------------------------------------------------
        // POSITION
        // -----------------------------------------------------
        e.addComponent(new Position(x, y));

        // -----------------------------------------------------
        // SPRITESHEET
        // -----------------------------------------------------
        BufferedImage sheet = TextureManager.load("/sprite/hero_rightatk.png");

        // -----------------------------------------------------
        // ANIMATION COMPONENT
        // -----------------------------------------------------
        EntityAnimation animComp = new EntityAnimation();

        // -------------------------
        // Idle animation (2 frames)
        // -------------------------
        BufferedImage[] idleFrames = new BufferedImage[2];
        for (int i = 0; i < 2; i++) {
            idleFrames[i] = sheet.getSubimage(i * 64, 64, 64, 64);
        }
        Animation idle = new Animation(idleFrames, 0.40f);
        animComp.addAnimation("idle", idle);

        // -------------------------
        // Attack animation (6 frames)
        // -------------------------
        BufferedImage[] attackFrames = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            attackFrames[i] = sheet.getSubimage(i * 192, 0, 192, 64);
        }
        Animation attack = new Animation(attackFrames, 0.17f);
        animComp.addAnimation("attack", attack);

        e.addComponent(animComp);

        // -----------------------------------------------------
        // COMBAT COMPONENTS
        // -----------------------------------------------------
        e.addComponent(new Attack(1, 25, 74, 1.0));
        e.addComponent(new Accuracy(0.8f));
        e.addComponent(new AttackCoolDown(5.0f));   // attack speed
        e.addComponent(new AttackState());
        e.addComponent(new AttackProfile(0.51f, 0.34f, 0.17f)); // windup, hit, recovery

        // -----------------------------------------------------
        // COLLISION + HITBOX
        // -----------------------------------------------------
        e.addComponent(new Collision(16, 34, 20, 28));
        e.addComponent(new AttackHitbox(40, 20, 66, 32));

        // -----------------------------------------------------
        // STATS
        // -----------------------------------------------------
        e.addComponent(new Health(100));
        e.addComponent(new Defense(1, 4));
        e.addComponent(new Evasion(0.2f));
        e.addComponent(new CriticalHit(0.20f, 2.0f));

        // -----------------------------------------------------
        // FACTION + TARGET
        // -----------------------------------------------------
        e.addComponent(new Faction(Faction.Type.HERO));
        e.addComponent(new CurrentTarget());

        return e;
    }

    // =========================================================
    // DUMMY
    // =========================================================
    public static Entity createDummy(double x, double y) {

        Entity e = new Entity(1);

        // Position
        e.addComponent(new Position(x, y));

        // Spritesheet
        BufferedImage sheet = TextureManager.load("/sprite/enemy_64x64.png");

        // Animation component
        EntityAnimation animComp = new EntityAnimation();

        // Idle animation (13 frames)
        BufferedImage[] idleFrames = new BufferedImage[13];
        for (int i = 0; i < 13; i++) {
            idleFrames[i] = sheet.getSubimage(i * 64, 0, 64, 64);
        }
        animComp.addAnimation("idle", new Animation(idleFrames, 0.08f));

        e.addComponent(animComp);

        // Collision
        e.addComponent(new Collision(16, 34, 20, 28));

        // Stats
        e.addComponent(new Health(100));
        e.addComponent(new Defense(1, 4));
        e.addComponent(new Evasion(0.2f));

        // Faction + GodMode
        e.addComponent(new Faction(Faction.Type.ENEMY));
        e.addComponent(new GodMode(GodMode.Mode.REGEN));

        return e;
    }
}

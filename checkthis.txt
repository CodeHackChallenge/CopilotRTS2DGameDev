package demo.main;

import java.awt.image.BufferedImage;

public class EntityFactory {

    public static Entity createHero(double x, double y) {

        Entity e = new Entity(0);

        // Position
        e.addComponent(new Position(x, y));

        // Load sprite sheet
        BufferedImage sheet = TextureManager.load("/sprite/hero_rightatk.png");//load("/sprite/hero.png");

        // Animation component
        EntityAnimation animComp = new EntityAnimation();

        // Idle animation
        Animation idle = new Animation(2, 0.40f);
        for (int i = 0; i < 2; i++) {
            idle.frames.add(sheet.getSubimage(i * 64, 64, 64, 64));
        }
        animComp.addAnimation("idle", idle);

        // Attack animation
        Animation attack = new Animation(6, 0.17f);
        for (int i = 0; i < 6; i++) {
            attack.frames.add(sheet.getSubimage(i * 192, 0, 192, 64));
        }
        animComp.addAnimation("attack", attack);

        e.addComponent(animComp);

        // Combat components
        e.addComponent(new Attack(1, 25, 74, 1.0)); //attack speed here is not control by this arg
        e.addComponent(new Accuracy(0.8f));
        e.addComponent(new AttackCoolDown(5.0f)); //this control the atk speed 1f default
        e.addComponent(new AttackState());
        //e.addComponent(new AttackProfile(0.51f, 0.17f, 0.34f));
        e.addComponent(new AttackProfile(0.51f, 0.34f, 0.17f)); //windup hitWindow recovery
        //e.addComponent(new AttackProfile(0.17f, 0.51f, 0.34f));  
        //e.addComponent(new AttackProfile(0.17f, 0.34f, 0.51f )); //windup hitWindow recovery
        //e.addComponent(new AttackProfile(0.34f, 0.17f, 0.51f )); //windup hitWindow recovery
        //e.addComponent(new AttackProfile(0.34f, 0.51f, 0.17f )); //windup hitWindow recovery
        
        // Collision + hitbox
        e.addComponent(new Collision(16, 34, 20, 28));
        e.addComponent(new AttackHitbox(40, 20, 66, 32));

        // Health
        e.addComponent(new Health(100));
        //def
        e.addComponent(new Defense(1, 4));
        //eva
        e.addComponent(new Evasion(0.2f)); // 0.0 - 0.5
        //crit
        e.addComponent(new CriticalHit(0.20f, 2.0f)); //20% chance, double damage
		
        //faction 
        e.addComponent(new Faction(Faction.Type.HERO));
        //target
        e.addComponent(new CurrentTarget());
         
        return e;
    }
    
    public static Entity createDummy(double x, double y) {

        Entity e = new Entity(1);

        // Position
        e.addComponent(new Position(x, y));

        // Load sprite sheet
        BufferedImage sheet = TextureManager.load("/sprite/enemy_64x64.png"); 

        // Animation component
        EntityAnimation animComp = new EntityAnimation();

        // Idle animation
        Animation idle = new Animation(13, 0.08f);
        for (int i = 0; i < 13; i++) {
            idle.frames.add(sheet.getSubimage(i * 64, 0, 64, 64));
        }
        animComp.addAnimation("idle", idle);

//        // Attack animation
//        Animation attack = new Animation(6, 0.17f);
//        for (int i = 0; i < 6; i++) {
//            attack.frames.add(sheet.getSubimage(i * 64, 0, 64, 64));
//        }
//        animComp.addAnimation("attack", attack);

        e.addComponent(animComp);

        // Combat components
        //e.addComponent(new Attack(1, 25, 74, 1.0));
        //e.addComponent(new Accuracy(0.8f));
       // e.addComponent(new AttackCoolDown(1f));
       // e.addComponent(new AttackState());
       // e.addComponent(new AttackProfile(0.51f, 0.17f, 0.34f));

        // Collision + hitbox
        e.addComponent(new Collision(16, 34, 20, 28));
        //e.addComponent(new AttackHitbox(40, 20, 66, 32));

        // Health
        e.addComponent(new Health(100));
        //def
        e.addComponent(new Defense(1, 4));
        //eva
        e.addComponent(new Evasion(0.2f)); // 0.0 - 0.5
        
        //faction 
        e.addComponent(new Faction(Faction.Type.ENEMY));
        //GM
        e.addComponent(new GodMode(GodMode.Mode.REGEN));
        return e;
    }
}

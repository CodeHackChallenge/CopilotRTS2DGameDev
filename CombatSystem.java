package demo.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CombatSystem {

    public void update(List<Entity> entities,
                       float delta,
                       List<HitDetectionSystem.HitEvent> hits) {

        List<Entity> toRemove = new ArrayList<>();
        List<Entity> toAdd = new ArrayList<>();

        // ---------------------------------------------------------
        // 1. Handle cooldowns and attack attempts
        // ---------------------------------------------------------
        for (Entity attacker : entities) {

            Health ah = attacker.getComponent(Health.class);
            if (ah == null || ah.current <= 0) continue;

            Attack atk = attacker.getComponent(Attack.class);
            Faction af = attacker.getComponent(Faction.class);
            Accuracy acc = attacker.getComponent(Accuracy.class);

            if (atk == null || af == null || acc == null)
                continue;

            // ---------------------------------------------------------
            // CurrentTarget (NO auto-targeting anymore)
            // ---------------------------------------------------------
            CurrentTarget ct = attacker.getComponent(CurrentTarget.class);
            if (ct == null) {
                ct = new CurrentTarget(null);
                attacker.addComponent(ct);
            }

            // If no target → do nothing
            if (ct.target == null) continue;

            // If target is dead → clear target
            Health th = ct.target.getComponent(Health.class);
            if (th == null || th.current <= 0) {
                ct.target = null;
                continue;
            }

            // ---------------------------------------------------------
            // Attack cooldown
            // ---------------------------------------------------------
            AttackCoolDown acd = attacker.getComponent(AttackCoolDown.class);
            if (acd == null) {
                acd = new AttackCoolDown(1f);
                attacker.addComponent(acd);
            }
          /*
            //for debugging
        	EntityAnimation animComp = attacker.getComponent(EntityAnimation.class);
        	Animation anim = animComp.getAnimation();  
            System.out.println("ID: "+ attacker.race +" CD: " + acd.cooldown + " | frame: " + anim.currentFrame);
          */
            acd.cooldown -= delta;
            
            
            if (acd.cooldown > 0f) continue;

            acd.cooldown = 1f / acd.attackSpeed;
           
            // ---------------------------------------------------------
            // 2. Check collision hits for this attacker
            // ---------------------------------------------------------
            for (HitDetectionSystem.HitEvent event : hits) {
            	 
                if (event.attacker != attacker) continue;

                // Only attack the chosen target
                if (event.target != ct.target) continue; 
                attackTarget(attacker, ct.target, toRemove, toAdd); 
            }
        }

        // ---------------------------------------------------------
        // 3. Apply entity removal and additions
        // ---------------------------------------------------------
        entities.addAll(toAdd);
        entities.removeAll(toRemove);
    }

    // -------------------------------------------------------------
    // Attack logic (unchanged from your original version)
    // -------------------------------------------------------------
    private void attackTarget(Entity attacker,
                              Entity target,
                              List<Entity> toRemove,
                              List<Entity> toAdd) {

        Attack atk = attacker.getComponent(Attack.class);
        Accuracy acc = attacker.getComponent(Accuracy.class);

        Health th = target.getComponent(Health.class);
        Evasion ev = target.getComponent(Evasion.class);
        Defense def = target.getComponent(Defense.class);

        if (th == null || ev == null || def == null) return;

        // ---------------------------------------------------------
        // HIT CHANCE
        // ---------------------------------------------------------
        float hitChance = acc.value - ev.value;
        hitChance = Math.max(0f, Math.min(1f, hitChance));

        float roll = ThreadLocalRandom.current().nextFloat();

        if (roll > hitChance) {
            spawnDamageText(target, "miss", toAdd, false);
            return;
        }

        // ---------------------------------------------------------
        // DAMAGE
        // ---------------------------------------------------------
        int rawDamage = random(atk.min, atk.max);
        int defense = random(def.min, def.max);

        int finalDamage = rawDamage - defense;
        if (finalDamage <= 0) finalDamage = 1;

        // ---------------------------------------------------------
        // CRITICAL HIT
        // ---------------------------------------------------------
        boolean isCrit = false;
        CriticalHit ch = attacker.getComponent(CriticalHit.class);

        if (ch != null) {
            float critRoll = ThreadLocalRandom.current().nextFloat();
            if (critRoll < ch.critChance) {
                isCrit = true;
                finalDamage = Math.round(finalDamage * ch.critMultiplier);
            }
        }

        // ---------------------------------------------------------
        // APPLY DAMAGE
        // ---------------------------------------------------------        
//        AttackCycle cycle = attacker.getComponent(AttackCycle.class);
//        if(cycle != null && cycle.hasHit) return; //already hit cycle
//        
        th.damage(finalDamage);

        
        spawnDamageText(target, String.valueOf(finalDamage), toAdd, isCrit);
//        if(cycle != null) {
//        	cycle.hasHit = true;
//        }
        // ---------------------------------------------------------
        // DEATH HANDLING
        // ---------------------------------------------------------
        if (th.current <= 0) {
            GodMode gm = target.getComponent(GodMode.class);
            if (gm != null) {
                gm.activate(target);
            } else {
                toRemove.add(target);
            }
        }
    }

    // -------------------------------------------------------------
    // Damage text (your original version)
    // -------------------------------------------------------------
    private void spawnDamageText(Entity target,
                                 String text,
                                 List<Entity> toAdd,
                                 boolean isCrit) {

        Position tp = target.getComponent(Position.class);

        //get collision box of target        
        Collision col = target.getComponent(Collision.class);
        
        double x = tp.x + col.offsetX;
        double y = tp.y + col.offsetY;
        
        int w = col.width; //32;
        int h = col.height; //32;

        int offsetX = ThreadLocalRandom.current().nextInt(0, w);
        int offsetY = ThreadLocalRandom.current().nextInt(0, h);

        
        Entity textEntity = new Entity();
        textEntity.addComponent(new Position(x, y));

        RenderTextComponent rtc =
                isCrit ? new RenderTextComponent(Color.ORANGE, 18)
                       : new RenderTextComponent(Color.WHITE, 14);

        textEntity.addComponent(rtc);

        DamageTextComponent dt = new DamageTextComponent(text, offsetX, offsetY);

        if (isCrit) {
            dt.isCrit = true;
            dt.scale = 1.4f;
            dt.shakeIntensity = 4f;
            dt.shakeTime = 0.15f;
        }

        textEntity.addComponent(dt);
        toAdd.add(textEntity);
    }

    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
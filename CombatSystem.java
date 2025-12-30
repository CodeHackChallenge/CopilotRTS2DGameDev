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

        for (Entity attacker : entities) {

            Health ah = attacker.getComponent(Health.class);
            if (ah == null || ah.current <= 0) continue;

            Attack atk = attacker.getComponent(Attack.class);
            Faction af = attacker.getComponent(Faction.class);
            Accuracy acc = attacker.getComponent(Accuracy.class);

            if (atk == null || af == null || acc == null)
                continue;

            CurrentTarget ct = attacker.getComponent(CurrentTarget.class);
            if (ct == null) {
                ct = new CurrentTarget(null);
                attacker.addComponent(ct);
            }

            if (ct.target == null) continue;

            Health th = ct.target.getComponent(Health.class);
            if (th == null || th.current <= 0) {
                ct.target = null;
                continue;
            }

            // ---------------------------------------------------------
            // ⭐ NEW: Only apply damage during HIT state
            // ---------------------------------------------------------
            AttackState as = attacker.getComponent(AttackState.class);
            if (as == null || as.state != AttackState.AttackStateType.HIT) {
                continue; // not in HIT → skip all damage
            }

            // ---------------------------------------------------------
            // 2. Process hit events for this attacker
            // ---------------------------------------------------------
            for (HitDetectionSystem.HitEvent event : hits) {

                if (event.attacker != attacker) continue;
                if (event.target != ct.target) continue;

                attackTarget(attacker, ct.target, toRemove, toAdd);
            }
        }

        entities.addAll(toAdd);
        entities.removeAll(toRemove);
    }

    // -------------------------------------------------------------
    // Attack logic (now synced with AttackStateSystem)
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

        AttackState as = attacker.getComponent(AttackState.class);
        if (as == null) return;

        // ---------------------------------------------------------
        // ⭐ Prevent multiple hits during the same attack animation
        // ---------------------------------------------------------
        if (as.hitApplied) {
            return;
        }

        // ---------------------------------------------------------
        // HIT CHANCE
        // ---------------------------------------------------------
        float hitChance = acc.value - ev.value;
        hitChance = Math.max(0f, Math.min(1f, hitChance));

        float roll = ThreadLocalRandom.current().nextFloat();

        if (roll > hitChance) {
            spawnDamageText(target, "miss", toAdd, false);
            as.hitApplied = true; // still counts as a hit attempt
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
        th.damage(finalDamage);

        // ⭐ Mark that this attack has already hit
        as.hitApplied = true;

        spawnDamageText(target, String.valueOf(finalDamage), toAdd, isCrit);

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

    private void spawnDamageText(Entity target,
                                 String text,
                                 List<Entity> toAdd,
                                 boolean isCrit) {

        Position tp = target.getComponent(Position.class);
        Collision col = target.getComponent(Collision.class);
      //random damage text spawn
//        double x = tp.x + col.offsetX;
//        double y = tp.y + col.offsetY; 
//        int w = col.width;
//        int h = col.height;
//
//        int offsetX = ThreadLocalRandom.current().nextInt(0, w);
//        int offsetY = ThreadLocalRandom.current().nextInt(0, h);
         
        
        //head position spawn
        double headX = tp.x + col.offsetX + (col.width / 2 - 5);
        double headY = tp.y + col.offsetY - 10; //10px above head
        
        
        Entity textEntity = new Entity();
        //textEntity.addComponent(new Position(x, y));
        textEntity.addComponent(new Position(headX, headY));
        
        RenderTextComponent rtc =
                isCrit ? new RenderTextComponent(Color.ORANGE, 18)
                       : new RenderTextComponent(Color.WHITE, 14);

        textEntity.addComponent(rtc);

        //DamageTextComponent dt = new DamageTextComponent(text, offsetX, offsetY);
        DamageTextComponent dt = new DamageTextComponent(text, 0, 0);

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
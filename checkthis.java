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
        // 1. Handle cooldowns and target selection
        // ---------------------------------------------------------
        for (Entity attacker : entities) {

            Health ah = attacker.getComponent(Health.class);
            if (ah == null || ah.current <= 0) continue;

            Attack atk = attacker.getComponent(Attack.class);
            Faction af = attacker.getComponent(Faction.class);
            Accuracy acc = attacker.getComponent(Accuracy.class);
            Position ap = attacker.getComponent(Position.class);

            if (atk == null || af == null || acc == null || ap == null)
                continue;

            // ---------------------------------------------------------
            // CurrentTarget component (keep your system)
            // ---------------------------------------------------------
            CurrentTarget ct = attacker.getComponent(CurrentTarget.class);
            if (ct == null) {
                ct = new CurrentTarget(null);
                attacker.addComponent(ct);
            }

            // Validate current target
            if (ct.target != null) {
                Health th = ct.target.getComponent(Health.class);
                if (th == null || th.current <= 0) {
                    ct.target = null;
                }
            }

            // Acquire new target if needed
            if (ct.target == null) {
                ct.target = findClosestEnemy(attacker, entities, atk.range);
            }

            // No target found
            if (ct.target == null) continue;

            // ---------------------------------------------------------
            // Attack cooldown
            // ---------------------------------------------------------
            AttackCoolDown acd = attacker.getComponent(AttackCoolDown.class);
            if (acd == null) {
                acd = new AttackCoolDown(1f);
                attacker.addComponent(acd);
            }

            acd.cooldown -= delta;
            if (acd.cooldown > 0f) continue;

            acd.cooldown = 1f / acd.attackSpeed;

            // ---------------------------------------------------------
            // 2. Check collision hits for this attacker
            // ---------------------------------------------------------
            for (HitDetectionSystem.HitEvent event : hits) {

                if (event.attacker != attacker) continue;

                // Only attack the current target
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
    // Your original findClosestEnemy (unchanged)
    // -------------------------------------------------------------
    private Entity findClosestEnemy(Entity attacker, List<Entity> entities, int range) {
        Position ap = attacker.getComponent(Position.class);
        Faction af = attacker.getComponent(Faction.class);

        Entity closest = null;
        double closestDist = Double.MAX_VALUE;

        for (Entity target : entities) {
            if (attacker == target) continue;

            Faction tf = target.getComponent(Faction.class);
            Health th = target.getComponent(Health.class);
            Position tp = target.getComponent(Position.class);

            if (tf == null || th == null || tp == null) continue;
            if (tf.type == af.type) continue;

            int dx = (int) (tp.x - ap.x);
            int dy = (int) (tp.y - ap.y);
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist <= range && dist < closestDist) {
                closestDist = dist;
                closest = target;
            }
        }
        return closest;
    }

    // -------------------------------------------------------------
    // Your original attackTarget, cleaned and collision‑ready
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
        th.damage(finalDamage);

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

    // -------------------------------------------------------------
    // Your original damage text spawner (unchanged)
    // -------------------------------------------------------------
    private void spawnDamageText(Entity target,
                                 String text,
                                 List<Entity> toAdd,
                                 boolean isCrit) {

        Position tp = target.getComponent(Position.class);

        int w = 32;
        int h = 32;

        int offsetX = ThreadLocalRandom.current().nextInt(0, w);
        int offsetY = ThreadLocalRandom.current().nextInt(0, h);

        Entity textEntity = new Entity();
        textEntity.addComponent(new Position(tp.x, tp.y));

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

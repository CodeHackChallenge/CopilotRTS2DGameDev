package demo.main; 

import java.util.List;

public class AttackStateSystem {

    public static boolean DEBUG_FORCE_ATTACK = false;

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            AttackState as = e.getComponent(AttackState.class);
            AttackProfile ap = e.getComponent(AttackProfile.class);
            AttackCoolDown acd = e.getComponent(AttackCoolDown.class);
            CurrentTarget ct = e.getComponent(CurrentTarget.class);

            if (as == null || ap == null || acd == null) continue;

            // Advance timer
            as.timer += delta;

            // ---------------------------------------------------------
            // DEBUG: Force an attack cycle start
            // ---------------------------------------------------------
            if (as.state == AttackState.AttackStateType.IDLE && DEBUG_FORCE_ATTACK) {
                DEBUG_FORCE_ATTACK = false;
                as.state = AttackState.AttackStateType.WINDUP;
                as.timer = 0f;
            }

            switch (as.state) {

                case IDLE:
                    // Auto-start attack if cooldown is ready and target exists
                    if (acd.cooldown <= 0f && ct != null && ct.target != null) {
                        as.state = AttackState.AttackStateType.WINDUP;
                        as.timer = 0f;
                        
                        //switch to attack animation
                        EntityAnimation anim = e.getComponent(EntityAnimation.class);
                        if(anim != null) anim.play("attack");
                    }
                    break;

                case WINDUP:
                    if (as.timer >= ap.windup) {
                        as.state = AttackState.AttackStateType.HIT;
                        as.timer = 0f;
                    }
                    break;

                case HIT:
                    if (as.timer >= ap.hitWindow) {
                        as.state = AttackState.AttackStateType.RECOVERY;
                        as.timer = 0f;
                    }
                    break;

                case RECOVERY:
                    if (as.timer >= ap.recovery) {
                        as.state = AttackState.AttackStateType.IDLE;
                        as.timer = 0f;

                        // Reset cooldown for next attack
                        acd.cooldown = 1f / acd.attackSpeed;

                        // ⭐ Reset hit flag for next attack cycle
                        as.hitApplied = false;
                    }
                    break;
            }

            // Tick down cooldown
            if (acd.cooldown > 0f)
                acd.cooldown -= delta;
        }
    }
}
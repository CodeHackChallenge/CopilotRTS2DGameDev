public class AttackStateSystem {

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            AttackState as = e.getComponent(AttackState.class);
            AttackProfile ap = e.getComponent(AttackProfile.class);
            AttackCoolDown acd = e.getComponent(AttackCoolDown.class);
            CurrentTarget ct = e.getComponent(CurrentTarget.class);

            if (as == null || ap == null || acd == null) continue;

            as.timer += delta;

            switch (as.state) {

                case IDLE:
                    if (acd.cooldown <= 0f && ct != null && ct.target != null) {
                        as.state = AttackStateType.WINDUP;
                        as.timer = 0f;
                    }
                    break;

                case WINDUP:
                    if (as.timer >= ap.windup) {
                        as.state = AttackStateType.HIT;
                        as.timer = 0f;
                    }
                    break;

                case HIT:
                    if (as.timer >= ap.hitWindow) {
                        as.state = AttackStateType.RECOVERY;
                        as.timer = 0f;
                    }
                    break;

                case RECOVERY:
                    if (as.timer >= ap.recovery) {
                        as.state = AttackStateType.IDLE;
                        as.timer = 0f;
                        acd.cooldown = 1f / acd.attackSpeed;
                    }
                    break;
            }

            if (acd.cooldown > 0f)
                acd.cooldown -= delta;
        }
    }
}

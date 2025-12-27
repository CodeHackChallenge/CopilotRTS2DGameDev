import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class HitDetectionSystem {

    public static class HitEvent {
        public final Entity attacker;
        public final Entity target;

        public HitEvent(Entity attacker, Entity target) {
            this.attacker = attacker;
            this.target = target;
        }
    }

    private final List<HitEvent> currentHits = new ArrayList<>();

    public void update(List<Entity> entities, float delta) {
        currentHits.clear();

        int size = entities.size();
        for (int i = 0; i < size; i++) {
            Entity a = entities.get(i);

            Attack attackA = a.getComponent(Attack.class);
            Faction factionA = a.getComponent(Faction.class);
            Collision colA = a.getComponent(Collision.class);
            Position posA = a.getComponent(Position.class);
            Health hpA = a.getComponent(Health.class);

            if (attackA == null || factionA == null || colA == null || posA == null || hpA == null)
                continue;
            if (hpA.current <= 0) continue; // dead attackers don't hit

            Rectangle boundsA = colA.getBounds(posA);

            for (int j = 0; j < size; j++) {
                if (i == j) continue;

                Entity b = entities.get(j);

                Faction factionB = b.getComponent(Faction.class);
                Collision colB = b.getComponent(Collision.class);
                Position posB = b.getComponent(Position.class);
                Health hpB = b.getComponent(Health.class);

                if (factionB == null || colB == null || posB == null || hpB == null)
                    continue;
                if (hpB.current <= 0) continue; // dead targets don't get hit

                // Only enemies
                if (factionA.type == factionB.type) continue;

                Rectangle boundsB = colB.getBounds(posB);

                if (boundsA.intersects(boundsB)) {
                    currentHits.add(new HitEvent(a, b));
                }
            }
        }
    }

    public List<HitEvent> getCurrentHits() {
        return currentHits;
    }
}

package demo.main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import demo.main.AttackState.AttackStateType;

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

            Entity attacker = entities.get(i);

            // Attacker must have a weapon hitbox
            AttackHitbox atkHit = attacker.getComponent(AttackHitbox.class);
            if (atkHit == null) continue;
            
            Position posA = attacker.getComponent(Position.class);
            Faction facA = attacker.getComponent(Faction.class);
            Health hpA = attacker.getComponent(Health.class);

            if (posA == null || facA == null || hpA == null) continue;
            if (hpA.current <= 0) continue;

            Rectangle swordBox = atkHit.getBounds(posA);
            
            // ---------------------------------------------------------
            // Check all potential targets
            // ---------------------------------------------------------
            for (int j = 0; j < size; j++) {

                if (i == j) continue;

                Entity target = entities.get(j);

                Collision colB = target.getComponent(Collision.class);
                Position posB = target.getComponent(Position.class);
                Faction facB = target.getComponent(Faction.class);
                Health hpB = target.getComponent(Health.class);

                if (colB == null || posB == null || facB == null || hpB == null)
                    continue;

                if (hpB.current <= 0) continue;

                // Must be enemy
                if (facA.type == facB.type) continue;

                Rectangle bodyBox = colB.getBounds(posB);

                // ---------------------------------------------------------
                // Sword hitbox intersects target hurtbox
                // ---------------------------------------------------------
                if (swordBox.intersects(bodyBox)) { 
                	//------------debug-------------------
                	 // System.out.println("DEBUG: Collision detected " + attacker.ID+" -> "+target.ID);  
                	 
                    //------------------------------------
                    currentHits.add(new HitEvent(attacker, target));
                }
            }
        }
    }

    public List<HitEvent> getCurrentHits() {
        return currentHits;
    }
}
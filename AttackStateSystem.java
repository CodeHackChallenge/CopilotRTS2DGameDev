package demo.main;

import java.util.List;

import demo.main.AttackState.AttackStateType;

public class AttackStateSystem {

	public static boolean DEBUG_FPRCE_ATTACK = false;
	
    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            AttackState as = e.getComponent(AttackState.class);
            AttackProfile ap = e.getComponent(AttackProfile.class);
            AttackCoolDown acd = e.getComponent(AttackCoolDown.class);
            CurrentTarget ct = e.getComponent(CurrentTarget.class);

            if (as == null || ap == null || acd == null) continue;
            
            as.timer += delta;
            //------------debug-------------------
            	//if(as.state == AttackStateType.HIT) {
            		//System.out.println("DEBUG: HIT state active"); ok
            	//}
            //------------------------------------
            switch (as.state) {
            	
                case IDLE:
                	//debug-----------------------------------
	                	if(DEBUG_FPRCE_ATTACK) {
	                		DEBUG_FPRCE_ATTACK = false;
	                		as.state = AttackStateType.WINDUP;
	                		//as.state = AttackStateType.HIT;
	                		as.timer = 0f;
	                		
	                		System.out.println(" DEBUG: Forced at start");
	                	} 
                	//----------------------------------------
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

                case HIT: //System.out.println(as.state + " id "+e.race);
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
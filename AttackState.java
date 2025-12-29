package demo.main;



public class AttackState implements Component {
	
	public enum AttackStateType {
	    IDLE,
	    WINDUP,
	    HIT,
	    RECOVERY
	}
	
    public AttackStateType state = AttackStateType.IDLE;
    
    public float timer = 0f;
    public boolean hitApplied = false;
}
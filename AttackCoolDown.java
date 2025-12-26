package demo.main;

public class AttackCoolDown implements Component{

	public float attackSpeed;
	public float cooldown; 
	
	public AttackCoolDown(float attackSpeed) { 
		this.attackSpeed = attackSpeed;
		this.cooldown = 0f;
	}
}

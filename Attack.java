package demo.main;

public class Attack implements Component {

	public int damage;
	private boolean isInCombat;
	private double atkCounter = 0;
	private double atkSpeed = 0;
	
	public int min;
	public int max;
	public int range;
	
	public Attack(int damage) {
		this.damage = damage;
		
		isInCombat = true;
	}
	
	public Attack(int min, int max, int range, double atkSpeed) {
		this.min = min;
		this.max = max;
		this.range = range;
		this.atkSpeed = atkSpeed;
		
		isInCombat = true;
	}
	
	public void update() {
		atkCounter += atkSpeed;
	}
	
	public void increaseDmg(int increase) {
		this.damage += increase;
	}
	
	public void setDmg(int dmg) {
		this.damage = dmg;
	}
	
	public void isInCombat(boolean status) { this.isInCombat = status; }
	public boolean isInCombat() { return isInCombat; }
	 
	
	public int atkCounter() {return (int) Math.round(atkCounter);}
	public void setAtkCounter(double count) { this.atkCounter += count;}
	public void setCounter(double count) { this.atkCounter = count;}
	public void setAtkSpeed(double speed) { this.atkSpeed = speed;}
	public double getAtkSpeed() {return atkSpeed;}
}

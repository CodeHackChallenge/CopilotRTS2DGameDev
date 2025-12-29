package demo.main;

public class CriticalHit implements Component {
	
	public float critChance; //0.0-1.0
	public float critMultiplier; //1.5f or 2.0f
	
	public CriticalHit(float critChance, float critMultiplier) {
		this.critChance = critChance;
		this.critMultiplier = critMultiplier;
	}

}

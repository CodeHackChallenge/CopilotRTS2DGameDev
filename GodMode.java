package demo.main;

public class  GodMode implements Component {
	
	public enum Mode { REGEN, DEFENSE, ATTACK, MAGIC}
	public Mode mode;
	public boolean isGodMode;
	
	public GodMode(Mode mode) {
		this.mode = mode;
		
		isGodMode = false;
	}
	public void activate(Entity target) {
		
		switch(mode) {
		
		case REGEN: 
			Health h = target.getComponent(Health.class);
			h.health = h.restoreHP;
			System.out.println("GodMode -> HP restored: "+ h.health);
			break;
		case DEFENSE: 
			break;
		case ATTACK: 
			break;
		case MAGIC: 
			break;
		}
		
	}
	
	

}

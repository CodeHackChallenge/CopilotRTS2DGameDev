package demo.main;

public class CurrentTarget implements Component{
	
	public Entity target;
	
	public CurrentTarget() {
		this.target = null;
	}
	
	public CurrentTarget(Entity target) {
		this.target = target;
	}

}

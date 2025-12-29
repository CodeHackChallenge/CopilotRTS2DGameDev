package demo.main;

import java.util.List;

public class HealthBarSystem {
	 	
	private static final float SMOOTH_SPEED = 10f; // higher = faster
	
	public void update(List<Entity> entities, float delta) {
		
		for(Entity e: entities) {
			Health hp = e.getComponent(Health.class);
			if(hp == null) continue;
			
			//lerp displayed hp towards actual HP
			hp.displayed = lerp(hp.displayed, hp.current, delta * SMOOTH_SPEED);
			
		}
	}

	private float lerp(float a, int b, float t) {
		 
		return a + (b - a) * t; //formula
	}

}

package demo.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DamageTextSystem {

	public void update(List<Entity> entities, float delta) {
		List<Entity> toRemove = new ArrayList<>();
		
		for(Entity e : entities) {
			
			DamageTextComponent dt = e.getComponent(DamageTextComponent.class);
			Position pos = e.getComponent(Position.class);
			
			if(dt == null || pos == null) continue;
			
			dt.elapsed += delta;
			dt.alpha = 1f - (dt.elapsed / dt.lifetime);
			
			//move upward
			pos.y += dt.velocity * delta;
			
			//crit and shake
			if(dt.isCrit && dt.shakeTime > 0f) {
				dt.shakeTime -= delta;
				
				float shakeX = (ThreadLocalRandom.current().nextFloat() * 2f - 1f) * dt.shakeIntensity;
				float shakeY = (ThreadLocalRandom.current().nextFloat() * 2f - 1f) * dt.shakeIntensity;
				
				pos.x += shakeX;
				pos.y += shakeY;
			}
			
			if(dt.alpha <= 0f) {
				toRemove.add(e);
			}
		}
		
		entities.removeAll(toRemove);
	}
}

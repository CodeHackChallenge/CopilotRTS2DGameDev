package demo.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CombatSystem {
 
	//private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();
	private double removeCounter = 0;
	public void update(List<Entity> entities) {
		List<Entity> toRemove = new ArrayList<>();
		List<Entity> toAdd = new ArrayList<>();
		
		for(Entity attacker : entities) {

			Health ah = attacker.getComponent(Health.class);
			
			if(ah == null || ah.getHealth() <= 0) {continue;}
			
			Attack atk = attacker.getComponent(Attack.class);
			Faction af = attacker.getComponent(Faction.class); 
			Accuracy acc = attacker.getComponent(Accuracy.class);
			Position ap = attacker.getComponent(Position.class);
			
			if(atk == null || af == null || acc == null) {continue;}
			//-------------------------------------------------------------------
			// Get or create current target
			//-------------------------------------------------------------------			
			CurrentTarget ct = attacker.getComponent(CurrentTarget.class);
			if(ct == null) {
				ct = new CurrentTarget(null);
				attacker.addComponent(ct);
			}
			//-------------------------------------------------------------------
			// validate current target
			//-------------------------------------------------------------------			
			if(ct.target != null) {
				Health th = ct.target.getComponent(Health.class);
				//if target is dead or removed
				if(th == null || th.getHealth() <= 0) {
					ct.target = null;
				
				}else {
					//check distance
					Position tp = ct.target.getComponent(Position.class);
					int dx = (int) (tp.x - ap.x);
					int dy = (int) (tp.y - ap.y);
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist > atk.range) {
						ct.target = null; //out of range: drop target
					}
				}
				
			}//if(ct.target != null)
			//-------------------------------------------------------------------
			// acquire new target if needed
			//-------------------------------------------------------------------			
			if(ct.target == null) {
				ct.target =  findClosestEnemy(attacker, entities, atk.range);
			}
			
			//No target found
			if(ct.target == null) continue; 
			//-------------------------------------------------------------------
			// attack cooldown handling
			//-------------------------------------------------------------------			
			AttackCoolDown acd = attacker.getComponent(AttackCoolDown.class);
			if(acd == null) {
				acd = new AttackCoolDown(1f); //default 1 attack per second
				attacker.addComponent(acd);
			}
			//reduce cooldown
			acd.cooldown -=  0.005;//1 / Engine.UPS;//delta;
			
			//cannot attack yet
			if(acd.cooldown > 0f) continue;
			
			//attack is allowed
			acd.cooldown = 1f / acd.attackSpeed;
			
			
			//-------------------------------------------------------------------
			// attack the current target only
			//-------------------------------------------------------------------			
			attackTarget(attacker, ct.target, toRemove, toAdd); 
			
		}
		//-------------------------------------------------------------------
		// remove dead units after combat loop
		//-------------------------------------------------------------------	
		entities.addAll(toAdd);
		entities.removeAll(toRemove);
		//removeCounter += 0.2;//TODO: work here the logic should be in the system
		//System.out.println(removeCounter);
		/*
		if(removeCounter >= Engine.UPS) {	
			for(Entity e : entities) {
				if(e.hasComponent(DamageEvent.class)) {  
					removeCounter = 0;
					e.removeComponent(DamageEvent.class); 
				}
			} 
		}
		 */
	}
	/**
	 * find closest enemy within range
	 * @param attacker
	 * @param entities
	 * @param range
	 * @return
	 */
	private Entity findClosestEnemy(Entity attacker, List<Entity> entities, int range) {
		Position ap = attacker.getComponent(Position.class);
		Faction af = attacker.getComponent(Faction.class);
		
		Entity closest = null;
		double closestDist = Double.MAX_VALUE;
		
		for(Entity target : entities) {
			if(attacker == target) continue; //don't attack yourself
			
			Faction tf = target.getComponent(Faction.class);
			Health th = target.getComponent(Health.class);
			Position tp = target.getComponent(Position.class);
			
			if(tf == null || th == null || tp == null) continue;
			if(tf.type == af.type) continue; //same faction 
			
			int dx = (int) (tp.x - ap.x);
			int dy = (int) (tp.y - ap.y);
			double dist = Math.sqrt(dx * dx + dy * dy);
			
			if(dist <= range && dist < closestDist) {
				closestDist = dist;
				closest = target;
			}
			
		}
		return closest;
	}
	/**
	 * attack logic for a single target
	 */
	private void attackTarget(Entity attacker, 
							  Entity target, 
							  List<Entity> toRemove,  
							  List<Entity> toAdd) {
		Attack atk = attacker.getComponent(Attack.class);
		Accuracy acc = attacker.getComponent(Accuracy.class);
		Position ap = attacker.getComponent(Position.class);
		
		Health th = target.getComponent(Health.class);
		Evasion ev = target.getComponent(Evasion.class);
		Defense def = target.getComponent(Defense.class);
		Position tp = target.getComponent(Position.class);
		
		if(th == null || ev == null || def == null || tp == null) return;
		//distance check
		int dx = (int) (tp.x - ap.x);
		int dy = (int) (tp.y - ap.y);
		double dist = Math.sqrt(dx * dx + dy * dy);
		
		if(dist > atk.range) return;
		//update counter for attack
		//atk.update();
		
		//if(atk.isInCombat() && atk.atkCounter() >= Engine.UPS) {
		//	atk.setCounter(0.0); //reset counter for attacking			
			//atk.isInCombat(false); //TODO: attack is disabled here
			
			//-------------------------------------------------------------------
			// hit calculation
			//-------------------------------------------------------------------	 
			float hitChance = acc.value - ev.value;
			hitChance = Math.max(0f, Math.min(1f, hitChance)); //clamp 0-1;
			
			float roll = ThreadLocalRandom.current().nextFloat();
			
			if(roll > hitChance) {
				//miss or doge
				//if(acc.value < ev.value) {
					//target.addComponent(new DamageEvent("dodge")); //dodge 
				//} else {
					//target.addComponent(new DamageEvent("miss")); //miss
				//}
				//Miss or dodge
				//String text = (acc.value < ev.value) ? "1" : "miss";
				spawnDamageText(target, "miss", toAdd, false); //only miss no dodge
				
				return; 
			}
			//-------------------------------------------------------------------
			// damage calculation
			//-------------------------------------------------------------------	  
			int rawDamage = random(atk.min, atk.max);
			//def
			int defense = random(def.min, def.max);
			//
			int finalDamage = rawDamage - defense;// Math.max(0, rawDamage - defense);
			if(finalDamage <= 0) {
				//target.addComponent(new DamageEvent("blocked"));
				//spawnDamageText(target, "1", toAdd);
				finalDamage = 1;				
				//return;
			}
			//-------------------------------------------------------------------
			// crit hit check
			//-------------------------------------------------------------------	  
			boolean isCrit = false;
			CriticalHit ch = attacker.getComponent(CriticalHit.class);
			
			if(ch != null) {
				float critRoll = ThreadLocalRandom.current().nextFloat();
				if(critRoll < ch.critChance) {
					isCrit = true;
					finalDamage = Math.round(finalDamage * ch.critMultiplier);
					System.out.println(attacker.race + " CRIT dmg: " + finalDamage + " HP: "+ th.health); 
					
				}
			}
			
			//apply damage   
			double result = th.health -= finalDamage;
			
			th.health = result;//.setHealth(result);
			//target.addComponent(new DamageEvent(String.valueOf(finalDamage)));  
			spawnDamageText(target, String.valueOf(finalDamage), toAdd, isCrit);
			System.out.println(attacker.race + " has attack! dmg: " + finalDamage + " HP: "+ th.health); 
			
			//mark for removal if dead
			if(th.health <= 0) {
				GodMode gm = target.getComponent(GodMode.class);
				if(gm != null) {
					gm.activate(target);
				}else {
					toRemove.add(target);
				}
				 
			 }
		//}//if(atk.isInCombat() && atk.atkCounter() >= Engine.UPS
		
	} 
	

	private void spawnDamageText(Entity target, 
								 String text, 
								 List<Entity> toAdd,
								 boolean isCrit) {
		
		Position tp = target.getComponent(Position.class);
		 
		int w = 32;
		int h = 32;
		
		int offsetX = ThreadLocalRandom.current().nextInt(0, w);
		int offsetY = ThreadLocalRandom.current().nextInt(0, h);
		
		Entity textEntity = new Entity();
		textEntity.addComponent(new Position(tp.x, tp.y));
		
		RenderTextComponent rtc;
		
		if(isCrit) {
			rtc = new RenderTextComponent(Color.ORANGE, 18);
			
		}else {
			rtc = new RenderTextComponent(Color.WHITE, 14);
		}
		
		textEntity.addComponent(rtc);
		
		DamageTextComponent dt = new DamageTextComponent(text, offsetX, offsetY);
		
		if(isCrit) {
			dt.isCrit = true;
			dt.scale = 1.4f; //40% bigger
			dt.shakeIntensity = 4f; //radius
			dt.shakeTime = 0.15f; //duration
		}
		
		textEntity.addComponent(dt);
		
		/*
		Entity textEntity = new Entity();
		textEntity.addComponent(new Position(tp.x, tp.y));
		textEntity.addComponent(new RenderTextComponent(Color.WHITE, 14));
		textEntity.addComponent(new DamageTextComponent(text, offsetX, offsetY));
		*/
		//entities.add(textEntity);
		toAdd.add(textEntity);
	}
	/**
	 * Utility method
	 * @param min
	 * @param max
	 * @return
	 */
	private int random(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}

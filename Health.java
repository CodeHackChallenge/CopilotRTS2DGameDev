package demo.main;

import java.awt.Color;
import java.awt.Image;
 
public class Health implements Component {
	
	public int current;
	public int max;
	public float displayed; //for lerp smooth display
	
	public Health(int max) {
		this.max = max;
		this.current = max;
		this.displayed = max;
	}
	
	public void set(int value) {
		this.current = Math.max(0, Math.min(value, max));
	}
	
	public void damage(int amount) {
		set(current - amount);
	}
	
	public void heal(int amount) {
		set(current + amount);
	}
	
	public float getPercent() {
		return (float) current / max;
	}
	 
	public float getDisplayedPrecent() {
		return displayed / max;
	}
}
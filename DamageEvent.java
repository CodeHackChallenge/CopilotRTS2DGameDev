package demo.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DamageEvent  implements Component{
	 
	public String damage;
	public double txtSpeed = 1.9;
	public double txtCounter = 0;
	public Position position;
	
	public DamageEvent(String damage) {  
		System.out.println(damage);
		this.damage = damage; 
	}
	 
}

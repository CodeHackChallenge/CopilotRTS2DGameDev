package demo.main;

import java.awt.Color;
import java.awt.Image;
 
public class Health implements Component {
	public static final int HP_GREEN = 0, HP_ORANGE = 1, HP_RED = 2;
	
	public double restoreHP;
	public double health;
	private int width, height;
	
	private Image[] healthbar;
	
	public Health(int health) {
		this.health = health;
		this.restoreHP = health;
		
		healthbar = new Image[3];
		
		healthbar[HP_GREEN] = ImageLoader.createBar(Color.GREEN);
		healthbar[HP_ORANGE] = ImageLoader.createBar(Color.ORANGE);
		healthbar[HP_RED] = ImageLoader.createBar(Color.RED);
		
		
		this.width = healthbar[0].getWidth(null);
		this.height = healthbar[0].getHeight(null);
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public Image[] getHealthbar() {
		return healthbar;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void restoreHP() {
		health = restoreHP;
		
	}
 
}
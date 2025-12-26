package demo.main;
 
import java.awt.Color;
import java.awt.Image;

public class Mana implements Component {

	public double mana;
	public int width, height;
	
	public Image manaBar;
	
	public Mana(int mana) {
		this.mana = mana; 
		
		manaBar = ImageLoader.createBar(Color.BLUE);
		
		
		this.width = manaBar.getWidth(null);
		this.height = manaBar.getHeight(null);
	}
}

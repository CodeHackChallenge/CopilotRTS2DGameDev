package demo.main;

public class DamageTextComponent implements Component {
	
	public String text; 
	public float lifetime = 1.2f;
	public float elapsed = 0f;
	public float alpha = 1f;
	
	public int offsetX;
	public int offsetY;
	
	public float velocity = -70f;//-20f; //pixels per seconds
	//critical hit
	public boolean isCrit = false;
	public float shakeIntensity = 0f;
	public float shakeTime = 0f;
	public float scale = 1;
	
	public DamageTextComponent(String text, int offsetX, int offsetY) {
		this.text = text;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
	}

}

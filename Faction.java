package demo.main;

public class Faction implements Component {

	public enum Type { HERO, ENEMY}
	public Type type;
	
	public Faction(Type type) { this.type = type;}
	
	public Type getFaction() {return type;}
	
	
}

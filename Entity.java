package demo.main;

import java.util.HashMap;
import java.util.Map;

public class Entity {
	
	private final int ID;
	//stores component by their type
	private final Map<Class<? extends Component>, Component> components = new HashMap<>();
	public final String race;
	
	public Entity() { 
		this.ID = 0;
		this.race = "Human"; 
	}
	
	public Entity(int id) {
		this.ID = id;
		this.race = "Human";
		
	}
	public Entity(String race) {
		this.ID = 0;
		this.race = race;
	}
	 
	public <T extends Component> void addComponent(T component) {
		components.put(component.getClass(), component);
	}
	
	public <T extends Component> T getComponent(Class<T> type) {
		return type.cast(components.get(type));
	}
	
	public <T extends Component> void removeComponent(Class<T> type) {
		components.remove(type);		
	}
	
	public <T extends Component> boolean hasComponent(Class<T> type) {
		return components.containsKey(type);		
	}
	
	public int getID() {
		return ID;
	}

}

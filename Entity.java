import java.util.HashMap;
import java.util.Map;

public class Entity {
    private int id;
    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    public Entity(int id) { this.id = id; }
    public int getId() { return id; }

    public <T extends Component> void addComponent(T component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        return type.cast(components.get(type));
    }
}

public class GameContext {
    private final Map<Class<?>, Object> resources = new HashMap<>();

    public <T> void addResource(Class<T> type, T resource) {
        resources.put(type, resource);
    }

    public <T> T getResource(Class<T> type) {
        return type.cast(resources.get(type));
    }
}

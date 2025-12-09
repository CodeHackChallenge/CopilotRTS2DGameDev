import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Example: Config asset
class Config implements Asset {
    private final String name;
    private final Properties properties;

    public Config(String name, Properties properties) {
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String getName() { return name; }

    public String get(String key) { return properties.getProperty(key); }
}

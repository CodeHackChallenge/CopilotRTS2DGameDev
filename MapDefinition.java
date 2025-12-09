import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



// Example: Map definition asset
class MapDefinition implements Asset {
    private final String name;
    private final List<String> lines;

    public MapDefinition(String name, List<String> lines) {
        this.name = name;
        this.lines = lines;
    }

    @Override
    public String getName() { return name; }

    public List<String> getLines() { return lines; }
}


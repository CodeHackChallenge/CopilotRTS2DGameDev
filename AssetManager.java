import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


// AssetManager with type-safe generics
public class AssetManager {
    private final Map<String, Asset> cache = new ConcurrentHashMap<>();

    // Generic loader
    public <T extends Asset> T load(String relativePath, AssetFactory<T> factory) throws IOException {
        // Check cache
        if (cache.containsKey(relativePath)) {
            return (T) cache.get(relativePath);
        }

        // Filesystem first
        Path fsPath = Paths.get(relativePath);
        InputStream in;
        if (Files.exists(fsPath)) {
            in = Files.newInputStream(fsPath);
        } else {
            in = AssetManager.class.getResourceAsStream("/" + relativePath);
            if (in == null) {
                throw new FileNotFoundException("Asset not found: " + relativePath);
            }
        }

        // Build typed asset
        T asset = factory.create(relativePath, in);
        cache.put(relativePath, asset);
        return asset;
    }

    // Factory interface for type-safe construction
    public interface AssetFactory<T extends Asset> {
        T create(String name, InputStream in) throws IOException;
    }
}

package sunposition.springday.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryCache<K, V> {

    private static final int MAX_SIZE = 100;
    private final Map<K, V> cacheMap = new HashMap<>();

    public void put(K key, V value) {
        if (key != null && value != null) {
            cacheMap.put(key, value);
            if (cacheMap.size() >= MAX_SIZE) {
                cacheMap.clear();
            }
        }
    }

    public V get(K key) {
        if (key != null) {
            return cacheMap.get(key);
        }
        return null;
    }

    public boolean containsKey(K key) {
        if (key != null) {
            return cacheMap.containsKey(key);
        }
        return false;
    }

    public void remove(K key) {
        if (key != null) {
            cacheMap.remove(key);
        }
    }

    public void clear() {
        cacheMap.clear();
    }

    public Iterable<Map.Entry<K, V>> getNativeCache() {
        return cacheMap.entrySet();
    }
}

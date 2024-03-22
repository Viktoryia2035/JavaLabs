package sunposition.springday.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class Cache<K, V> {
    private final Map<K, V> cacheMap = new ConcurrentHashMap<>();
    private static final int MAX_SIZE = 100;

    public void put(final K key, final V value) {
        cacheMap.put(key, value);
        if (cacheMap.size() > MAX_SIZE) {
            removeOldestEntry();
        }
    }

    public V get(final K key) {
        return cacheMap.get(key);
    }

    public void remove(final K key) {
        cacheMap.remove(key);
    }

    public boolean containsKey(final K key) {
        return cacheMap.containsKey(key);
    }

    public void clear() {
        cacheMap.clear();
    }

    private void removeOldestEntry() {
        if (!cacheMap.isEmpty()) {
            K oldestKey = cacheMap.keySet().iterator().next();
            cacheMap.remove(oldestKey);
        }
    }
}

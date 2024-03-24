package sunposition.springday.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class DataCache<String, Object> {
    private final Map<String, Object> hashMap = new ConcurrentHashMap<>();
    private static final int MAX_SIZE = 100;

    public void put(final String key, final Object value) {
        hashMap.put(key, value);
        if (hashMap.size() > MAX_SIZE) {
            removeOldestEntry();
        }
    }

    public Object get(final String key) {
        return hashMap.get(key);
    }

    public void remove(final String key) {
        hashMap.remove(key);
    }

    public void clear() {
        hashMap.clear();
    }

    private void removeOldestEntry() {
        if (!hashMap.isEmpty()) {
            String oldestKey = hashMap.keySet().iterator().next();
            hashMap.remove(oldestKey);
        }
    }
}

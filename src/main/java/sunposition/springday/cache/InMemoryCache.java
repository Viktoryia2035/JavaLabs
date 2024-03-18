package sunposition.springday.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryCache {

    private static final int MAX_SIZE = 100;
    private final Map<String, Object> cacheMap = new HashMap<>();

    public void put(String key, Object value) {
        if (key != null && value != null) {
            if (cacheMap.containsKey(key)) {
                cacheMap.put(key, value);
            } else {
                cacheMap.put(key, value);
                if (cacheMap.size() >= MAX_SIZE) {
                    cacheMap.clear();
                }
            }
        }
    }

    public Object get(String key) {
        if (key != null) {
            return cacheMap.get(key);
        }
        return null;
    }

    public boolean containsKey(String key) {
        if (key != null) {
            return cacheMap.containsKey(key);
        }
        return false;
    }

    public void remove(String key) {
        if (key != null) {
            cacheMap.remove(key);
        }
    }

    public void clear() {
        cacheMap.clear();
    }
}
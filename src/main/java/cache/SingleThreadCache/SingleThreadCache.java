package cache.SingleThreadCache;

import cache.Cache;
import cache.order.Order;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.HashMap;
import java.util.Map;

public class SingleThreadCache implements Cache {
    private Map<Object, Object> cache;
    private Order order;
    private final int size;

    public SingleThreadCache(int size, Order order) {
        this.size = size;
        this.cache = new HashMap<Object, Object>(size);
        this.order = order;
    }

    public Object get(Object key) {
        return cache.get(key);
    }

    public void put(Object key, Object value) {
        if (cache.containsKey(key)) {
            updateElement(key, value);
        } else {
            addNewElement(key, value);
        }
    }

    private void updateElement(Object key, Object value) {
        cache.put(key, value);
        order.remove(key);
        order.add(key);
    }

    private void addNewElement(Object key, Object value) {
        if (cache.size() == size) {
            Object pollKey = order.poll();
            cache.remove(pollKey);
        }
        order.add(key);
        cache.put(key, value);
    }
}

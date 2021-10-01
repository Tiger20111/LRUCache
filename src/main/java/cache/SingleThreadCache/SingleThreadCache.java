package cache.SingleThreadCache;

import cache.Cache;
import cache.order.Order;

import java.util.HashMap;
import java.util.Map;

public class SingleThreadCache implements Cache {
    private Map<Object, Object> cache;
    private Order order;
    private final int capacity;

    public SingleThreadCache(int capacity, Order order) {
        this.capacity = capacity;
        this.cache = new HashMap<Object, Object>(capacity);
        this.order = order;
    }

    @Override
    public Object get(Object key) {
        return cache.get(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
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
        if (cache.size() == capacity) {
            Object pollKey = order.poll();
            cache.remove(pollKey);
        }
        order.add(key);
        cache.put(key, value);
    }
}

package cache.MultiThreadCache;

import cache.Cache;
import cache.order.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MultiThreadCache implements Cache {
    private Map<Object, Object> cache;
    private Order order;
    private final int capacity;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    public MultiThreadCache(int capacity, Order order) {
        this.capacity = capacity;
        this.cache = new HashMap<Object, Object>(capacity);
        this.order = order;
    }

    @Override
    public Object get(Object key) {
        Object value;
        rwLock.readLock().lock();
        value = cache.get(key);
        rwLock.readLock().unlock();
        return value;
    }

    @Override
    public int size() {
        int size;
        rwLock.readLock().lock();
        size = cache.size();
        rwLock.readLock().unlock();
        return size;
    }

    @Override
    public void put(Object key, Object value) {
        rwLock.writeLock().lock();
        if (cache.containsKey(key)) {
            updateElement(key, value);
        } else {
            addNewElement(key, value);
        }
        rwLock.writeLock().unlock();
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

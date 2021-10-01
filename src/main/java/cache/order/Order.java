package cache.order;

public interface Order {
    void add(Object key);
    Object poll();
    void remove(Object key);
}

package cache.order;

import java.util.LinkedList;

public class LRUOrder implements Order{
    LinkedList<Object> queue;
    public LRUOrder() {
        queue = new LinkedList<>();
    }
    public void add(Object key) {
        queue.add(key);
    }

    public Object poll() {
        return queue.poll();
    }

    public void remove(Object key) {
        queue.remove(key);
    }
}

package cache;

import cache.SingleThreadCache.SingleThreadCache;
import cache.order.LRUOrder;
import cache.order.Order;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SingleThreadCacheTest {
    private Cache cache;
    private Order order;
    private int capacity;

    @Test
    public void singleThreadLRUCacheTest() {
        capacity = 5;
        order = new LRUOrder();
        cache = new SingleThreadCache(capacity, order);
        ArrayList<Integer> data = Data.getSingleThreadTestData();
        for (int i = 0; i < data.size(); i++) {
            Assert.assertEquals(cache.size(), Math.min(i, capacity));
            cache.put(i, data.get(i));
        }

        for (int i = data.size() - 1; i > data.size() - 1 - capacity; i --) {
            Assert.assertEquals((int) (Integer) cache.get(i), i);
        }

    }
}

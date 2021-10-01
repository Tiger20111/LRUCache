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
    private int size;

    @Test
    public void singleThreadLRUCacheTest() {
        size = 5;
        order = new LRUOrder();
        cache = new SingleThreadCache(size, order);
        ArrayList<Integer> data = Data.getSingleThreadTestData();
        for (int i = 0; i < data.size(); i++) {
            cache.put(i, data.get(i));
        }

        for (int i = data.size() - 1; i > data.size() - 1 - size; i --) {
            Assert.assertTrue((Integer)cache.get(i) == i);
        }
    }
}

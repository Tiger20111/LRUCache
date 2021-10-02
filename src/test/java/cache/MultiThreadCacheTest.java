package cache;

import cache.MultiThreadCache.MultiThreadCache;
import cache.SingleThreadCache.SingleThreadCache;
import cache.order.LRUOrder;
import cache.order.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadCacheTest {
    private Cache cache;
    private Order order;
    private int capacity;
    private int numThreads;

    @Test
    public void multiThreadLRUCache() {
        capacity = 5;
        order = new LRUOrder();
        cache = new MultiThreadCache(capacity, order);
        numThreads = 16;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        ArrayList<Integer> data = Data.getMultiThreadTestData();

        ArrayList<Future<?>> futures = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();

        int n = 100;
        int k = data.size() / n;
        for (int i = 0; i < n - 1; i ++) {
            nodes.add(new Node(new ArrayList<>(data.subList(i * k, (i + 1) * k)), cache));
        }
        if (k * n < data.size()) {
            nodes.add(new Node(new ArrayList<>(data.subList(k * n, data.size() - 1)), cache));
        }
        for (Node node:
                nodes) {
            futures.add(executorService.submit(node));
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals(cache.size(), capacity);
    }

    private static class Node implements Runnable{
        private Cache cache;
        ArrayList<Integer> data;
        Node(ArrayList<Integer> data, Cache cache) {
            this.data = data;
            this.cache = cache;
        }
        @Override
        public void run() {
            for (int i = 0; i < data.size(); i ++) {
                cache.put(i % 10, data.get(i));
                cache.get(i % 10); // Добавляем доп нагрузку чтения, просто по фану
            }
        }
    }
}

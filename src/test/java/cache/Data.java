package cache;

import java.util.ArrayList;

public class Data {
    public static ArrayList<Integer> getSingleThreadTestData() {
        ArrayList<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            data.add(i);
        }
        return data;
    }

    public static ArrayList<Integer> getMultiThreadTestData() {
        ArrayList<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < 1_000_000; i++) {
            data.add(i);
        }
        return data;
    }
}

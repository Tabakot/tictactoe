package ru.tab.tictactoe.util;

import java.util.*;

public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int num) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();

        int counter = 0;
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
            counter++;
            if (counter == num)
                return result;
        }

        return result;
    }
}

package testutils;

import java.util.AbstractList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TestUtils {
    public static List<Integer> range(final int begin, final int end) {
        return new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                return begin + index;
            }

            @Override
            public int size() {
                return end - begin;
            }
        };
    }
    
    public static Set<Integer> setRange(int begin, int end) {
        return new LinkedHashSet<Integer>(range(begin, end));
    }
    
    public static Set<Integer> setRange(int end) {
        return setRange(0, end);
    }
}

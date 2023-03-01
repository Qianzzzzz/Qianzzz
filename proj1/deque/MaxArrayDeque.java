package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 1; i < size(); i += 1) {
            //  compare() return 1 if x > y; 0 if equal; -1 if x < y;
            //  we want to get the larger and equal one within two items;
            //  thus we keep the result when method outputs 0 and 1;
            if (comparator.compare(get(i), maxItem) >= 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

    //  same as previous method, we take in a variable type is comparator and named it c;
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 1; i < size(); i += 1) {
            if (c.compare(get(i), maxItem) >= 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }
}

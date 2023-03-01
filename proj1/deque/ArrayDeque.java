package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private class ArrayDequeIterator implements Iterator<T> {

        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T nextItem = (T) get(wizPos);
            wizPos += 1;
            return nextItem;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }


    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private static final int FOUR = 4;
    private static final int EIGHT = 8;
    private static final int SIXTEEN = 16;

    public ArrayDeque() {
        items = (T[]) new Object[EIGHT];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private void resize(int capacity) {
        //   different cases array would have different situation of the start point of the order;
        T[] a = (T[]) new Object[capacity];
        //      Case 1: expend length, nextFirst locates on the left side of the nextLast;
        //      need larger capacity to contain more items;
        if (nextFirst < nextLast && capacity > items.length) {
            System.arraycopy(items, nextFirst + 1, a, 0, size - nextFirst - 1);
            System.arraycopy(items, 0, a, size - nextFirst - 1, nextFirst + 1);
        }
        //      Case 2: expend length, nextFirst locates on the right side of the nextLast;
        //      need larger capacity to contain more items;
        if (nextFirst > nextLast && capacity > items.length) {
            System.arraycopy(items, 0, a, 0, size);
        }
        //      Case 3: reduce length, nextFirst locates on the left side of the nextLast;
        //      need reduce capacity to improve perform;
        if (nextFirst < nextLast && capacity < items.length) {
            System.arraycopy(items, nextFirst + 1, a, 0, size);
        }
        //      Case 4: reduce length, nextFirst locates on the right side of the nextLast;
        //      need reduce capacity to improve perform;
        if (nextFirst > nextLast && capacity < items.length) {
            System.arraycopy(items, 0, a, 0, size);
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T x) {
        //     if size full, increase items.length; need think about special case when nextFirst at endpoint;
        //     nextFirst pointer would point to the other side endpoint;
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        size += 1;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    @Override
    public void addLast(T x) {
        //     idea same as addFirst;
        //     opposite situation, nextLast points to index 0 when call method at the end of list.
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        size += 1;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
    }

    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            System.out.println();
        }
        if ((items.length - nextFirst) > size) {
            for (int i = nextFirst + 1; i < nextFirst + 1 + size; i += 1) {
                System.out.print(items[i] + " ");
            }
        } else {
            for (int j = nextFirst + 1; j < items.length; j += 1) {
                System.out.print(items[j] + " ");
            }
            for (int k = 0; k < size - items.length + nextFirst + 1; k += 1) {
                System.out.print(items[k] + " ");
            }
        }
    }

    @Override
    public T removeFirst() {
        //     first thing first, determine whether resize is needed before remove;
        //     Similar to add methods, the change of nextFirst pointer share the same idea.
        T itm = null;
        if ((size - 1 < items.length / FOUR) && size >= SIXTEEN) {
            resize(items.length / FOUR);
        }
        if (size == 0) {
            return null;
        }
        if (nextFirst == items.length - 1) {
            nextFirst = -1;
        }
        itm = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst += 1;
        size -= 1;
        return itm;
    }

    @Override
    public T removeLast() {
        //     Same as addFirst, determine whether resize is needed before remove;
        T itm = null;
        if ((size - 1 < items.length / FOUR) && size >= SIXTEEN) {
            resize(items.length / FOUR);
        }
        if (size == 0) {
            return null;
        }
        if (nextLast == 0) {
            nextLast = items.length;
        }
        itm = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast -= 1;
        size -= 1;
        return itm;
    }

    @Override
    public T get(int index) {
        int itm = nextFirst;
        if (index > items.length - 1) {
            return null;
        } else {
            if (itm + index >= items.length - 1) {
                itm = index + itm - items.length + 1;
                return items[itm];
            } else {
                return items[itm + index + 1];
            }
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            if (size() == ((Deque<T>) o).size()) {
                for (int i = 0; i < size; i += 1) {
                    if (!get(i).equals(((Deque<T>) o).get(i))) {
                        return false;
                    }
                    return true;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}


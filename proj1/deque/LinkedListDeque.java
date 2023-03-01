package deque;

//create a linked list

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class LinkedListDequeIterator implements Iterator<T> {

        private int wizPos;

        public LinkedListDequeIterator() {
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
        return new LinkedListDequeIterator();
    }

    // Linked list Node.
    private class TNode {
        private T item;
        private TNode next;
        private TNode prev;

        public TNode(TNode i, T j, TNode k) {
            prev = i;
            item = j;
            next = k;
        }

    }

    private TNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        if (size == 0) {
            sentinel.next = new TNode(sentinel, x, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            sentinel.next = new TNode(sentinel, x, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
        }
        size = size + 1;
    }

    @Override
    public void addLast(T x) {
        if (size == 0) {
            sentinel.prev = new TNode(sentinel, x, sentinel);
            sentinel.next = sentinel.prev;
        } else {
            sentinel.prev = new TNode(sentinel.prev, x, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
        }
        size = size + 1;
    }

    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            System.out.println();
        } else {
            while (size > 0) {
                System.out.print(sentinel.next.item);
                sentinel.next = sentinel.next.next;
                size -= 1;
            }
            System.out.println();
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0 || sentinel.next.item == null) {
            return null;
        }
        TNode first = sentinel.next;
        if (size == 1) {
            sentinel.next = null;
            sentinel.prev = null;
        } else {
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
        }
        size -= 1;
        return first.item;
    }

    @Override
    public T removeLast() {
        if (size == 0 || sentinel.prev.item == null) {
            return null;
        }
        TNode first = sentinel.prev;
        if (size == 1) {
            sentinel.next = null;
            sentinel.prev = null;
        } else {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
        }
        size -= 1;
        return first.item;
    }

    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        } else {
            TNode p = sentinel;
            while (index >= 0) {
                p = p.next;
                index -= 1;
            }
            return p.item;
        }
    }

    //  use helper function to implement recursive.
    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        } else {
            return getRecursiveHelper(index, sentinel.next);
        }
    }

    private T getRecursiveHelper(int index, TNode getItem) {
        if (index == 0) {
            return getItem.item;
        }
        while (index > 0) {
            getItem = getItem.next;
            return getRecursiveHelper(index - 1, getItem);
        }
        return getItem.item;
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

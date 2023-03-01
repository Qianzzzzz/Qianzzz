package deque;

import org.junit.Test;

import java.util.Iterator;


public class ArrayDequeTest {
    @Test
    public void addTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addFirst(17);
        ad1.addFirst(16);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);
        ad1.addFirst(15);

//        ad1.addFirst(14);
//        ad1.addFirst("13");
//        ad1.addFirst("12");
//        ad1.addFirst("11");
//        ad1.addFirst("10");
//        ad1.addFirst("9");
//        ad1.addFirst("8");
//        ad1.addFirst("7");
//        ad1.addFirst("6");
//        ad1.addFirst("5");
//        ad1.addFirst("4");
//        ad1.addFirst("3");
//        ad1.addFirst("2");
//        ad1.addFirst("1");
//        ad1.addFirst("0");

        for (int i : ad1) {
            System.out.println(i);
        }


//
//        System.out.print(ad1.removeFirst());
//        ad1.printDeque();
//        System.out.println(ad1.size());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
////        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.removeLast());
//        System.out.println(ad1.get(0));
//        System.out.println(ad1.get(1));
//        System.out.println(ad1.get(2));
//        System.out.println(ad1.get(3));
//        System.out.println(ad1.get(4));
//        System.out.println(ad1.get(5));
//        System.out.println(ad1.get(6));
//        System.out.println(ad1.get(7));
//        System.out.println(ad1.get(8));
//        System.out.println(ad1.get(9));
//        System.out.println(ad1.get(10));
//        System.out.println(ad1.get(11));


    }
//    @Test
//    public void IteratorTest() {
//        Iterator<T> seer = ArrayDeque.iterator();
//
//        while (seer.hasNext()) {
//            System.out.println(seer.next());
//        }
//    }

}

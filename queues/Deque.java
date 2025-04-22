import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null to the queue");
        if (size == 0) {
            first = new Node();
            first.item = item;
        } 
        else if (size == 1) {
            Node newFirst = new Node();
            Node newLast = first;
            first = newFirst;
            first.item = item;
            first.next = newLast;
            newLast.previous = first;
            last = newLast;
        }
        else {
            Node newFirst = new Node();
            first.previous = newFirst;
            Node oldFirst = first;
            newFirst.next = oldFirst;
            newFirst.item = item;
            first = newFirst;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null to the queue");
        if (size == 0) {
            Node newFirst = new Node();
            newFirst.item = item;
            first = newFirst;
        } 
        else if (size == 1) {
            Node newLast = new Node();
            first.next = newLast;
            newLast.previous = first;
            newLast.item = item;
            last = newLast;
        }
        else {
            Node newLast = new Node();
            Node oldLast = last;
            newLast.item = item;
            oldLast.next = newLast;
            newLast.previous = oldLast;
            last = newLast;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call removeFirst() on an empty queue");
        Item item = first.item;
        first = first.next;
        if (size > 1) first.previous = null;
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call removeLast() on an empty queue");
        Item item;
        if (size == 1)
            item = removeFirst();
        else {
            item = last.item;
            last = last.previous;
            last.next = null;
            size--;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        public boolean hasNext() {
            return first != null;
        }
        public void remove() {
            throw new UnsupportedOperationException("method remove is not implemented on Deque");
        }
        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException("Cannot call next() on an exhausted Dequeue iterator");
            Item item = first.item;
            first = first.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        String first = "My";
        String last = "Expected";
        deque.addFirst("As");
        deque.addFirst("Works");
        deque.addFirst("Deque");
        deque.addFirst(first);
        deque.addLast(last);
        String newLast = deque.removeLast();
        deque.addLast(newLast);
        String newFirst = deque.removeFirst();
        deque.addFirst(newFirst);

        for (String s: deque) {
            StdOut.print(s);
            StdOut.print("\n");
        }
        StdOut.printf("Deque size = %1$d\n", deque.size());
        StdOut.printf("Is deque empty? %1$s\n", deque.isEmpty());
        
        StdOut.println("Testing the adding one element and calling removeFirst() or removeLast() works");

        Deque<String> deque2 = new Deque<String>();
        deque2.addFirst("first");
        StdOut.println(deque2.removeFirst());
        deque2.addFirst("first");
        StdOut.println(deque2.removeLast());
        deque2.addLast("Last");
        StdOut.println(deque2.removeFirst());
        deque2.addLast("Last");
        StdOut.println(deque2.removeLast());

        StdOut.println("Check that two addFirst() calls followed by a removeFirst() works");
        Deque<String> deque3 = new Deque<String>();
        deque3.addFirst("one");
        deque3.addFirst("two");
        StdOut.println(deque3.removeFirst());
    }
}

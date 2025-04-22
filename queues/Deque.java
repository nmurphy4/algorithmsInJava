import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque < Item > implements Iterable < Item > {

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
            last = first;
        } else {
            Node newFirst = new Node();
            Node oldFirst = first;
            newFirst.next = oldFirst;
            newFirst.item = item;
            oldFirst.previous = newFirst;
            first = newFirst;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null to the queue");
        if (size == 0) {
            last = new Node();
            last.item = item;
            first = last;
        } else {
            Node newLast = new Node();
            Node oldLast = last;
            newLast.item = item;
            last = newLast;
            oldLast.next = newLast;
            newLast.previous = oldLast;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call removeFirst on an empty queue");
        Item item = first.item;
        first = first.next;
        first.previous = null;
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call removeLast on an empty queue");
        Item item = last.item;
        last = last.previous;
        last.next = null;
        size--;
        return item;
    }

    public Iterator < Item > iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator < Item > {
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
        Deque < String > deque = new Deque < String > ();
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
    }
}

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    private Node first;
    private Node last;
    private int size;
    private Node[] internalArray = (Node[]) new Object[1];

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = internalArray[i];
        }
        internalArray = temp;
    }
    public RandomizedQueue() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null to the queue");

        Node newNode = new Node();
        newNode.item = item;

        if (size == 0) {
            first = newNode;
            last = first;
        }
        else {
            if (size == internalArray.length) resize(2 * a.length);
            oldFirst = first;
            first = newNode;    
            newNode.next = oldFirst;
            oldFirst.previous = first;
        }

        internalArray[size] = first;
        size++;
    }

    private Node getRandomElement() {
        int randomIndex = StdRandom.uniqueInt(internalArray.length);
        Node randomlyAccessedNode = internalArray[randomIndex];
        if (randomlyAccessedNode == null) return getRandomElement()
        else 
            return randomlyAccessedNode;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call dequeue on an empty RandomizedQueue");
        Item item;
        if (size > 0 && size == internalArray.length / 4) resize(internalArray.length / 2);
        Node randomlyAccessedNode = getRandomElement();
        else {
            item = randomlyAccessedNode.item;
            internalArray[randomIndex] = null;
            Node previous = randomlyAccessedNode.previous;
            Node next = randomlyAccessedNode.next;
            previous.next = next;
            next.previous = previous;
        }
        size--;
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call removeFirst on an empty queue");
    }

    public Iterator < Item > iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator < Item > {
        public boolean hasNext() 
        public void remove() {
            throw new UnsupportedOperationException("method remove() is not implemented on RandomizedQueue");
        }
        public Item next() {
            if (first.next == null)
                throw NoSuchElementException("Cannot call next() on an exhausted RandomizedQueue")
        }
    }

    public static void main(String[] args) {

        for (String s : deque)  {
            StdOut.print(s);
            StdOut.print("\n");
        }
        StdOut.printf("Deque size = %1$d\n", deque.size());
        StdOut.printf("Is deque empty? %1$s\n", deque.isEmpty());
    }
}

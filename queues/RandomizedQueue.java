import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] internalArray = (Item[]) new Object[1];

    
    public RandomizedQueue() {
        size = 0;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = internalArray[i];
        }
        internalArray = temp;
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

        if (size == internalArray.length) resize(2 * internalArray.length);
        internalArray[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot call dequeue on an empty RandomizedQueue");
        Item item;
        if (size == 1) {
            item = internalArray[0];
            internalArray[0] = null;
        } else {
            if (size == internalArray.length / 4) resize(internalArray.length / 2);
            int randomIndex = StdRandom.uniformInt(size);
            item = internalArray[randomIndex];
            internalArray[randomIndex] = internalArray[size - 1];
            internalArray[size - 1] = null;
        }
        size--;
        return item;
    }

    public Item sample() {
        int randomIndex = StdRandom.uniformInt(size);
        return internalArray[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int copySize;
        private Item[] copyArray;

        public RandomIterator() {
            copySize = size;
            copyArray = (Item[]) new Object[copySize];
            for (int i = 0; i < size; i++)
                copyArray[i] = internalArray[i];
        }
        public boolean hasNext() {
            return copySize > 0;
        }
        public void remove() {
            throw new UnsupportedOperationException("method remove() is not implemented on RandomizedQueue");
        }
        public Item next() {
            if (copySize == 0)
                throw new NoSuchElementException("Cannot call next() on an exhausted RandomizedQueue");
            Item item;
            if (copySize == 1) {
                item = copyArray[0];
                copyArray[0] = null;
            } else {
                int randomIndex = StdRandom.uniformInt(copySize);
                item = copyArray[randomIndex];
                copyArray[randomIndex] = copyArray[copySize - 1];
            }
            copySize--;
            return item;
        }
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> queue1 = new RandomizedQueue<Integer>();
        RandomizedQueue<Integer> queue2 = new RandomizedQueue<Integer>();
        for (int i = 0; i <= 10; i++) {
            queue1.enqueue(i);
            queue2.enqueue(i);
        }
        StdOut.printf("RandomizedQueue size = %1$d\n", queue1.size());
        StdOut.printf("Is dequeue empty? %1$s\n", queue2.isEmpty());

        StdOut.println("Checking that dequeue is random");
        StdOut.println(queue1.dequeue());
        StdOut.println(queue2.dequeue());

        StdOut.println("checking that two different iterators iterate in indepently random order");
        for (int s: queue1) {
            StdOut.print(s);
            StdOut.print(" ");
        }
        StdOut.print("\n");
        for (int s: queue1) {
            StdOut.print(s);
            StdOut.print(" ");
        }
        StdOut.print("\n");
    }
}

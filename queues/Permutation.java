import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
        String nextString;
        while (!StdIn.isEmpty()) {
            nextString = StdIn.readString();
            randomQueue.enqueue(nextString);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(randomQueue.dequeue());
        }
    }
}

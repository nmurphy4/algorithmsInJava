import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String [] args) {
        String nextString;
        String champion = "";
        int element = 1;
        while (!StdIn.isEmpty()) {
            nextString = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / element)) {
                champion = nextString;
            }
            element++;     
        }        
        if (champion.isEmpty()) {
            StdOut.println("No Words Entered!");
        }
        else {
            StdOut.println(champion);
        }
    }
}

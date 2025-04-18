import edu.princeton.cs.algs4.StdOut;

public class Test {
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        p.open(1,1);
        System.out.println(p.percolates());
        p.open(2,1);
        System.out.println(p.percolates());
    }
}

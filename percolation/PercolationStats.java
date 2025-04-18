import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private double[] trialResults;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) 
            throw new IllegalArgumentException("Dimensions of grid must be strictly positive");
        if (trials <= 0) 
            throw new IllegalArgumentException("Number of trials must be strictly positive");
        double percolationThreshold = 0;
        trialResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            trialResults[i] = performExperiment(n);
        }
    }

    private void openRandom(int upperBoundExclusive, Percolation p) {
        int randRow = StdRandom.uniformInt(upperBoundExclusive);
        int randCol = StdRandom.uniformInt(upperBoundExclusive);
        p.open(randRow + 1, randCol + 1);
    }

    private double performExperiment(int n) {
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < n; i++)
            openRandom(n, percolation);

        while (!percolation.percolates())
            openRandom(n, percolation);

        double percolationThreshold = (double) percolation.numberOfOpenSites() / (n * n);
        return percolationThreshold;

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trialResults.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trialResults.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int dimension = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(dimension, trials);
        StdOut.printf("mean                     = %.20f\n", percolationStats.mean());
        StdOut.printf("stddev                   = %.20f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%1$.20g, %2$.20g]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }

}

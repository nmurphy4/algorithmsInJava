import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] trialResults;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials < 2)
            throw new IllegalArgumentException(String.format("You must run at least two trials to create a PercolationStats object, trials = %1$s", trials));

        double percolationThreshold = 0;
        trialResults = new double[trials];
        for (int i = 0; i < trials; i++)
            trialResults[i] = performExperiment(n);
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
        
        double percolationThreshold = (double)percolation.numberOfOpenSites() / (n*n);
        return percolationThreshold;
        
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0.0; // initialize sum
        for (int i = 0; i < trialResults.length; i++)
            sum += trialResults[i];
        return sum / trialResults.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0.0;
        double diff;
        for (int i = 0; i < trialResults.length; i++) {
            diff = trialResults[i] - mean();
            sum += diff * diff;
        }

        return sum / (trialResults.length - 1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trialResults.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trialResults.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int dimension = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(dimension, trials);
        StdOut.printf("mean                    = %.20f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %.20f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%1$.20g, %2$.20g]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }

}

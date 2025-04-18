import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private int gridDimension;
    private WeightedQuickUnionUF currentConnectionsWithSink;
    private WeightedQuickUnionUF currentConnectionsNoSink;
    private int openSites = 0;
    private final static int TOP = 0;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("Invalid argument %1$s passed to constructor!, The percolation grid must have a strictly positive size", n));
        }
        gridDimension = n;
        grid = new boolean[gridDimension][gridDimension];
        for (int i = 0; i < gridDimension; i++) {
            for (int j = 0; j < gridDimension; j++) {
                grid[i][j] = false;
            }
        }
        currentConnectionsWithSink = new WeightedQuickUnionUF(gridDimension * gridDimension + 2);
        currentConnectionsNoSink = new WeightedQuickUnionUF(gridDimension * gridDimension + 1);
        bottom = gridDimension * gridDimension + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkDimension(row);
        checkDimension(col);
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;
            int uniqueId = mapIndicesToUniqueId(row, col);
            if (row == 1) {
                currentConnectionsWithSink.union(uniqueId, TOP);
                currentConnectionsNoSink.union(uniqueId, TOP);
            }
            if (row == gridDimension)
                currentConnectionsWithSink.union(uniqueId, bottom);
            if (col > 1 && isOpen(row, col - 1)) {
                currentConnectionsWithSink.union(uniqueId, mapIndicesToUniqueId(row, col - 1));
                currentConnectionsNoSink.union(uniqueId, mapIndicesToUniqueId(row, col - 1));
            }
            if (row > 1 && isOpen(row - 1, col)) {
                currentConnectionsWithSink.union(uniqueId, mapIndicesToUniqueId(row - 1, col));
                currentConnectionsNoSink.union(uniqueId, mapIndicesToUniqueId(row - 1, col));
            }
            if (row < gridDimension && isOpen(row + 1, col)) {
                currentConnectionsWithSink.union(uniqueId, mapIndicesToUniqueId(row + 1, col));
                currentConnectionsNoSink.union(uniqueId, mapIndicesToUniqueId(row + 1, col));
            }
            if (col < gridDimension && isOpen(row, col + 1)) {
                currentConnectionsWithSink.union(uniqueId, mapIndicesToUniqueId(row, col + 1));
                currentConnectionsNoSink.union(uniqueId, mapIndicesToUniqueId(row, col + 1));
            }
        }
    }

    private int mapIndicesToUniqueId(int row, int col) {
        checkDimension(row);
        checkDimension(col);
        return gridDimension * (row - 1) + col;
    }
    private void checkDimension(int dim) {
        if (dim <= 0 || dim > gridDimension)
            throw new IllegalArgumentException(String.format("Invalid dimension %1$s!, Grid dimensions must be positive and <= %2$s", dim, gridDimension));
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkDimension(row);
        checkDimension(col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return currentConnectionsNoSink.find(TOP) == currentConnectionsNoSink.find(mapIndicesToUniqueId(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return currentConnectionsWithSink.find(TOP) == currentConnectionsWithSink.find(bottom);
    }
}

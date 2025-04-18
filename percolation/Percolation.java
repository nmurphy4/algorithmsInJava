import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private boolean[][] grid;
    private int gridDimension;
    private WeightedQuickUnionUF currentConnections;
    private int openSites = 0;

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
        currentConnections = new WeightedQuickUnionUF(gridDimension * gridDimension);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkDimension(row);
        checkDimension(col);
        if (!grid[row - 1][col - 1]) { 
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        int uniqueId = mapIndicesToUniqueId(row, col);
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            currentConnections.union(uniqueId, mapIndicesToUniqueId(row, col - 1));
        }
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            currentConnections.union(uniqueId, mapIndicesToUniqueId(row - 1 , col));
        }     
        if (row + 1 <= gridDimension && isOpen(row + 1, col)) {
            currentConnections.union(uniqueId, mapIndicesToUniqueId(row + 1 , col));
        }     
        if (col + 1 <= gridDimension && isOpen(row, col + 1)) {
            currentConnections.union(uniqueId, mapIndicesToUniqueId(row, col + 1));
        }     
    }

    private int mapIndicesToUniqueId(int row, int col) {
        checkDimension(row);
        checkDimension(col);
        return gridDimension * (row - 1) + (col - 1);
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
        int index = mapIndicesToUniqueId(row, col);
        boolean status = false;
        int j = 1;
        while (j <= gridDimension) {
            status = currentConnections.find(mapIndicesToUniqueId(1, j)) == currentConnections.find(index);
            j++;
            if (status)
                break;
        }
        return status;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites; 
    }

    // does the system percolate?
    public boolean percolates() {
        boolean p = false;
        int i = 1;
        while (!p && i <= gridDimension) {
            p = isFull(gridDimension, i);
            i++;
        }
        return p;
    }
}

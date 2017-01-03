package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF waterConnect;
    private int[] arrayClosed;
    private int n;
    private int openSites;
    private boolean percolates;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        waterConnect = new WeightedQuickUnionUF(N*N );
        n = N;
        arrayClosed = new int[n*n];
        openSites = 0;
        for (int i = 0; i < n - 1; i++) {
            waterConnect.union(i, i + 1);
        }
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int square = row * n + col;
            arrayClosed[square] = 1;
            openSites += 1;

            if (isOpen(row - 1, col)) {
                int squareAbove = (row - 1) * n + col;
                waterConnect.union(square, squareAbove);
            }
            if (isOpen(row + 1, col)) {
                int squareDown = (row + 1) * n + col;
                waterConnect.union(square, squareDown);
            }
            if (isOpen(row, col + 1)) {
                int squareLeft = row * n + col + 1;
                waterConnect.union(square, squareLeft);
            }
            if (isOpen(row, col - 1)) {
                int squareRight = row * n + col - 1;
                waterConnect.union(square, squareRight);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            return false;
        }
        int square = row * n + col;
        return arrayClosed[square] == 1;

    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int square = row * n + col;
        if (!isOpen(row, col)) {
            return false;
        }
        if (waterConnect.connected(0, square)) {
            if(row == n - 1) percolates = true;
            return true;
        }

        return false;


    }
    // number of open sites
    public int numberOfOpenSites() {
        return openSites;

    }
    // does the system percolate?
    public boolean percolates() {
        return percolates;

    }
    // unit testing (not required)

    public static void main(String[] args) {

    }

}                       

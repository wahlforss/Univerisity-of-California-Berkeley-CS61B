package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

import java.util.LinkedList;

public class PercolationStats {
    private double[] fractions;
    private int T;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)  {
        this.T = T;
        fractions = new double[T];
        for (int i = 0; i < T; i ++) {
            Percolation test = new Percolation(N);
            while (!test.percolates()) {
                int row = StdRandom.uniform(0,N);
                int col = StdRandom.uniform(0,N);
                test.open(row, col);
                test.isFull(row, col);
            }
            fractions[i] = ((double) test.numberOfOpenSites() / (N*N));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(fractions);
    }


    // low  endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(1000, 30);
        System.out.println(test.confidenceLow());
        System.out.println(test.confidenceHigh());
    }

}                       

package lab1;

/**
 * Created by wcf on 15-9-19.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int pN;
    private int times;
    private double[] record;

    // perform T independent experiments on an N-by-N gird
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        pN = N;
        times = T;
        record = new double[T];

        result();
    }

    //every time the percolation's result
    private double once() {
        int count = 0;
        int rol, col;
        Percolation pField = new Percolation(pN);
        while (true) {
            if (count > 0 && pField.percolates()) {
                break;
            }
            rol = StdRandom.uniform(1, pN + 1); col = StdRandom.uniform(1, pN + 1);
            if (!pField.isOpen(rol, col)) {
                pField.open(rol, col);
                count++;
            }
        }
        return count * 1.0 / (pN * pN);
    }

    //the record of the simulation
    private void result() {
       for (int i = 0; i < times; i++) {
          record[i] = once();
       }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(record);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(record);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats sample = new PercolationStats(N, T);
        StdOut.println("mean                    = " + sample.mean());
        StdOut.println("stddev                  = " + sample.stddev());
        StdOut.println("95% confidence interval = " + sample.confidenceLo() + ", "
                            + sample.confidenceHi());

    }
}

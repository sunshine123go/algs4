package lab1;

/**
 * Created by wcf on 15-9-19.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private boolean[][] gird;
    private int pN;
    private WeightedQuickUnionUF uf;
    // create N-by-N gird, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        gird = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                gird[i][j] = false;
            }
        pN = N;
    }

    //calculate the number of xyToID
    private int xyToID(int i, int j) {
        return (i - 1) * pN + j;
    }

    //open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (i <= 0 || i > pN || j <= 0 || j > pN) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(i, j))
            return;

        gird[i - 1][j - 1] = true;
        if (i == 1) {
            uf.union(0, xyToID(i, j));
        }
        if (i == pN) {
            uf.union(pN * pN + 1, xyToID(i, j));
        }
        if (i - 1 >= 1 && isOpen(i - 1, j)) {
            uf.union(xyToID(i, j), xyToID(i - 1, j));
        }
        if (j - 1 >= 1 && isOpen(i, j - 1)) {
            uf.union(xyToID(i, j), xyToID(i, j - 1));
        }
        if (i + 1 <= pN && isOpen(i + 1, j)) {
            uf.union(xyToID(i, j), xyToID(i + 1, j));
        }
        if (j + 1 <= pN && isOpen(i, j + 1)) {
            uf.union(xyToID(i, j), xyToID(i, j + 1));
        }
    }

    //is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return gird[i - 1][j - 1];
    }

    //is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > pN || j <= 0 || j > pN) {
            throw new IndexOutOfBoundsException();
        }

        if ( isOpen(i, j) && uf.connected(0, xyToID(i, j))) {
                return true;
        }
        return false;
    }

    // does the system perlate?
    public boolean percolates() {
        if (uf.connected(0, pN * pN + 1)) {
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Percolation pField = new Percolation(N);
        int count = 0;
        int rol, col;
        while (true) {
            if (count > 0 && pField.percolates()) {
                break;
            }
            rol = StdRandom.uniform(1, N + 1);
            col = StdRandom.uniform(1, N + 1);
            if (!pField.isOpen(rol, col)) {
                pField.open(rol, col);
                count++;
            }
        }
        StdOut.println(count * 1.0 / (N * N));
//        int N = 4;
//        Percolation pField = new Percolation(N);
//        StdOut.println(pField.isFull(1,1));
    }
}

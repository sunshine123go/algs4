package lab1;

/**
 * Created by wcf on 15-9-19.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private char[][] gird;
    private boolean percolation;
    private int pN;
    private WeightedQuickUnionUF uf;
    // create N-by-N gird, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        pN = N;
        percolation = false;
        uf = new WeightedQuickUnionUF(N * N);
        gird = new char[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                gird[i][j] = 0;
            }
    }

    // calculate the number of xyToID
    private int xyToID(int i, int j) {
        return (i - 1) * pN + j - 1;
    }

    // open site (row i, column j) if it is not open already
    // every time open a site
    // mark the site's root that the site have connect
    public void open(int i, int j) {
        if (i <= 0 || i > pN || j <= 0 || j > pN) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(i, j))
            return;
        int mask = 0;
        int p, q;
        gird[i - 1][j - 1] = 1;
        if (i == 1) {
            gird[i - 1][j - 1] |= 1 << 1;
        }
        if (i == pN) {
            gird[i - 1][j - 1] |= 1 << 2;
        }
        mask |= gird[i - 1][j - 1];
        if (i - 1 >= 1 && isOpen(i - 1, j)) {
            p = uf.find(xyToID(i - 1, j));
            q = p % pN;
            p /= pN;
            mask |= gird[p][q];
            gird[p][q] |= mask;
            uf.union(xyToID(i, j), xyToID(i - 1, j));
        }
        if (j - 1 >= 1 && isOpen(i, j - 1)) {
            p = uf.find(xyToID(i, j - 1));
            q = p % pN;
            p /= pN;
            mask |= gird[p][q];
            gird[p][q] |= mask;
            uf.union(xyToID(i, j), xyToID(i, j - 1));
        }
        if (i + 1 <= pN && isOpen(i + 1, j)) {
            p = uf.find(xyToID(i + 1, j));
            q = p % pN;
            p /= pN;
            mask |= gird[p][q];
            gird[p][q] |= mask;
            uf.union(xyToID(i, j), xyToID(i + 1, j));
        }
        if (j + 1 <= pN && isOpen(i, j + 1)) {
            p = uf.find(xyToID(i, j + 1));
            q = p % pN;
            p /= pN;
            mask |= gird[p][q];
            gird[p][q] |= mask;
            uf.union(xyToID(i, j), xyToID(i, j + 1));
        }
        p = uf.find(xyToID(i, j));
        q = p % pN;
        p /= pN;
        mask |= gird[p][q];
        gird[p][q] |= mask;

        if (gird[p][q] == 7) {
            percolation = true;
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return gird[i - 1][j - 1] > 0;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > pN || j <= 0 || j > pN) {
            throw new IndexOutOfBoundsException();
        }
        int p, q;
        p = uf.find(xyToID(i, j));
        q = p % pN;
        p /= pN;

        return (gird[p][q] & 0x2) == 0x2;
    }

    // does the system perlate?
    public boolean percolates() {
        return percolation;
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

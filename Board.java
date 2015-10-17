package lab4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * Created by wcf on 15-10-15.
 */
public class Board {
    private int[][] blocks;
    private char N;
    private char zi, zj;
    private int manhan = 0;
    private int ham = 0;

    public Board(int[][] blocks) {
        this.N = (char) blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < (int) N; i++) {
            for (int j = 0; j < (int) N; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zi = (char) i;
                    zj = (char) j;
                    continue;
                } else {
                    manhan += Math.abs((blocks[i][j] - 1) / N - i) +
                            Math.abs((blocks[i][j] - 1) % N - j);
                }

                if (blocks[i][j] != (i * N + j + 1)) {
                    ham++;
                }
            }
        }
    }          // construct a board from an N-by-N array of blocks

    // (where blocks[i][j] = block in row i, column j)
    private void swap(int[][] a, int a1, int b1, int a2, int b2) {
        int temp;
        temp = a[a1][b1];
        a[a1][b1] = a[a2][b2];
        a[a2][b2] = temp;
    }
    public int dimension() {
        return (int) N;
    }                // board dimension N
    public int hamming() {
        return ham;
    }                  // number of blocks out of place
    public int manhattan() {
        return manhan;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        return manhan == 0;
    }               // is this board the goal board?
    public Board twin() {
        int a1 = 0, a2 = 0, b1 = 0, b2 = 0;
        int flag = 1;
        for (int i = 0; i < (int) N; i++) {
            if (flag == 3) break;
            for (int j = 0; j < (int) N; j++) {
                if (blocks[i][j] != 0 && flag == 1) {
                    a1 = i;
                    b1 = j;
                    flag = 2;
                    continue;
                }
                if (blocks[i][j] != 0 && flag == 2) {
                    a2 = i;
                    b2 = j;
                    flag = 3;
                    break;
                }
            }
        }
        swap(blocks, a1, b1, a2, b2);
        Board tw = new Board(blocks);
        swap(blocks, a1, b1, a2, b2);
        return tw;
    }                   // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.blocks == that.blocks) {
            return true;
        }
        if (this.dimension() != that.dimension()) {
            return false;
        }
        for (int i = 0; i < (int) N; i++)
            for (int j = 0; j < (int) N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        return true;
    }       // does this board equaly?
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbor = new ArrayList<Board>();
        if (zi - 1 >= 0) {
            swap(blocks, zi - 1, zj, zi, zj);
            neighbor.add(new Board(blocks));
            swap(blocks, zi - 1, zj, zi, zj);
        }
        if (zi + 1 < N) {
            swap(blocks, zi + 1, zj, zi, zj);
            neighbor.add(new Board(blocks));
            swap(blocks, zi + 1, zj, zi, zj);
        }
        if (zj - 1 >= 0) {
            swap(blocks, zi, zj - 1, zi, zj);
            neighbor.add(new Board(blocks));
            swap(blocks, zi, zj - 1, zi, zj);
        }
        if (zj + 1 < N) {
            swap(blocks, zi, zj + 1, zi, zj);
            neighbor.add(new Board(blocks));
            swap(blocks, zi, zj + 1, zi, zj);
        }

        return neighbor;
    }    // all neighboring boards
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append((int) N + "\n");
        for (int i = 0; i < (int) N; i++) {
            for (int j = 0; j < (int) N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // string representation of this board (in the output format specified below)

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board unit = new Board(blocks);

//        StdOut.println(unit.twin());
//
        StdOut.println(unit);
        StdOut.println("###" + unit.manhattan());
    }
}

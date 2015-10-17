package lab4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

/**
 * Created by wcf on 15-10-15.
 */
public class Solver {
    private BoardPriority current;
    private boolean isSolved;

    private class BoardPriority implements Comparable<BoardPriority> {
        private Board board;
        private int cost;
        private int priority;
        private BoardPriority cameFrom;
        public BoardPriority(Board boardA, int costA, BoardPriority came) {
            board = boardA;
            cost = costA;
            priority = cost + board.manhattan();
            cameFrom = came;
        }
        public int compareTo(BoardPriority that) {
            if (this.priority < that.priority) {
                return -1;
            }
            if (this.priority > that.priority) {
                return 1;
            }
            if (this.priority == that.priority) {
                if (this.board.manhattan() < that.board.manhattan()) {
                    return -1;
                }
            }
            return 1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<BoardPriority> PQ, PQ_t;

        PQ = new MinPQ<BoardPriority>();
        PQ_t = new MinPQ<BoardPriority>();
        PQ.insert(new BoardPriority(initial, 0, null));
        PQ_t.insert(new BoardPriority(initial.twin(), 0, null));

        BoardPriority current_t;
        while (!PQ.isEmpty() && !PQ_t.isEmpty()) {
            current = PQ.delMin();
            current_t = PQ_t.delMin();

            if (current.board.isGoal() || current_t.board.isGoal()) {
                break;
            }
            BoardPriority _prev;
            for (Board next : current.board.neighbors()) {
                int new_cost = current.cost + 1;
                _prev = current;
                boolean flag = false;
                for (int i = 0; i < 5; i++) {
                    if (_prev == null) {
                        break;
                    }
                    if (next.equals(_prev.board)) {
                        flag = true;
                        break;
                    } else {
                        _prev = _prev.cameFrom;
                    }
                }
                if (!flag) {
                    PQ.insert(new BoardPriority(next, new_cost, current));
                }
            }
            for (Board next : current_t.board.neighbors()) {
                int new_cost = current_t.cost + 1;
                _prev = current_t;
                boolean flag_t = false;
                for (int i = 0; i < 5; i++) {
                    if (_prev == null) {
                        break;
                    }
                    if (next.equals(current_t.board)) {
                        flag_t = true;
                    } else {
                        _prev = _prev.cameFrom;
                    }
                }
                if (!flag_t) {
                    PQ_t.insert(new BoardPriority(next, new_cost, current_t));
                }
            }
        }
        if (current.board.isGoal()) {
            isSolved = true;
        } else {
            isSolved = false;
        }


    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolved) {
            return -1;
        } else {
            return current.cost;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolved) {
            return null;
        }
        Stack<Board> tmp = new Stack<Board>();
        BoardPriority trace = current;
        while (trace.cameFrom != null) {
            tmp.push(trace.board);
            trace = trace.cameFrom;
        }
        tmp.push(trace.board);
        return tmp;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    // solve a slider puzzle (given below)
}

package lab4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

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
        private BoardPriority came_from;
        public BoardPriority(Board _board, int _cost, BoardPriority came) {
            board = _board;
            cost = _cost;
            priority = cost + board.manhattan();
            came_from = came;
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

    public Solver(Board initial) {
        MinPQ<BoardPriority> PQ, PQ_t;
//        ArrayList<Board> cost_so_far, cost_so_far_t;
//        ArrayList<Integer> cost_so_far_value, cost_so_far_value_t;

        PQ = new MinPQ<BoardPriority>();
        PQ_t = new MinPQ<BoardPriority>();
        PQ.insert(new BoardPriority(initial, 0, null));
        PQ_t.insert(new BoardPriority(initial.twin(), 0, null));

//        cost_so_far = new ArrayList<Board>();
//        cost_so_far_t = new ArrayList<>();
//        cost_so_far.add(initial);
//        cost_so_far_t.add(initial.twin());
//        cost_so_far_value = new ArrayList<Integer>();
//        cost_so_far_value_t = new ArrayList<>();
//        cost_so_far_value.add(0);
//        cost_so_far_value_t.add(0);

//        int priority = 0, priority_t = 0;
//        int aaa = 0; int bbb = 0;
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
                        _prev = _prev.came_from;
                    }
                }
                if ( !flag ) {
//                    cost_so_far.add(next);
//                    cost_so_far_value.add(new_cost);
//                    priority = new_cost + next.manhattan();
                    PQ.insert(new BoardPriority(next, new_cost, current));
                }
//                else if (new_cost < cost_so_far_value.get(cost_so_far.indexOf(next))) {
//                    cost_so_far_value.set(cost_so_far.indexOf(next), new_cost);
//                    priority = new_cost + next.manhattan();
//                    PQ.insert(new BoardPriority(next, priority, current));
//                }
            }
            StdOut.println();
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
                        _prev = _prev.came_from;
                    }
                }
                if ( !flag_t ) {
                    PQ_t.insert(new BoardPriority(next, new_cost, current_t));
                }
            }
        }
        if (current.board.isGoal()) {
            isSolved = true;
        } else {
            isSolved = false;
        }


    }          // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable() {
        if (isSolved) {
            return true;
        } else {
            return false;
        }
    }           // is the initial board solvable?
    public int moves() {
        if (!isSolved) {
            return -1;
        } else {
            return current.cost;
        }
    }                    // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        if (!isSolved) {
            return null;
        }
        ArrayList<Board> tmp = new ArrayList<Board>();
        BoardPriority trace = current;
        while (trace.came_from != null) {
            tmp.add(trace.board);
            trace = trace.came_from;
        }
        tmp.add(trace.board);
        Collections.reverse(tmp);
        return tmp;
    }     // sequence of boards in a shortest solution; null if unsolvable

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

package lab2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wcf on 15-9-29.
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> s;
        s = new RandomizedQueue<String>();
        int N = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            s.enqueue(item);
        }
        for (int i = 0; i < N; i++) {
            StdOut.println(s.dequeue());
        }
    }
}

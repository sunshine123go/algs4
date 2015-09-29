package lab2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by wcf on 15-9-29.
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> s;
        s = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        int n = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            n++;
            int i = StdRandom.uniform(n); //shuffle?
            if (i < k) {
                if (n > k) {
                    s.dequeue();
                }
                s.enqueue(item);
            }
        }
        for (String i : s) {
            StdOut.println(i);
        }
    }
}

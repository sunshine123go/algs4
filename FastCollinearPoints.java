package lab3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wcf on 15-10-5.
 */
public class FastCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> lineSegments;
    private LineSegment[] ls;
    private int linenumber;
    private int pN;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException("argument is null");
        }
        this.points = points.clone();
        this.pN = points.length;
        linenumber = 0;
        lineSegments = new ArrayList<LineSegment>();

        for (int ii = 0; ii < pN; ii++)
            for (int jj = ii; jj < pN; jj++) {
                if (this.points[jj] == null) {
                    throw new java.lang.
                            IllegalArgumentException("Illegal Arguments");
                }
                if (ii != jj && this.points[ii].compareTo(this.points[jj]) == 0) {
                    throw new java.lang.
                            IllegalArgumentException("Illegal Arguments");
                }
            }

        Point p;
        for (int i = 0; i < pN; i++) {
            p = points[i];
            Arrays.sort(this.points, p.slopeOrder());

            Point first = p;
            Point last = p;
            int count = 1;
            for (int j = 1; j < pN - 1; j++) {
                if (p.slopeTo(this.points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new java.lang.
                            IllegalArgumentException("Illegal arguments");
                }
                if (p.slopeTo(this.points[j + 1]) != p.slopeTo(this.points[j])) {
                    last = last.compareTo(this.points[j]) < 0 ?
                            this.points[j] : last;
                    first = first.compareTo(this.points[j]) > 0 ?
                            this.points[j] : first;
                    if (count >= 3) {
                        if (p.compareTo(first) == 0) {
                            lineSegments.add(new LineSegment(p, last));
                        }
                    }
                    first = p;
                    last = p;
                    count = 1;
                } else {
                    first = first.compareTo(this.points[j]) > 0 ?
                            this.points[j] : first;
                    last = last.compareTo(this.points[j]) < 0 ?
                            this.points[j] : last;
                    count++;
                    if (j + 1 == pN - 1 && count >= 3) {
                        last = last.compareTo(this.points[j+1]) < 0 ?
                                this.points[j+1] : last;
                        first = first.compareTo(this.points[j+1]) > 0 ?
                                this.points[j+1] : first;
                        if (p.compareTo(first) == 0) {
                            lineSegments.add(new LineSegment(p, last));
                        }
                        count = 1;
                        first = p;
                        last = p;
                    }
                }
            }
        }
        ls = lineSegments.toArray(new LineSegment[lineSegments.size()]);
        linenumber = ls.length;
    }

    public int numberOfSegments() {
        return linenumber;
    }

    public LineSegment[] segments() {
        return ls.clone();
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println(collinear.numberOfSegments());
    }
}

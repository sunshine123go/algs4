package lab3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * Created by wcf on 15-10-5.
 */
public class BruteCollinearPoints {
    private Point[] ipoints;
    private int pN;
    private ArrayList<LineSegment> lineSegments;
    private LineSegment[] ls;
    private int linenumber;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException("argument is null");
        }
        this.ipoints = points.clone();
        this.pN = points.length;
        lineSegments = new ArrayList<LineSegment>();
        linenumber = 0;

        for (int ii = 0; ii < pN; ii++)
            for (int jj = ii; jj < pN; jj++) {
                if (ipoints[jj] == null) {
                    throw new java.lang.
                            IllegalArgumentException("Illegal Arguments");
                }
                if (ii != jj && ipoints[ii].compareTo(ipoints[jj]) == 0) {
                    throw new java.lang.
                            IllegalArgumentException("Illegal Arguments");
                }
            }

        Point p, q, r, s;
        for (int i = 0; i < pN; i++)
            for (int j = i + 1; j < pN; j++)
                for (int k = j + 1; k < pN; k++)
                    for (int l = k + 1; l < pN; l++) {
                        p = ipoints[i];
                        q = ipoints[j];
                        r = ipoints[k];
                        s = ipoints[l];

                        if (p.slopeTo(q) == p.slopeTo(r)
                                && p.slopeTo(r) == p.slopeTo(s)) {
                            lineSegments.add(line(p, q, r, s));
                        }
                    }
        ls = lineSegments.toArray(new LineSegment[lineSegments.size()]);
        linenumber = ls.length;
    }

    private LineSegment line(Point p, Point q, Point r, Point s) {
        Point minp, maxp;
        minp = p.compareTo(q) < 0 ? p : q;
        minp = minp.compareTo(r) < 0 ? minp : r;
        minp = minp.compareTo(s) < 0 ? minp : s;

        maxp = p.compareTo(q) > 0 ? p : q;
        maxp = maxp.compareTo(r) > 0 ? maxp : r;
        maxp = maxp.compareTo(s) > 0 ? maxp : s;

        return new LineSegment(minp, maxp);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println(collinear.numberOfSegments());
    }
}

package lab3;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

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
        if(points == null) {
            throw new java.lang.NullPointerException("argument is null");
        }
        this.points = points;
        this.pN = points.length;
        linenumber = 0;
        lineSegments = new ArrayList<LineSegment>();

        Point p;
        for(int i = 0; i < pN; i++) {
            if(this.points[i] == null) {
                throw new java.lang.IllegalArgumentException("Illegal Arguments");
            }
            p = this.points[i];
            MergeX.sort(this.points);
            MergeX.sort(this.points, p.slopeOrder());
            Point first = null;
            Point last = null;
            int count = 1;
            for(int j = 1; j < pN - 1; j++) {
                if(p.slopeTo(this.points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new java.lang.IllegalArgumentException("Illegal arguments");
                }
                if(p.slopeTo(this.points[j + 1]) != p.slopeTo(this.points[j])) {
                    last = p.compareTo(this.points[j]) < 0 ? this.points[j] : p;
//                    last = this.points[j];
                    if(count >= 3 && p.compareTo(last) == 0) {
//                            if(p.compareTo(first) < 0) {
                                lineSegments.add(new LineSegment(first, last));
//                            }
                    }
                    first = null;
                    count = 1;
                } else {
                    if(first == null) {
                        first = p.compareTo(this.points[j]) > 0 ? this.points[j] : p;
//                        first = this.points[j];
                    }
                    count++;
                    if(j + 1 == pN - 1 && count >= 3) {
                        last = p.compareTo(this.points[j + 1]) < 0 ? this.points[j+1] : p;
//                        last = this.points[j+1];
                        if(p.compareTo(last) == 0) {
                            lineSegments.add(new LineSegment(first, last));
                        }
                        first = null;
                        count = 1;
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
        return ls;
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

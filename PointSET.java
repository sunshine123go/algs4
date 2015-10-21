package lab5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wcf on 15-10-20.
 */
public class PointSET {
    private SET<Point2D> points;
    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    // number of points in the set
    public int size() {
        return points.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }
    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D key : points) {
            StdDraw.point(key.x(), key.y());
        }
    }
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();
        for (Point2D key : points) {
            if (rect.contains(key)) {
                queue.enqueue(key);
            }
        }
        return queue;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D near = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D key : points) {
            if (p.distanceSquaredTo(key) < distance) {
                distance = p.distanceSquaredTo(key);
                near = key;
            }
        }
        return near;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET test = new PointSET();
        test.insert(new Point2D(.1, .2));
        test.insert(new Point2D(.1, .3));
        StdOut.println(test.nearest(new Point2D(.1, .2)));

    }
}

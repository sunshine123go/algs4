package lab5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

/**
 * Created by wcf on 15-10-21.
 */
public class KdTree {
    private Node root;
    private int N;
    private Point2D near;
    private double mindis;
    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;
        public Node(Point2D p, RectHV rect) {
            this.point = p;
            this.rect = rect;
        }
    }
    public KdTree() {
        this.N = 0;
    }
    public boolean isEmpty() {
        return this.N == 0;
    }
    public int size() {
        return this.N;
    }
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Illegal argument!");
        }
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insertX(root, p, rect);
        N++;
    }
    private Node insertX(Node x, Point2D p, RectHV rect) {
        if (x == null) {
            return new Node(p, rect);
        }
        double cmp = p.x() - x.point.x();
        RectHV rectL =
                new RectHV(rect.xmin(), rect.ymin(), x.point.x(), rect.ymax());
        RectHV rectR =
                new RectHV(x.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        if (cmp < 0) {
            x.lb = insertY(x.lb, p, rectL);
        } else if (cmp > 0) {
            x.rt = insertY(x.rt, p, rectR);
        } else if (!p.equals(x.point)) {
            x.rt = insertY(x.rt, p, rectR);
        } else {
            N--;
        }
        return x;
    }
    private Node insertY(Node x, Point2D p, RectHV rect) {
        if (x == null) {
            return new Node(p, rect);
        }
        double cmp = p.y() - x.point.y();
        RectHV rectB =
                new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.point.y());
        RectHV rectT =
                new RectHV(rect.xmin(), x.point.y(), rect.xmax(), rect.ymax());
        if (cmp < 0) {
            x.lb = insertX(x.lb, p, rectB);
        } else if (cmp > 0) {
            x.rt = insertX(x.rt, p, rectT);
        } else if (!p.equals(x.point)) {
            x.rt = insertX(x.rt, p, rectT);
        } else {
            N--;
        }
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Illegal argument!");
        }
        Node temp = containsX(root, p);
        if (temp != null) {
            return temp.point.equals(p);
        }
        return false;
    }
    private Node containsX(Node x, Point2D p) {
        if (x == null) {
            return null;
        }
        double cmp = p.x() - x.point.x();
        if (cmp < 0) {
            return containsY(x.lb, p);
        } else if (cmp > 0) {
            return containsY(x.rt, p);
        } else if (!p.equals(x.point)) {
            return containsY(x.rt, p);
        }
        return x;
    }
    private Node containsY(Node x, Point2D p) {
        if (x == null) {
            return null;
        }
        double cmp = p.y() - x.point.y();
        if (cmp < 0) {
            return containsX(x.lb, p);
        } else if (cmp > 0) {
            return containsX(x.rt, p);
        } else if (!p.equals(x.point)) {
            return containsX(x.rt, p);
        }
        return x;
    }
    public void draw() {
        draw(root, true);
    }
    private void draw(Node x, boolean vertical) {
        if (x == null) {
            return;
        }
        if (vertical) {
            draw(x.lb, false);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.point.x(), x.rect.ymin(), x.point.x(), x.rect.ymax());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            StdDraw.point(x.point.x(), x.point.y());
            draw(x.rt, false);
        } else {
            draw(x.lb, true);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.point.y());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            StdDraw.point(x.point.x(), x.point.y());
            draw(x.rt, true);
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();
        range(root, queue, rect);
        return queue;
    }
    private void range(Node x, Queue<Point2D> queue, RectHV rect) {
        if (x == null) {
            return;
        }
        if (x.rect.intersects(rect)) {
            if (rect.contains(x.point)) {
                queue.enqueue(x.point);
            }
            range(x.lb, queue, rect);
            range(x.rt, queue, rect);
        }
    }
    public Point2D nearest(Point2D p) {
        mindis = Double.POSITIVE_INFINITY;
        near = root.point;
        nearest(root, p);
        return near;
    }
    private void nearest(Node x, Point2D p) {
        if (x == null) {
            return;
        }
        double pTp = Double.POSITIVE_INFINITY;
        if (!p.equals(x.point)) {
            pTp = p.distanceSquaredTo(x.point);
            if (pTp <= mindis) {
                mindis = pTp;
                near = x.point;
            }
        }
        if (x.lb == null) {
            nearest(x.rt, p);
        } else if (x.rt == null) {
            nearest(x.lb, p);
        } else if (x.lb.rect.distanceSquaredTo(p)
                < x.rt.rect.distanceSquaredTo(p)) {
            nearest(x.lb, p);
            if (mindis >= x.rect.distanceSquaredTo(p)) {
                nearest(x.rt, p);
            }
        } else {
            nearest(x.rt, p);
            if (mindis >= x.rect.distanceSquaredTo(p)) {
                nearest(x.lb, p);
            }
        }
    }
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        for (int i = 0; i < 100; i++) {
            StdOut.println(kdtree.nearest(new Point2D(.1, .8)));
            StdOut.println(kdtree.nearest(new Point2D(.1, .6)));
//        StdOut.println(brute.nearest(new Point2D(.1, .8)));
//        StdOut.println(brute.nearest(new Point2D(.1, .6)));
        }

    }
}

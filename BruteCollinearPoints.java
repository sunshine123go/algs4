package lab3;

/**
 * Created by wcf on 15-10-5.
 */
public class BruteCollinearPoints {
    private Point[] points;
    private Point p, q, r, s;
    private int pN;
    private LineSegment[] lineSegments;
    private int linenumber;

    public BruteCollinearPoints(Point[] points) {
        if(points == null) {
            throw new java.lang.NullPointerException("argument is null");
        }
        this.points = points;
        this.pN = points.length;
        lineSegments = new LineSegment[pN];
        linenumber = 0;

        for(int i = 0; i < pN; i++)
            for(int j = i + 1; j < pN; j++)
                for(int k = j + 1; j < pN; j++)
                    for(int l = k + 1; l < pN; l++) {
                        p = points[i];
                        q = points[j];
                        r = points[k];
                        s = points[l];
                        if (p.compareTo(q) == 0 || p.compareTo(r) == 0
                                || p.compareTo(s) == 0 || q.compareTo(r) == 0
                                || q.compareTo(s) == 0 || r.compareTo(s) == 0) {
                            throw new java.lang.IllegalArgumentException("illegal arguments");
                        }

                        if(p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            lineSegments[linenumber++] = line(p, q, r, s);
                        }
                    }
    }

    private LineSegment line(Point p, Point q, Point r, Point s) {
        Point minp, maxp;
        minp = p.compareTo(q) < 0 ? p : q;
        minp = minp.compareTo(r) < 0 ? minp : r;
        minp = minp.compareTo(s) < 0 ? minp : s;

        maxp = p.compareTo(q) > 0 ? p : q;
        maxp = maxp.compareTo(r) > 0 ? maxp : r;
        maxp = maxp.compareTo(s) > 0 ? maxp :s;

        return new LineSegment(minp, maxp);
    }
    public int numberOfSegments() {
        return linenumber;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}

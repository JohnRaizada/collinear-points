import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final int X_ORIGIN = 5000;
    private static final int Y_ORIGIN = 5000;
    private static final Point ORIGIN = new Point(X_ORIGIN, Y_ORIGIN);
    private Point[] points;
    private LineSegment[] segments;

    /**
     * Find all line segments containing 4 or more points.
     *
     * @param points input points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        this.points = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }

        Arrays.sort(this.points);
        checkForRepeatedPoints();

        Arrays.sort(this.points, ORIGIN.slopeOrder());
        this.segments = generateSegments();
    }

    private void checkForRepeatedPoints() {
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && Double.compare(points[i].slopeTo(points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private LineSegment[] generateSegments() {
        ArrayList<LineSegment> lineCombinations = new ArrayList<>();

        Point start = null;
        Point end = null;
        int counter = 0;

        for (int i = 0; i < this.points.length; i++) {

            for (int j = i + 1; j < this.points.length; j++) {
                if (ORIGIN.slopeTo(this.points[i]) == ORIGIN.slopeTo(this.points[j])) {
                    if (counter == 0) start = this.points[i];

                    counter++;
                }
                else {
                    if (counter >= 2) {
                        end = this.points[j - 1];
                        i = j - 1;
                        lineCombinations.add(new LineSegment(start, end));
                    }
                    counter = 0;
                    break;
                }
            }
        }

        LineSegment[] segments = new LineSegment[lineCombinations.size()];
        return lineCombinations.toArray(segments);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
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
        StdDraw.show();
    }
}

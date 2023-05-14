import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * ASSESSMENT SUMMARY
 * <p>
 * Compilation:  PASSED
 * API:          PASSED
 * <p>
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   PASSED
 * <p>
 * Correctness:  41/41 tests passed
 * Memory:       1/1 tests passed
 * Timing:       41/41 tests passed
 */
public class FastCollinearPoints {
    private LineSegment[] segments;

    /**
     * Find all line segments containing 4 or more points.
     *
     * @param points input points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }
        Point[] pointsNaturalOrder = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsNaturalOrder);

        checkForRepeatedPoints(pointsNaturalOrder);
        ArrayList<LineSegment> lineSegments = getLineSegmentsFromPoints(pointsNaturalOrder);
        segments = lineSegments.toArray(new LineSegment[0]);
    }

    private void checkForRepeatedPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && Double.compare(points[i].slopeTo(points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private ArrayList<LineSegment> getLineSegmentsFromPoints(Point[] points) {
        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        Point[] pointsNaturalOrder = Arrays.copyOf(points, points.length);
        Point[] pointsSlopeOrder = Arrays.copyOf(points, points.length);

        LinkedList<Point> collinearPoints = new LinkedList<>();

        for (Point point : pointsNaturalOrder) {
            Arrays.sort(pointsSlopeOrder);
            Arrays.sort(pointsSlopeOrder, point.slopeOrder());
            double previousSlope = 0.0;

            for (int i = 0; i < pointsSlopeOrder.length; i++) {
                double currentSlope = point.slopeTo(pointsSlopeOrder[i]);
                if (i == 0 || currentSlope != previousSlope) {
                    if (collinearPoints.size() >= 3) {
                        if (collinearPoints.getFirst().compareTo(point) > 0)
                            lineSegments.add(new LineSegment(point,
                                                             collinearPoints.getLast()));
                    }

                    collinearPoints.clear();
                }
                collinearPoints.add(pointsSlopeOrder[i]);
                previousSlope = currentSlope;

                if (i == pointsSlopeOrder.length - 1 && collinearPoints.size() >= 3
                        && collinearPoints.getFirst().compareTo(point) > 0) {
                    lineSegments.add(new LineSegment(point, collinearPoints.getLast()));
                    collinearPoints.clear();
                }
            }
        }
        return lineSegments;
    }

    /**
     * Return the number of line segments.
     *
     * @return number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Return the line segments themselves.
     *
     * @return all line segments
     */
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

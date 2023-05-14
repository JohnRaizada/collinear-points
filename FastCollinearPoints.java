import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

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
        checkForRepeatedPoints(points);

        // getLineSegmentsFromPoints(Arrays.copyOf(points, points.length));

        Point[] pointsNaturalOrder = Arrays.copyOf(points, points.length);
        Point[] pointsSlopeOrder = Arrays.copyOf(points, points.length);

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        Arrays.sort(pointsNaturalOrder);
        Arrays.sort(pointsSlopeOrder);

        for (int i = 0; i < pointsNaturalOrder.length; i++) {
            Point origin = pointsNaturalOrder[i];
            Point lineBeginning = null;
            Arrays.sort(pointsSlopeOrder);
            Arrays.sort(pointsSlopeOrder, origin.slopeOrder());
            int pointsCount = 1;
            for (int k = i + 1; k < pointsSlopeOrder.length - 1; k++) {

                double currentPointSlopeToOrigin = pointsSlopeOrder[k].slopeTo(origin);
                double nextPointSlopeToOrigin = pointsSlopeOrder[k + 1].slopeTo(origin);

                if (currentPointSlopeToOrigin == nextPointSlopeToOrigin) {
                    pointsCount++;

                    if (pointsCount == 2) {
                        lineBeginning = pointsSlopeOrder[k];
                        pointsCount++;
                    }
                    else if (pointsCount >= 4 && k + 1 == pointsSlopeOrder.length - 1) {
                        if (lineBeginning.compareTo(origin) > 0)
                            lineSegments.add(
                                    new LineSegment(lineBeginning, pointsSlopeOrder[k + 1]));
                        pointsCount = 1;
                    }

                }

                else if (pointsCount >= 4) {
                    if (lineBeginning.compareTo(origin) > 0)
                        lineSegments.add(new LineSegment(origin, pointsSlopeOrder[k]));
                    pointsCount = 1;
                }
                else pointsCount = 1;

            }
        }

        segments = lineSegments.toArray(new LineSegment[0]);

        // this.segments = generateSegments();
    }

    private void checkForRepeatedPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && Double.compare(points[i].slopeTo(points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }


    // private ArrayList<LineSegment> getLineSegmentsFromPoints(Point[] points) {
    //
    // }

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * Examine 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p and r, and between p and s are all equal.
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numOfSegments;


    /**
     * Find all line segments containing 4 points
     *
     * @param inputPoints all points
     */
    public BruteCollinearPoints(Point[] inputPoints) {
        if (inputPoints == null) throw new IllegalArgumentException();
        for (Point point : inputPoints) {
            if (point == null) throw new IllegalArgumentException();
        }

        Point[] points = Arrays.copyOf(inputPoints, inputPoints.length);
        Arrays.sort(points);
        checkForRepeatedPoints(points);
        this.segments = findFourPointCombinations(points);
        numOfSegments = segments.length;
    }

    private void checkForRepeatedPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && Double.compare(points[i].slopeTo(points[i - 1]),
                                        Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }


    private LineSegment[] findFourPointCombinations(Point[] input) {
        List<List<Point>> lineCombinations = findLineCombinations(input);
        LineSegment[] lineSegments = new LineSegment[lineCombinations.size()];
        int index = 0;
        for (List<Point> combination : lineCombinations) {
            lineSegments[index++] = new LineSegment(combination.get(0), combination.get(3));
        }
        return lineSegments;
    }

    private List<List<Point>> findLineCombinations(Point[] input) {
        List<List<Point>> lineCombinations = new ArrayList<>();

        for (int i = 0; i < input.length - 3; i++) {
            for (int j = i + 1; j < input.length - 2; j++) {
                for (int k = j + 1; k < input.length - 1; k++) {
                    for (int m = k + 1; m < input.length; m++) {
                        Point first = input[i];
                        Point second = input[j];
                        Point third = input[k];
                        Point fourth = input[m];
                        if (checkIfPointsAreOnTheSameLine(first, second, third, fourth)) {
                            List<Point> combination = Arrays.asList(first, second, third, fourth);
                            lineCombinations.add(combination);
                        }
                    }
                }
            }
        }
        return lineCombinations;
    }

    private boolean checkIfPointsAreOnTheSameLine(Point first, Point second,
                                                  Point third, Point fourth) {
        return first.slopeOrder().compare(second, third) == 0
                && first.slopeOrder().compare(third, fourth) == 0;
    }

    /**
     * Return the number of line segments.
     *
     * @return number of line segments
     */
    public int numberOfSegments() {
        return numOfSegments;
    }

    /**
     * Return the line segments themselves.
     *
     * @return line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {
        Point p1 = new Point(10000, 0);
        Point p2 = new Point(0, 10000);
        Point p3 = new Point(3000, 7000);
        Point p4 = new Point(7000, 3000);
        Point p5 = new Point(20000, 21000);
        Point p6 = new Point(3000, 4000);
        Point p7 = new Point(14000, 15000);
        Point p8 = new Point(6000, 7000);

        Point[] points = { p1, p2, p3, p4, p5, p6, p7, p8 };

        BruteCollinearPoints bb = new BruteCollinearPoints(points);
        System.out.println(Arrays.toString(bb.segments()));
    }
}

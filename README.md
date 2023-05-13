<img height="400" src="logo.png" title="princetopn logo" width="1000"/>

### Collinear Points

**Algorithms Part 1 | Coursera course | Week 3
Assignment | [my coursera profile](https://www.coursera.org/user/045cf702be8b31ef1aa039e2b4f07db6)**

---

### Task specification

🔗Detailed specifications for the assignment can be
found [here](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php).

The task is to write a program that recognizes line patterns in a given set of points.

**The problem:**
Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of
the points.

Start with creating an immutable data type `Point` that represents a point inthe plane.

Next implement `LineSegment` data type to represent line segments in the plane.

**Brute force solution:** program `BruteCollinearPoints` that examines 4 points at a time and checks whether they all
lie on the same line segment, returning all such line segments.
To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p
and r, and between p and s are all equal.

**Faster, sorting-based solution:** given a point p, the following method determines whether p participates in a set of
4 or more collinear points.

- Think of p as the origin.
- For each other point q, determine the slope it makes with p.
- Sort the points according to the slopes they make with p.
- Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points,
  together with p, are collinear.

Applying this method for each of the n points in turn yields an efficient algorithm to the problem.

The algorithm solves the problem because points that have equal slopes with respect to p are collinear, and sorting
brings such points together.

The algorithm is fast because the bottleneck operation is sorting.

---

#### Point API

```
public class Point implements Comparable<Point> {
   public Point(int x, int y)               // constructs the point (x, y)
   public void draw()                       // draws this point
   public void drawTo(Point that)           // draws the line segment from this point to that point
   public String toString()                 // string representation
   public int compareTo(Point that)         // compare two points by y-coordinates, breaking ties by x-coordinates
   public double slopeTo(Point that)        // the slope between this point and that point
   public Comparator<Point> slopeOrder()    // compare two points by slopes they make with this point
}
```

---

#### Line Segment API

```
public class LineSegment {
   public LineSegment(Point p, Point q)     // constructs the line segment between points p and q
   public void draw()                       // draws this line segment
   public String toString()                 // string representation
}
```

---

#### Brute force solution API

```
public class BruteCollinearPoints {
   public BruteCollinearPoints(Point[] points)      // finds all line segments containing 4 points
   public int numberOfSegments()                    // the number of line segments
   public LineSegment[] segments()                  // the line segments
}
```

---

#### Fast solution API

```
public class FastCollinearPoints {
   public FastCollinearPoints(Point[] points)   // finds all line segments containing 4 or more points
   public int numberOfSegments()                // the number of line segments
   public LineSegment[] segments()              // the line segments
}
```

---

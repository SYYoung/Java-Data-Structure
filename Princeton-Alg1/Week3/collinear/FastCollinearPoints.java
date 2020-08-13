/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int totalSegment = 0;

    private LineSegment[] allSegments = new LineSegment[8];

    // find all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        checkValidityOfPoint(points);
        int totalPoint = points.length;
        for (int i = 0; i < totalPoint; i++) {
            double[] slopes = new double[totalPoint - i - 1];
            int k = 0;
            for (int j = i + 1; j < totalPoint; j++)
                slopes[k++] = points[i].slopeTo(points[j]);
            findCollinearEndPoint(slopes, i, points);
        }
    }

    private void checkValidityOfPoint(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        Arrays.sort(points);
    }

    private void assignLineSegment(Point startPoint, Point endPoint) {
        if (totalSegment >= allSegments.length)
            resize(2 * allSegments.length);
        allSegments[totalSegment++] = new LineSegment(startPoint, endPoint);
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < totalSegment; i++)
            copy[i] = allSegments[i];
        allSegments = copy;
    }

    private void findCollinearEndPoint(double[] allSlopes, int startIndex,
                                       Point[] points) {
        // sort the slopes and also the corresponding points
        // use the copy of points to be sorted instead of the points itself
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(allSlopes);
        Arrays.sort(copy, copy[startIndex].slopeOrder());
        int i = 0;
        int j = i + 1;
        int origin = 0;
        while ((i < allSlopes.length - 1) && (j < allSlopes.length)) {
            if (allSlopes[i] == allSlopes[j])
                j++;
            else if (j - i > 2) {
                assignLineSegment(copy[origin], copy[origin + j - 1]);
                i++;
                j = i + 1;
            }
            else {
                i++;
                j = i + 1;
            }
        }
        if (j - i > 2)
            assignLineSegment(copy[origin], copy[origin + j]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return totalSegment;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(allSegments, totalSegment);
    }

    public static void main(String[] args) {
        // 1. get the input
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        Arrays.sort(points);
        StdOut.println("Input points: ");
        for (Point eachPoint : points)
            StdOut.println(eachPoint);

        // set up for drawing
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point eachPoint : points)
            eachPoint.draw();
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment eachSet : collinear.segments()) {
            StdOut.println(eachSet);
            eachSet.draw();
        }
        StdDraw.show();
    }
}


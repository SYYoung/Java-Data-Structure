/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int totalSegment = 0;

    private LineSegment[] allSegments = new LineSegment[8];

    // find all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        int totalPoint = points.length;
        Point[] eachSet = new Point[4];
        for (int i = 0; i < totalPoint; i++) {
            eachSet[0] = points[i];
            for (int j = i + 1; j < totalPoint; j++) {
                eachSet[1] = points[j];
                for (int k = j + 1; k < totalPoint; k++) {
                    eachSet[2] = points[k];
                    for (int m = k + 1; m < totalPoint; m++) {
                        eachSet[3] = points[m];
                        if (areCollinear(eachSet)) {
                            assignLineSegment(eachSet);
                        }
                    }
                }
            }
        }
    }

    private void assignLineSegment(Point[] pointSet) {
        if (totalSegment >= allSegments.length)
            resize(2 * allSegments.length);
        allSegments[totalSegment++] = new LineSegment(pointSet[0], pointSet[3]);
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < totalSegment; i++)
            copy[i] = allSegments[i];
        allSegments = copy;
    }

    private boolean areCollinear(Point[] pointSet) {
        Arrays.sort(pointSet);
        double[] slopes = new double[3];
        slopes[0] = pointSet[0].slopeTo(pointSet[1]);
        slopes[1] = pointSet[0].slopeTo(pointSet[2]);
        slopes[2] = pointSet[0].slopeTo(pointSet[3]);
        return ((slopes[0] == slopes[1]) && (slopes[1] == slopes[2]));
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

        // 2. invoke BruteCollinearPoints
        BruteCollinearPoints bruteCollinearPt = new BruteCollinearPoints(points);
        StdOut.println("Total line segments = " + bruteCollinearPt.numberOfSegments());
        LineSegment[] result = bruteCollinearPt.segments();
        for (LineSegment eachSeg : result)
            StdOut.println(eachSeg);
        // set up for drawing
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (LineSegment eachSet : result)
            eachSet.draw();
        StdDraw.show();
        // check the shuffle
        /*
        int k = 4;
        int[] a = new int[6];
        for (int i = 0; i < a.length; i++)
            a[i] = i + 1;
        for (int i : a)
            StdOut.print("\t," + i);
        StdOut.println();
        for (int i = 1; i <= 360; i++) {
            int[] result = StdRandom.permutation(a.length, k);
            for (int j = 0; j < result.length; j++)
                StdOut.print("\t" + result[j]);
            StdOut.println();
        }

         */
    }
}

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
    private LineSlope tmpSegments = new LineSlope();
    private LineSegment[] allSegments = new LineSegment[8];

    // find all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {

        checkValidityOfPoint(points);
        Arrays.sort(points);
        int totalPoint = points.length;
        for (int i = 0; i < totalPoint - 3; i++) {
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

    private void assignLineSegment(Point startPoint, Point endPoint,
                                   double slope) {
        if (!(tmpSegments.lineExists(startPoint, endPoint, slope))) {
            tmpSegments.assignLineSlope(startPoint, endPoint, slope);
            if (totalSegment >= allSegments.length)
                resize(2 * allSegments.length);
            allSegments[totalSegment++] = new LineSegment(startPoint, endPoint);
        }
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
        Point[] copy = Arrays.copyOfRange(points, startIndex, points.length);
        Arrays.sort(allSlopes);
        Arrays.sort(copy, copy[0].slopeOrder());
        int i = 0;
        int j = i + 1;
        int origin = 0;
        while ((i < allSlopes.length - 1) && (j < allSlopes.length)) {
            if (allSlopes[i] == allSlopes[j])
                j++;
            else if (j - i >= 3) {
                assignLineSegment(copy[origin], copy[origin + j], allSlopes[i]);
                i = j;
                j = i + 1;
            }
            else {
                i++;
                j = i + 1;
            }
        }
        if (j - i >= 3)
            assignLineSegment(copy[origin], copy[origin + j], allSlopes[i]);
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
        // Arrays.sort(points);
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

    public class LineSlopeNode {
        Point startPt;
        Point endPt;
        double slope;

        public LineSlopeNode(Point start, Point end, double slope) {
            this.startPt = start;
            this.endPt = end;
            this.slope = slope;
        }
    }

    public class LineSlope {
        private int totalSeg = 0;
        private LineSlopeNode[] allSegs = new LineSlopeNode[8];

        public boolean lineExists(Point start, Point end, double slope) {
            for (int i = 0; i < totalSeg; i++) {
                if (((allSegs[i].startPt == start) || (allSegs[i].endPt == end)) &&
                        (allSegs[i].slope == slope))
                    return true;
            }
            return false;
        }

        private void assignLineSlope(Point startPoint, Point endPoint,
                                     double slope) {
            if (totalSeg >= allSegs.length)
                resize(2 * allSegs.length);
            allSegs[totalSeg++] = new LineSlopeNode(startPoint, endPoint, slope);
        }

        private void resize(int capacity) {
            LineSlopeNode[] copy = new LineSlopeNode[capacity];
            for (int i = 0; i < totalSeg; i++)
                copy[i] = allSegs[i];
            allSegs = copy;
        }
    }
}


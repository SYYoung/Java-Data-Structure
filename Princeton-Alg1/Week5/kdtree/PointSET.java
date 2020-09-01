/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> setOfPoint;

    public PointSET() {
        setOfPoint = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return setOfPoint.isEmpty();
    }

    public int size() {
        return setOfPoint.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        setOfPoint.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return setOfPoint.contains(p);
    }

    public void draw() {
        for (Point2D each : setOfPoint)
            each.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> whichPoints = new ArrayList<Point2D>();
        if (rect == null)
            throw new IllegalArgumentException();
        for (Point2D each : setOfPoint) {
            if (rect.contains(each))
                whichPoints.add(each);
        }
        return whichPoints;
    }

    public Point2D nearest(Point2D p) {
        Point2D whichPoint = null;
        double minDist = Double.MAX_VALUE;
        if (p == null)
            throw new IllegalArgumentException();
        for (Point2D each : setOfPoint) {
            double dist = each.distanceTo(p);
            if (dist < minDist) {
                whichPoint = each;
                minDist = dist;
            }
        }
        return whichPoint;
    }

    public static void main(String[] args) {

    }
}

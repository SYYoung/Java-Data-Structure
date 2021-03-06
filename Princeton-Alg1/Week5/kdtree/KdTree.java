/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {

    private Node root = null;
    private int totalCount = 0;
    private static final boolean vertical = true;
    private static final boolean horizon = false;

    public KdTree() {
        root = null;
        totalCount = 0;
    }

    public boolean isEmpty() {
        return totalCount == 0;
    }

    public int size() {
        return totalCount;
    }

    private Node insert(Point2D pt, Node x, Node parent) {
        double cmp;
        if (x == null) {
            Node newNode = new Node(pt);
            totalCount++;
            if (parent.direction == vertical) {
                // compare x coord
                cmp = pt.x() - parent.p.x();
                if (cmp < 0) {
                    newNode.direction = horizon;
                    newNode.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                              parent.p.x(), parent.rect.ymax());
                }
                else if (cmp >= 0) {
                    newNode.direction = horizon;
                    newNode.rect = new RectHV(parent.p.x(), parent.rect.ymin(),
                                              parent.rect.xmax(), parent.rect.ymax());
                }
            }
            else {
                // compare y coord
                cmp = pt.y() - parent.p.y();
                if (cmp < 0) {
                    newNode.direction = vertical;
                    newNode.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                              parent.rect.xmax(), parent.p.y());
                }
                else if (cmp >= 0) {
                    newNode.direction = vertical;
                    newNode.rect = new RectHV(parent.rect.xmin(), parent.p.y(),
                                              parent.rect.xmax(), parent.rect.ymax());
                }
            }
            return newNode;
        } // end of create a new node
        else if (pt.equals(x.p))
            return x;
        else {
            if (x.direction == vertical) {
                // compare x coord
                cmp = pt.x() - x.p.x();
                if (cmp < 0) {
                    x.leftBot = insert(pt, x.leftBot, x);
                }
                else if (cmp >= 0) {
                    x.rightTop = insert(pt, x.rightTop, x);
                }
            }
            else {
                // compare y coord
                cmp = pt.y() - x.p.y();
                if (cmp < 0) {
                    x.leftBot = insert(pt, x.leftBot, x);
                }
                else if (cmp >= 0) {
                    x.rightTop = insert(pt, x.rightTop, x);
                }
            }
            return x;
        }
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        RectHV theRect = new RectHV(0, 0, 1, 1);
        // search the position
        if (isEmpty()) {
            root = new Node(p, theRect, vertical);
            totalCount++;
        }
        else {
            root = insert(p, root, null);
        }
    }

    private boolean contains(Point2D pt, Node x) {
        while (x != null) {
            if ((pt.x() == x.p.x()) && (pt.y() == x.p.y()))
                return true;
            if (x.direction == vertical) {
                // compare x coord
                double cmp = pt.x() - x.p.x();
                if (cmp < 0)
                    x = x.leftBot;
                else if (cmp >= 0)
                    x = x.rightTop;
            }
            else {
                // compare y coord
                double cmp = pt.y() - x.p.y();
                if (cmp < 0)
                    x = x.leftBot;
                else if (cmp >= 0)
                    x = x.rightTop;
            }
        }
        return false;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return false;
        return contains(p, root);
    }

    public void draw() {
        Queue<Node> q = new Queue<Node>();
        inorder(root, q);
        while (!q.isEmpty()) {
            Node x = q.dequeue();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            x.p.draw();
            StdDraw.setPenRadius(0.006);
            if (x.direction == vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }
        }
    }

    private void inorder(Node x, Queue<Node> q) {
        if (x == null) return;
        inorder(x.leftBot, q);
        q.enqueue(x);
        inorder(x.rightTop, q);
    }

    private void printTree() {
        Queue<Node> q = new Queue<Node>();
        inorder(root, q);
        while (!q.isEmpty()) {
            Node x = q.dequeue();
            StdOut.print("pt: " + x.p.x() + ", " + x.p.y() +
                                 "rect: " + x.rect.xmin() + ", " + x.rect.ymin()
                                 + ", " + x.rect.xmax() + ", " + x.rect.ymax());
            StdOut.println();
        }
    }

    private void getRange(RectHV theRect, Node x, ArrayList<Point2D> rangeList) {
        if (x != null) {
            if (x.rect.intersects(theRect)) {
                if (theRect.contains(x.p))
                    rangeList.add(x.p);
                getRange(theRect, x.leftBot, rangeList);
                getRange(theRect, x.rightTop, rangeList);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> rangeList = new ArrayList<Point2D>();
        getRange(rect, root, rangeList);
        return rangeList;
    }

    private boolean rightOrLeft(Point2D queryP, Node x) {
        boolean rightTop = true;
        if (x.direction == vertical) {
            // compare x coord
            double cmp = queryP.x() - x.p.x();
            if (cmp < 0)
                rightTop = false;
            else if (cmp >= 0)
                rightTop = true;
        }
        else {
            // compare y coord
            double cmp = queryP.y() - x.p.y();
            if (cmp < 0)
                rightTop = false;
            else if (cmp >= 0)
                rightTop = true;
        }
        return rightTop;
    }

    private void nearestPoint(Point2D queryP, Node x, NearestNeighbor neighbor) {
        if (x != null) {
            if (x.rect.distanceSquaredTo(queryP) < neighbor.dist) {
                double dist = x.p.distanceSquaredTo(queryP);
                if (dist < neighbor.dist) {
                    neighbor.neighborPt = x.p;
                    neighbor.dist = dist;
                }
                if (rightOrLeft(queryP, x) == false) {
                    nearestPoint(queryP, x.leftBot, neighbor);
                    nearestPoint(queryP, x.rightTop, neighbor);
                }
                else {
                    nearestPoint(queryP, x.rightTop, neighbor);
                    nearestPoint(queryP, x.leftBot, neighbor);
                }
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        // should be modified later
        if (isEmpty()) return null;
        NearestNeighbor neighbor = new NearestNeighbor(p);
        nearestPoint(p, root, neighbor);
        if (neighbor.neighborPt != null)
            return neighbor.neighborPt;
        else
            return null;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.printTree();
        kdtree.draw();
        StdDraw.show();
        StdDraw.pause(40);

        double x = 0.72;
        double y = 0.23;
        Point2D query = new Point2D(x, y);
        query.draw();
        StdDraw.show();
        StdDraw.pause(40);

        // draw in blue the nearest neighbor (using kd-tree algorithm)
        StdDraw.setPenColor(StdDraw.BLUE);
        kdtree.nearest(query).draw();
        StdDraw.show();
        StdDraw.pause(40);

        // test contains
        Point2D pt = kdtree.root.leftBot.rightTop.p;
        boolean found = kdtree.contains(pt);
        StdOut.println("point : " + pt + "\t found or not? " + found);
        Point2D pt2 = new Point2D(pt.x() + 0.01, pt.y() + 0.01);
        found = kdtree.contains(pt2);
        StdOut.println("point : " + pt2 + "\t found or not? " + found);
    }

    private class Node {
        private Point2D p;
        private RectHV rect;
        private Node leftBot;
        private Node rightTop;
        private boolean direction;

        public Node(Point2D point, RectHV theRect, boolean dir) {
            p = point;
            rect = theRect;
            direction = dir;
            leftBot = null;
            rightTop = null;
        }

        public Node(Point2D point) {
            p = point;
            leftBot = null;
            rightTop = null;
        }
    }

    private class NearestNeighbor {
        private Point2D queryPt;
        private Point2D neighborPt;
        private double dist;

        public NearestNeighbor(Point2D pt) {
            queryPt = pt;
            neighborPt = null;
            dist = Double.MAX_VALUE;
        }
    }

}

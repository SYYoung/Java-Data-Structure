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
                else if (cmp > 0) {
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
                else if (cmp > 0) {
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
                else if (cmp > 0) {
                    x.rightTop = insert(pt, x.rightTop, x);
                }
            }
            else {
                // compare y coord
                cmp = pt.y() - x.p.y();
                if (cmp < 0) {
                    x.leftBot = insert(pt, x.leftBot, x);
                }
                else if (cmp > 0) {
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
                else if (cmp > 0)
                    x = x.rightTop;
            }
            else {
                // compare y coord
                double cmp = pt.y() - x.p.y();
                if (cmp < 0)
                    x = x.leftBot;
                else if (cmp > 0)
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

    public void printTree() {
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
        ArrayList<Point2D> rangeList = new ArrayList<Point2D>();
        getRange(rect, root, rangeList);
        return rangeList;
    }

    public Point2D nearest(Point2D p) {
        // should be modified later
        return new Point2D(0, 0);
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
}

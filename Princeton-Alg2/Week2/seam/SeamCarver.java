/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private static final int HIGHEST_ENERGY = 1000;
    private Picture curPicture;
    private int height;
    private int width;
    private int[] edgeTo;
    private double[] distTo;
    private double[] curEnergy;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        curPicture = new Picture(picture);
        height = curPicture.height();
        width = curPicture.width();
        edgeTo = new int[width * height];
        distTo = new double[width * height];
        curEnergy = new double[width * height];
        for (int i = 0; i < width * height; i++)
            distTo[i] = Integer.MAX_VALUE;

        for (int i = 0; i < width * height; i++) {
            if (i / width == 0) // top row
                curEnergy[i] = HIGHEST_ENERGY;
            else if (i / width == height - 1)
                curEnergy[i] = HIGHEST_ENERGY;
            else if (i % width == 0)
                curEnergy[i] = HIGHEST_ENERGY;
            else if (i % width == width - 1)
                curEnergy[i] = HIGHEST_ENERGY;
            else
                curEnergy[i] = Integer.MAX_VALUE;
        }

    }

    // current picture
    public Picture picture() {
        return curPicture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!validCoord(x, y))
            throw new IllegalArgumentException();
        if (curEnergy[y * width + x] == Integer.MAX_VALUE)
            curEnergy[y * width + x] = calEnergy(x, y);
        return (curEnergy[y * width + x]);
    }

    // sequence of indices for horizonal seam
    public int[] findHorizontalSeam() {

        // save energy in curEnergy
        int[] horIndex = new int[height];
        for (int i = 0; i < width; i++)
            horIndex[i] = edgeTo[i] % width;
        return horIndex;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        MinPQ<Integer> pq = new MinPQ<>();

        // for each vertex on the top row, find the STP
        for (int from = 0; from < width; from++) {
            pq.insert(from);
            int to;
            while (!pq.isEmpty()) {
                int v = pq.delMin();
                to = v + width - 1;
                if (validCoord(to)) {
                    relax(from, to);
                    pq.insert(to);
                }
                to = v + width;
                if (validCoord(v + width)) {
                    relax(from, to);
                    pq.insert(to);
                }
                to = v + width + 1;
                if (validCoord(v + width + 1)) {
                    relax(from, to);
                    pq.insert(to);
                }
            }
        }

        // save energy in curEnergy
        int[] vertIndex = new int[height];
        for (int i = 0; i < height; i++)
            vertIndex[i] = edgeTo[i] % width;
        return vertIndex;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if ((seam == null) || (seam.length != width) || !validSeamArray(seam)
                || (width <= 1))
            throw new IllegalArgumentException();

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if ((seam == null) || (seam.length != height) || !validSeamArray(seam)
                || (height <= 1))
            throw new IllegalArgumentException();

    }

    // unit testing
    public static void main(String[] args) {

    }

    private boolean validCoord(int x, int y) {
        if ((x < 0) || (x > width - 1))
            return false;
        if ((y < 0) || (y > height - 1))
            return false;
        return true;
    }

    private boolean validCoord(int v) {
        int x = v % width;
        int y = v / width;
        if ((x < 0) || (x > width - 1))
            return false;
        if ((y < 0) || (y > height - 1))
            return false;
        return true;
    }

    private boolean validSeamArray(int[] arr) {
        int val = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i] - val) > 1)
                return false;
            val = arr[i];
        }
        return true;
    }

    private boolean isXBorder(int x) {
        if ((x == 0) || (x == width - 1))
            return true;
        else
            return false;
    }

    private boolean isYBorder(int y) {
        if ((y == 0) || (y == height - 1))
            return true;
        else
            return false;
    }

    private double calXGradSquare(int x, int y) {
        int leftRBG, rightRGB;
        leftRBG = curPicture.getRGB(x - 1, y);
        rightRGB = curPicture.getRGB(x + 1, y);
        int lr = (leftRBG >> 16) & 0xFF;
        int lg = (leftRBG >> 8) & 0xFF;
        int lb = (leftRBG >> 0) & 0xFF;
        int rr = (rightRGB >> 16) & 0xFF;
        int rg = (rightRGB >> 8) & 0xFF;
        int rb = (rightRGB >> 0) & 0xFF;
        return ((rr - lr) ^ 2 + (rg - lg) ^ 2 + (rb - lb) ^ 2);
    }

    private double calYGradSquare(int x, int y) {
        int upRGB, downRGB;
        upRGB = curPicture.getRGB(x, y - 1);
        downRGB = curPicture.getRGB(x, y + 1);
        int ur = (upRGB >> 16) & 0xFF;
        int ug = (upRGB >> 8) & 0xFF;
        int ub = (upRGB >> 0) & 0xFF;
        int dr = (downRGB >> 16) & 0xFF;
        int dg = (downRGB >> 8) & 0xFF;
        int db = (downRGB >> 0) & 0xFF;
        return ((ur - dr) ^ 2 + (ug - dg) ^ 2 + (ub - db) ^ 2);
    }

    private void relax(int from, int to) {
        double curVal = energy(to % width, to / width);
        if (distTo[to] > distTo[from] + curVal) {
            distTo[to] = distTo[from] + curVal;
            edgeTo[to] = from;
        }
    }

    public double calEnergy(int x, int y) {
        double xGradSQ = calXGradSquare(x, y);
        double yGradSQ = calYGradSquare(x, y);
        double val = Math.sqrt(xGradSQ + yGradSQ);
        return val;
    }

    /*
    private ArrayList<Integer> buildTopological(int from) {
        ArrayList<Integer> topo = new ArrayList<Integer>();
        Queue<Integer> q = new Queue<Integer>();

        q.enqueue(from);
        topo.add(from);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            // next level of vertex : v+width-1, v+width, v+width+1;
            if (v / height < height - 1) {
                if ( (v % width != 0) &&
                        q.enqueue(v + width - 1);
                q.enqueue(v + width);
                q.enqueue(v + width + 1);
            }
        }

    }
     */
}

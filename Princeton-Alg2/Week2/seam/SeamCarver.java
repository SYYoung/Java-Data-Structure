/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    private static final int HIGHEST_ENERGY = 1000;
    private Picture curPicture;
    private int height;
    private int width;
    private double[][] curEnergy;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        curPicture = new Picture(picture);
        height = curPicture.height();
        width = curPicture.width();
        curEnergy = new double[width][height];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if (i == 0) // top row
                    curEnergy[j][i] = HIGHEST_ENERGY;
                else if (i == height - 1)
                    curEnergy[j][i] = HIGHEST_ENERGY;
                else if (j % width == 0)
                    curEnergy[j][i] = HIGHEST_ENERGY;
                else if (j % width == width - 1)
                    curEnergy[j][i] = HIGHEST_ENERGY;
                else
                    curEnergy[j][i] = Integer.MAX_VALUE;
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
        if (curEnergy[x][y] == Integer.MAX_VALUE)
            curEnergy[x][y] = calEnergy(x, y);
        return (curEnergy[x][y]);
    }

    // sequence of indices for horizonal seam
    public int[] findHorizontalSeam() {
        int[] edgeTo = new int[width * height];
        double[] distTo = new double[width * height];
        for (int i = 0; i < width * height; i++)
            distTo[i] = Integer.MAX_VALUE;

        // save energy in curEnergy
        int[] horIndex = new int[height];
        for (int i = 0; i < width; i++)
            horIndex[i] = edgeTo[i] % width;
        return horIndex;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] edgeTo = new int[width * height];
        double[] distTo = new double[width * height];
        for (int i = 0; i < width * height; i++)
            distTo[i] = Integer.MAX_VALUE;

        // for each vertex on the top row, find the STP
        for (int from = 0; from < width; from++) {
            distTo[from] = 0;
            edgeTo[from] = from;
            MinPQ<Integer> pq = new MinPQ<>();
            pq.insert(from);
            int to;
            while (!pq.isEmpty()) {
                int v = pq.delMin();
                if (v / width == height - 1) // reach bottom line
                    break;
                to = v + width - 1;
                if (validCoord(to)) {
                    relax(from, to, edgeTo, distTo);
                    pq.insert(to);
                }
                to = v + width;
                if (validCoord(v + width)) {
                    relax(from, to, edgeTo, distTo);
                    pq.insert(to);
                }
                to = v + width + 1;
                if (validCoord(v + width + 1)) {
                    relax(from, to, edgeTo, distTo);
                    pq.insert(to);
                }
            }
        }

        int botIndex = getBottomIndex(distTo);
        // save energy in curEnergy
        int[] vertIndex = new int[height];
        int i = height - 1;
        for (int v = botIndex; v < width; v = edgeTo[v]) {
            vertIndex[i--] = v % width;
        }
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
        int test = 1;
        String fname = "";
        if (test == 1)
            fname = "3x4.png";
        Picture pic = new Picture(fname);
        SeamCarver mySeam = new SeamCarver(pic);
        int wd = mySeam.width();
        int ht = mySeam.height();
        double energy;
        for (int i = 0; i < wd * ht; i++)
            energy = mySeam.energy(i % wd, i / wd);
        int[] vertSeam = mySeam.findVerticalSeam();
        for (int i = 0; i < vertSeam.length; i++)
            StdOut.println(vertSeam[i]);
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
        return (Math.pow(lr - rr, 2) + Math.pow(lg - rg, 2) + Math.pow(lb - rb, 2));
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
        return (Math.pow(ur - dr, 2) + Math.pow(ug - dg, 2) + Math.pow(ub - db, 2));
    }

    private void relax(int from, int to, int[] edgeTo, double[] distTo) {
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

    private int getBottomIndex(double[] dist) {
        double min = Double.MAX_VALUE;
        int whichIndex = 0;
        for (int i = width * height - width; i < width * height; i++) {
            if (min > dist[i]) {
                min = dist[i];
                whichIndex = i;
            }
        }
        return whichIndex;
    }
}

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private static final int HIGHEST_ENERGY = 1000;
    private Picture curPicture;
    private int height;
    private int width;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        curPicture = new Picture(picture);
        height = curPicture.height();
        width = curPicture.width();
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
        if (isXBorder(x) || isYBorder(y))
            return HIGHEST_ENERGY;
        double xGradSQ = calXGradSquare(x, y);
        double yGradSQ = calYGradSquare(x, y);
        double val = Math.sqrt(xGradSQ + yGradSQ);
        return val;
    }

    // sequence of indices for horizonal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
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
}

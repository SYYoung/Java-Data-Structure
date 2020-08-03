/* *****************************************************************************
 *  Name:              SY
 *  Coursera User ID:  123456
 *  Last modified:     08/01/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int blocked = 0;
    private int opened = 1;

    private int numOpen = 0;
    private int[][] grid;
    private WeightedQuickUnionUF myUF;
    private int gridsize;
    private int head = 0; // a node 0 which connects the top column;
    private int tail;

    // creates n-by-n grid, with all sites init blocked
    public Percolation(int n) {
        if (n <= 0) // invalid argument
            throw new IllegalArgumentException();

        gridsize = n;
        tail = n * n + 1;
        grid = new int[n][n];
        myUF = new WeightedQuickUnionUF(n * n + 2);

    }

    // check if the incoming arguments: row and col are out of range
    private boolean validGridRange(int row, int col) {
        int gridDim = grid[0].length;
        return ((row >= 1) && (row <= gridDim) && (col >= 1) &&
                (col <= gridDim));

    }

    // since the upper left grid is (1,1) actual row/col is row/col -1
    private int actualRowCol(int rowCol) {
        return rowCol - 1;
    }

    private void connect(int row, int col) {
        // if it is top row, connect to head node
        int actualNode = gridTo1D(row, col);
        boolean isCorner = ((row == 1) && (col == 1)) ||
                ((row == 1) && (col == gridsize)) ||
                ((row == gridsize) && (col == 1)) ||
                ((row == gridsize) && (col == gridsize));
        boolean onBoundary = (row == 1) || (row == gridsize) ||
                (col == 1) || (col == gridsize);
        if (!onBoundary) {
            boolean leftOpen = isOpen(row, col - 1);
            boolean rightOpen = isOpen(row, col + 1);
            boolean upOpen = isOpen(row - 1, col);
            boolean downOpen = isOpen(row + 1, col);
            if (leftOpen) myUF.union(actualNode, actualNode - 1);
            if (rightOpen) myUF.union(actualNode, actualNode + 1);
            if (upOpen) myUF.union(actualNode, actualNode - gridsize);
            if (downOpen) myUF.union(actualNode, actualNode + gridsize);
        }
        else
            boundaryConnect(row, col);
    }

    private void connectFirstRow(int row, int col) {
        int actualNode = gridTo1D(row, col);
        myUF.union(actualNode, head);
        boolean leftOpen, rightOpen, upOpen, downOpen;
        if (col == 1) {
            rightOpen = isOpen(row, col + 1);
            downOpen = isOpen(row + 1, col);
            if (rightOpen) myUF.union(actualNode, actualNode + 1);
            if (downOpen) myUF.union(actualNode, actualNode + gridsize);
        }
        else if (col == gridsize) {
            leftOpen = isOpen(row, col - 1);
            downOpen = isOpen(row + 1, col);
            if (leftOpen) myUF.union(actualNode, actualNode - 1);
            if (downOpen) myUF.union(actualNode, actualNode + gridsize);
        }
        else {
            leftOpen = isOpen(row, col - 1);
            rightOpen = isOpen(row, col + 1);
            downOpen = isOpen(row + 1, col);
            if (leftOpen) myUF.union(actualNode, actualNode - 1);
            if (rightOpen) myUF.union(actualNode, actualNode + 1);
            if (downOpen) myUF.union(actualNode, actualNode + gridsize);
        }
    }

    private void connectLastRow(int row, int col) {
        boolean upOpen, rightOpen, leftOpen;
        int actualNode = gridTo1D(row, col);
        myUF.union(actualNode, tail);
        if (col == 1) {
            rightOpen = isOpen(row, col + 1);
            upOpen = isOpen(row - 1, col);
            if (rightOpen) myUF.union(actualNode, actualNode + 1);
            if (upOpen) myUF.union(actualNode, actualNode - gridsize);
        }
        else if (col == gridsize) {
            leftOpen = isOpen(row, col - 1);
            upOpen = isOpen(row - 1, col);
            if (leftOpen) myUF.union(actualNode, actualNode - 1);
            if (upOpen) myUF.union(actualNode, actualNode - gridsize);
        }
        else {
            rightOpen = isOpen(row, col + 1);
            upOpen = isOpen(row - 1, col);
            leftOpen = isOpen(row, col - 1);
            if (leftOpen) myUF.union(actualNode, actualNode - 1);
            if (rightOpen) myUF.union(actualNode, actualNode + 1);
            if (upOpen) myUF.union(actualNode, actualNode - gridsize);
        }
    }

    private void connectFirstCol(int row, int col) {
        boolean upOpen, downOpen, rightOpen;
        int actualNode = gridTo1D(row, col);
        rightOpen = isOpen(row, col + 1);
        downOpen = isOpen(row + 1, col);
        upOpen = isOpen(row - 1, col);
        if (rightOpen) myUF.union(actualNode, actualNode + 1);
        if (upOpen) myUF.union(actualNode, actualNode - gridsize);
        if (downOpen) myUF.union(actualNode, actualNode + gridsize);
    }

    private void connectLastCol(int row, int col) {
        int actualNode = gridTo1D(row, col);
        boolean upOpen, downOpen, leftOpen;
        leftOpen = isOpen(row, col - 1);
        downOpen = isOpen(row + 1, col);
        upOpen = isOpen(row - 1, col);
        if (leftOpen) myUF.union(actualNode, actualNode - 1);
        if (upOpen) myUF.union(actualNode, actualNode - gridsize);
        if (downOpen) myUF.union(actualNode, actualNode + gridsize);
    }

    // take care the boundary case: first row/col, last row/col
    private void boundaryConnect(int row, int col) {
        // case 1: top row
        if (row == 1)
            connectFirstRow(row, col);
        else if (row == gridsize)
            connectLastRow(row, col);
        else if (col == 1)
            connectFirstCol(row, col);
        else if (col == gridsize)
            connectLastCol(row, col);

    }

    // opens the site if it is not open already
    public void open(int row, int col) {
        if (!validGridRange(row, col))
            throw new IllegalArgumentException();
        int actualRow = actualRowCol(row);
        int actualCol = actualRowCol(col);
        if (grid[actualRow][actualCol] == blocked) {
            grid[actualRow][actualCol] = opened;
            numOpen++;
            connect(row, col);
        }
    }

    // is the site open?
    public boolean isOpen(int row, int col) {
        if (!validGridRange(row, col))
            throw new IllegalArgumentException();
        return (grid[actualRowCol(row)][actualRowCol(col)] == opened);
    }

    private int gridTo1D(int row, int col) {
        return actualRowCol(row) * gridsize + col;
    }

    // is the site full?
    public boolean isFull(int row, int col) {
        if (!validGridRange(row, col))
            throw new IllegalArgumentException();
        return myUF.find(gridTo1D(row, col)) == myUF.find(head);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return myUF.find(head) == myUF.find(tail);
    }

    // test client (optinal)
    public static void main(String[] args) {
        int times = 0;
        int trial = 18;
        int num = Integer.parseInt(args[0]);
        Percolation myPercolate = new Percolation(num);
        // StdRandom.setSeed(1);
        while (times < trial) {
            int row = StdRandom.uniform(1, num + 1);
            int col = StdRandom.uniform(1, num + 1);
            myPercolate.open(row, col);
            /*
            StdOut.println("row, col, #opensites: " + row + ", " + col +
                                   ", " + myPercolate.numberOfOpenSites() +
                                   ",  full or not? " + myPercolate.isFull(row, col));
            StdOut.println("percolate or not ? " + myPercolate.percolates());
            */
            times++;
        }
        // StdOut.println("percolate or not ? " + myPercolate.percolates());
    }
}

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {
    private static int invalidDist = -1;
    private int[][] tile = null;
    private int dim = 0;

    private int manhattanDist = invalidDist;
    private int hammingDist = invalidDist;
    private boolean twinSet = false;
    private int twinPos1 = 0;
    private int twinPos2 = 2;

    private int[][] goal = null;

    // create a board from an n-by-n array of tiels,
    // where tiles[row][col] = tile at (row,col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        if (tile == null)
            tile = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tile[i][j] = tiles[i][j];
        dim = n;

        // build up the goal array
        if (goal == null) {
            // build the goal board
            goal = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    goal[i][j] = i * n + j + 1;
            goal[n - 1][n - 1] = 0;
        }

        // calcuate the distances
        manhattanDist = manhattan();
        hammingDist = hamming();
    }

    // String representations of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = dimension();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tile[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingDist == invalidDist) {
            int dist = 0;
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++) {
                    if (tile[i][j] != goal[i][j]) dist++;
                }
            // the last tile should not be counted
            if (tile[dim - 1][dim - 1] != 0)
                dist--;
            hammingDist = dist;
        }
        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanDist == invalidDist) {
            int dist = 0;
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (tile[i][j] != 0) {
                        if (tile[i][j] != goal[i][j]) {
                            int row = (tile[i][j] - 1) / dim;
                            int col = (tile[i][j] - 1) % dim;
                            dist += Math.abs(i - row) + Math.abs(j - col);
                        }
                    }
                }
            }
            manhattanDist = dist;
        }

        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        Board goalBoard = new Board(goal);
        return (this.equals(goalBoard));
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        if (this.dim != ((Board) other).dim) return false;
        return Arrays.deepEquals(this.tile, ((Board) other).tile);
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        NeighborList myNeighbors = new NeighborList(this);
        return myNeighbors.neighbor;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (!twinSet) {
            if (tile[twinPos1 / dim][twinPos1 % dim] == 0)
                twinPos1 = 1;
            else if (tile[twinPos2 / dim][twinPos2 % dim] == 0)
                twinPos2 = 1;
            twinSet = true;
        }
        int[][] twinTile = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                twinTile[i][j] = tile[i][j];
        int tmp = twinTile[twinPos1 / dim][twinPos1 % dim];
        twinTile[twinPos1 / dim][twinPos1 % dim] = twinTile[twinPos2 / dim][twinPos2 % dim];
        twinTile[twinPos2 / dim][twinPos2 % dim] = tmp;
        return new Board(twinTile);
    }

    private Board twinOld() {
        // pick up two spots which are not space tile and not match with goal board
        int row1 = 0, col1 = 0, row2 = 1, col2 = 0;
        boolean found = false;
        while (!found) {
            if (tile[row1][col1] != 0) found = true;
            else col1++;
        }
        found = false;
        while (!found) {
            int num = StdRandom.uniform(1, dim * dim + 1);
            row2 = (num - 1) / dim;
            col2 = (num - 1) % dim;
            if ((tile[row2][col2] != 0) && ((row1 != row2) || (col1 != col2)))
                found = true;
        }
        int[][] twinTile = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                twinTile[i][j] = tile[i][j];
        int tmp = twinTile[row1][col1];
        twinTile[row1][col1] = twinTile[row2][col2];
        twinTile[row2][col2] = tmp;
        return new Board(twinTile);
    }


    public static void main(String[] args) {

        int[][] a = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };


        // test the board constructor
        Board myBoard = new Board(a);
        StdOut.println(myBoard);
        // test hamming distance
        StdOut.println("Hamming distance = " + myBoard.hamming());
        // test manhattan distance
        StdOut.println("Manhattan distance = " + myBoard.manhattan());
        // test equals
        int testcase = 1;
        int[][] myTile;
        int[][] b = {
                { 7, 5, 8 },
                { 6, 1, 0 },
                { 3, 2, 4 }
        };
        int[][] a1 = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        if (testcase == 1)
            myTile = a1;
        else
            myTile = b;
        Board another = new Board(myTile);
        StdOut.println("Board b: ");
        StdOut.println(another);
        StdOut.println("Is Board a same as board B?" + myBoard.equals(another));

        // test neighbor
        int[][] c = {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };
        int[][] c1 = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };
        if (testcase == 1)
            myTile = c1;
        else
            myTile = c;
        Board bb = new Board(myTile);
        StdOut.println("Test neighbors: input: ");
        StdOut.println(bb);
        StdOut.println("The neighbors are: ");
        for (Board each : bb.neighbors()) {
            StdOut.println(each);
        }

        // test isGoal
        StdOut.println("Goal board: ");
        Board goalBoard = new Board(bb.goal);
        StdOut.println(goalBoard);
        StdOut.println("Test isGoal: " + bb.isGoal());

        // test twin
        StdOut.println("Test Twin: input: ");
        StdOut.println(bb);
        StdOut.println(bb.twin());
        StdOut.println("Twin again.");
        StdOut.println(bb.twin().twin());
    }

    private class NeighborList {
        ArrayList<Board> neighbor = new ArrayList<Board>();

        public NeighborList(Board original) {
            // 1. find out the location of '0'
            int row = 0, col = 0;
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    if (original.tile[i][j] == 0) {
                        row = i;
                        col = j;
                        break;
                    }
            // 2. swap the tile which is adjacent to the space
            // if there is a tile left to space
            int deltaDist = 0;
            if (col - 1 >= 0) {
                buildNewNeighborBoard(row, col - 1, row, col);
            }
            if (col + 1 <= original.dim - 1) {
                buildNewNeighborBoard(row, col + 1, row, col);
            }
            if (row - 1 >= 0) {
                buildNewNeighborBoard(row - 1, col, row, col);
            }
            if (row + 1 <= original.dim - 1) {
                buildNewNeighborBoard(row + 1, col, row, col);
            }

        }

        private void buildNewNeighborBoard(int oldRow, int oldCol, int newRow, int newCol) {
            Board newBoard = swap(oldRow, oldCol, newRow, newCol);
            int deltaDist = 0;
            int actualCol = (newBoard.tile[newRow][newCol] - 1) % dim;
            int actualRow = (newBoard.tile[newRow][newCol] - 1) / dim;
            deltaDist = Math.abs(newCol - actualCol) + Math.abs(newRow - actualRow)
                    - Math.abs(oldCol - actualCol)
                    - Math.abs(oldRow - actualRow);
            newBoard.manhattanDist = manhattanDist + deltaDist;
            neighbor.add(newBoard);
        }

        private Board swap(int row1, int col1, int row2, int col2) {
            Board newBoard = new Board(tile);
            int[][] theTile = newBoard.tile;
            int tmp = theTile[row1][col1];
            theTile[row1][col1] = theTile[row2][col2];
            theTile[row2][col2] = tmp;
            return newBoard;
        }

    }


}

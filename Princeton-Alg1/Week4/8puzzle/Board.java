/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class Board {
    private int[][] tile = null;
    private int dim = 0;

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
        int dist = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                int correctVal = i * dim + j + 1;
                if (tile[i][j] != correctVal) dist++;
            }
        // the last tile should not be counted
        dist--;
        return (dist);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (tile[i][j] != 0) {
                    int correctVal = i * dim + j + 1;
                    if (tile[i][j] != correctVal) {
                        int row = (tile[i][j] - 1) / dim;
                        int col = (tile[i][j] - 1) % dim;
                        dist += Math.abs(i - row) + Math.abs(j - col);
                    }
                }
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        if (this.dim != ((Board) other).dim) return false;
        return Arrays.deepEquals(this.tile, ((Board) other).tile);
    }

    /*
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }
    */

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
        Board another = new Board(a1);
        StdOut.println("Board b: ");
        StdOut.println(another);
        StdOut.println("Is Board a same as board B?" + myBoard.equals(another));
    }
}

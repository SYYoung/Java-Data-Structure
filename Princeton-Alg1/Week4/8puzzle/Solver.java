/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    private ArrayList<Board> boardMovement = new ArrayList<Board>();
    private int totalMove = 0;
    private boolean solvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        BoardNode initNode = new BoardNode(initial, null, 0);
        MinPQ<BoardNode> gameQueue = new MinPQ<BoardNode>();
        gameQueue.insert(initNode);
        // also build a goalBoard
        int n = initial.dimension();
        int[][] goalTile = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                goalTile[i][j] = i * n + j + 1;
        goalTile[n - 1][n - 1] = 0;
        Board goalBoard = new Board(goalTile);
        buildSolution(gameQueue, goalBoard);
    }


    private void buildSolution(MinPQ<BoardNode> gameQueue, Board goalBoard) {
        BoardNode curNode = null;
        int steps = 0;
        ArrayList<Board> visited = new ArrayList<Board>();
        while (!gameQueue.isEmpty()) {
            curNode = gameQueue.delMin();
            if (curNode.theBroad.equals(goalBoard)) {
                solvable = true;
                totalMove = curNode.moveStep;
                break;
            }
            if (visited.indexOf(curNode.theBroad) == -1) {
                // this board hasn't been visited yet
                visited.add(curNode.theBroad);
                int move = curNode.moveStep + 1;
                for (Board each : curNode.theBroad.neighbors()) {
                    // update move and priority of each BoardNode
                    BoardNode newNode = new BoardNode(each, curNode, move);
                    gameQueue.insert(newNode);
                }
            }
        }
        if (solvable) {
            BoardNode traceNode = curNode;
            while (traceNode != null) {
                boardMovement.add(0, traceNode.theBroad);
                traceNode = traceNode.prev;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return totalMove;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return boardMovement;
    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        // In in = new In(args[0]);
        Board initial = null;
        int testcase = 2;
        if (testcase == 1) {
            In input = new In();
            int n = input.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = input.readInt();
            initial = new Board(tiles);
        }
        else if (testcase == 2) {
            int[][] tiles = {
                    { 0, 1, 3 },
                    { 4, 2, 5 },
                    { 7, 8, 6 }
            };
            initial = new Board(tiles);
        }


        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible.");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class BoardNode implements Comparable<BoardNode> {

        private int priority = 0;
        private Board theBroad = null;
        private int dist = 0;
        private int moveStep = 0;
        private BoardNode prev = null;

        public BoardNode(Board initBoard, BoardNode prevBoardNode, int move) {
            theBroad = initBoard;
            prev = prevBoardNode;
            dist = initBoard.hamming();
            moveStep = move;
            priority = dist + moveStep;
        }

        public int compareTo(BoardNode another) {
            if (priority < another.priority) return -1;
            else if (priority == another.priority) return 0;
            else return 1;
        }
    }
}

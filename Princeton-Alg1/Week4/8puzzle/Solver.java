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

        // build queue for init board
        BoardNode initNode = new BoardNode(initial, null, 0);
        MinPQ<BoardNode> gameQueueInit = new MinPQ<BoardNode>();
        gameQueueInit.insert(initNode);
        // build queue for twin board
        BoardNode twinNode = new BoardNode(initial.twin(), null, 0);
        MinPQ<BoardNode> gameQueueTwin = new MinPQ<BoardNode>();
        gameQueueTwin.insert(twinNode);

        buildSolution(gameQueueInit, gameQueueTwin);
    }

    private boolean boardSolvable(MinPQ<BoardNode> gameQueue,
                                  ArrayList<Board> visited) {
        BoardNode curNode = null;
        boolean success = false;
        if (!gameQueue.isEmpty()) {
            curNode = gameQueue.delMin();
            if (curNode.theBroad.isGoal()) {
                success = true;
                totalMove = curNode.moveStep;
            }
            else if (visited.indexOf(curNode.theBroad) == -1) {
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
        if (success) {
            BoardNode traceNode = curNode;
            while (traceNode != null) {
                boardMovement.add(0, traceNode.theBroad);
                traceNode = traceNode.prev;
            }
        }
        return success;
    }

    private void buildSolution(MinPQ<BoardNode> gameQueueInit,
                               MinPQ<BoardNode> gameQueueTwin) {
        // BoardNode curNode = null;
        // int steps = 0;
        ArrayList<Board> visitedInit = new ArrayList<Board>();
        ArrayList<Board> visitedTwin = new ArrayList<Board>();
        boolean initSuccess = false, twinSuccess = false;

        while (!initSuccess && !twinSuccess) {
            initSuccess = boardSolvable(gameQueueInit, visitedInit);
            twinSuccess = boardSolvable(gameQueueTwin, visitedTwin);
        }
        if (initSuccess) {
            solvable = true;
        }
        else {
            solvable = false;
            totalMove = -1;
            boardMovement.clear();
            boardMovement = null;
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
            In input = new In(args[0]);
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
        else if (testcase == 3) {
            int[][] tiles = {
                    { 1, 2, 3 },
                    { 4, 5, 6 },
                    { 8, 7, 0 }
            };
            initial = new Board(tiles);
        }


        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible.");
            StdOut.println("There is solution for the twin.");
        }
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
            dist = initBoard.manhattan();
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

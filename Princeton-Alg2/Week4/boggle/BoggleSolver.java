/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BoggleSolver {

    private static final int MIN_WORD_LENGTH = 3;
    private static final int UPPER_LENGTH_THRESHOLD = 8;
    private static int[] points = { 1, 1, 2, 3, 5, 11 };
    private ArrayList<String> wordsSoFar;
    // the dictionary
    private NewTrieSet dict;
    private int leftOver;

    // Init the data structure using the given array of strings as the dictionary.
    // You can assume each word in the dictionary contains only the upper letters A thru z
    public BoggleSolver(String[] dictionary) {
        wordsSoFar = new ArrayList<String>();
        // 1. build the trie tree of the dictionary
        dict = new NewTrieSet();
        for (String s : dictionary) {
            if (s.length() >= MIN_WORD_LENGTH)
                dict.add(s);
        }
    }

    // returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        ArrayList<String> all = new ArrayList<String>();
        // 1. enumerate all strings that can be composed by adj dice
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                depthFirstSearch(board, i, j, all);
        }

        // 2. for each string, check the longest prefix
        // 2. store the word
        wordsSoFar = all;
        return wordsSoFar;
    }

    // returns the score of the given word if it is in the dictionary, zero otherwise.
    // you can assume the word contains only the uppercase letters A thru Z.
    public int scoreOf(String word) {
        // 1. check if the word is in dictionary. dict.contains(word) is true or not

        // 2. calculate the score of the word
        int score = calScore(word);
        return score;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        String pre = "WAS";
        StdOut.println("words which match: " + pre);
        for (String s : solver.dict.keysWithPrefix(pre)) {
            StdOut.println(s);
        }
        StdOut.println("The longest prefix of word: " + pre + " is : " +
                               solver.dict.longestPrefixOf(pre));
        BoggleBoard board = new BoggleBoard(args[1]);
        StdOut.println(board);
        int score = 0;

        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

    }

    private void depthFirstSearch(BoggleBoard board, int row, int col, ArrayList<String> all) {
        boolean[] marked = new boolean[board.rows() * board.cols()];
        StringBuilder theWord = new StringBuilder();
        dfs(board, row, col, theWord, all, marked);
        // all.add(theWord.toString());
        // reset the theWord and marked
    }

    private ArrayList<Integer> getAdj(int row, int col, int totalRow, int totalCol) {
        ArrayList<Integer> neigbor = new ArrayList<Integer>();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int r = row + i;
                int c = col + j;
                if ((r >= 0) && (r < totalRow) &&
                        ((c >= 0) && (c < totalCol)))
                    neigbor.add(r * totalCol + c);
            }
        return neigbor;
    }

    // depth first search from v
    private void dfs(BoggleBoard board, int row, int col, StringBuilder theWord,
                     ArrayList<String> all,
                     boolean[] marked) {
        int totalRow = board.rows();
        int totalCol = board.cols();

        char ch = board.getLetter(row, col);
        if (ch == 'Q')
            theWord.append("QU");
        else
            theWord.append(ch);
        if (!dict.isPrefixValid(theWord.toString())) {
            theWord.deleteCharAt(theWord.length() - 1);
            return;
        }
        marked[row * totalCol + col] = true;

        // if such prefix exists in dictionary, if not, undo it
        for (int w : getAdj(row, col, totalRow, totalCol)) {
            if (!marked[w]) {
                dfs(board, w / totalCol, w % totalCol, theWord, all, marked);
            }

        }
        String s = theWord.toString();
        if ((dict.contains(s)) && (!all.contains(s)))
            all.add(theWord.toString());
        // reset the theWord and marked
        theWord.deleteCharAt(theWord.length() - 1);
        if (ch == 'Q') // delete one more character
            theWord.deleteCharAt(theWord.length() - 1);
        marked[row * totalCol + col] = false;
    }

    private int calScore(String word) {
        if (word.length() < MIN_WORD_LENGTH)
            return 0;
        else if (word.length() >= UPPER_LENGTH_THRESHOLD)
            return points[UPPER_LENGTH_THRESHOLD - MIN_WORD_LENGTH];
        else
            return points[word.length() - MIN_WORD_LENGTH];

    }
}

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BurrowsWheeler {
    private final static int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        ArrayList<Character> lastColumn = new ArrayList<Character>();
        while (!BinaryStdIn.isEmpty()) {
            String s = BinaryStdIn.readString();
            int len = s.length();
            CircularSuffixArray circularSufArr = new CircularSuffixArray(s);
            // find out the index of the original string
            for (int i = 0; i < len; i++) {
                int ind = circularSufArr.index(i);
                if (ind == 0)
                    BinaryStdOut.write(i, 32);
                int pos = (ind + len - 1) % len;
                lastColumn.add(s.charAt(pos));
            }
            for (int i = 0; i < lastColumn.size(); i++)
                BinaryStdOut.write(lastColumn.get(i));
            BinaryStdOut.flush();
        }
        BinaryStdOut.close();
    }

    // apply Burrows-WHeeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        // read the index of first
        int first;
        /*
        char[] t;
        first = BinaryStdIn.readInt();
        ArrayList<Character> inputT = new ArrayList<Character>();
        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar();
            inputT.add(ch);
        }
        t = (char[])inputT.toArray();
         */
        first = 3;
        String s = "ARD!RCAAAABB";
        char[] t = s.toCharArray();

        // sort the array t
        int N = t.length;
        Queue<Integer>[] tQueue = (Queue<Integer>[]) new Queue[R];
        for (int i = 0; i < R; i++)
            tQueue[i] = new Queue<Integer>();
        int[] count1 = new int[R + 1];
        int[] count2 = new int[R + 1];
        char[] firstCol = new char[N];
        int[] next = new int[N];
        for (int i = 0; i < N; i++) {
            count1[t[i] + 1]++;
            tQueue[t[i]].enqueue(i);
        }
        count2[0] = count1[0];
        for (int r = 0; r < R; r++) {
            count1[r + 1] += count1[r];
            count2[r + 1] = count1[r + 1];
        }
        for (int i = 0; i < N; i++)
            firstCol[count2[t[i]]++] = t[i];

        // build next array
        for (int i = 0; i < N; i++) {
            int pos = tQueue[firstCol[i]].dequeue();
            next[i] = pos;
        }

        // for debugging
        for (int i = 0; i < N; i++)
            StdOut.println(firstCol[i]);
        for (int i = 0; i < N; i++)
            StdOut.println(next[i]);

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-"))
            BurrowsWheeler.transform();
        else
            BurrowsWheeler.inverseTransform();
    }
}

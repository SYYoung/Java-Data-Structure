/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

public class BurrowsWheeler {
    private static final int R = 256;

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
        int N;
        /*
        first = 3;
        String s = "ARD!RCAAAABB";
        char[] t = s.toCharArray();
        N = t.length;
         */

        first = BinaryStdIn.readInt();
        StringBuilder inputT = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar();
            inputT.append(ch);
        }
        N = inputT.length();
        char[] t = new char[N];
        inputT.getChars(0, N, t, 0);


        // sort the array t
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

        // Construct the original string
        char[] original = new char[N];
        original[0] = firstCol[first];
        int nextOne = next[first];
        for (int i = 1; i < N; i++) {
            original[i] = firstCol[nextOne];
            nextOne = next[nextOne];
        }
        // for debugging
        /*
        for (int i = 0; i < N; i++)
            StdOut.println(firstCol[i]);
        for (int i = 0; i < N; i++)
            StdOut.println(next[i]);
        for (int i = 0; i < N; i++)
            StdOut.print(original[i] + "\t");

         */
        for (int i = 0; i < N; i++)
            BinaryStdOut.write(original[i]);
        BinaryStdOut.flush();
        BinaryStdOut.close();
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

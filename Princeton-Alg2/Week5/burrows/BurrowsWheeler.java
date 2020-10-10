/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        ArrayList<Character> lastColumn = new ArrayList<Character>();
        while (!BinaryStdIn.isEmpty()) {
            int first = 0;
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
        while (!BinaryStdIn.isEmpty()) {
            String s = BinaryStdIn.readString();
        }
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        BurrowsWheeler myBurrows = new BurrowsWheeler();
        if (args[0].equals("-"))
            BurrowsWheeler.transform();
        else
            BurrowsWheeler.inverseTransform();
    }
}

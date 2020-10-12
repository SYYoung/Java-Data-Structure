/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private Suffix[] suffixes;

    public CircularSuffixArray(String text) {
        if (text == null)
            throw new IllegalArgumentException();
        int n = text.length();
        this.suffixes = new Suffix[n];
        // String inputText = new String(text + text);
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(text, i);
        Arrays.sort(suffixes);
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;
        private final int len;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
            this.len = text.length();
        }

        private char charAt(int i) {
            return text.charAt((index + i) % len);
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;
            int n = this.text.length();
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return 0;
        }
    }
    // end of private class Suffix

    // length of s
    public int length() {
        return suffixes.length;
    }

    // return index of ith sorted suffix
    public int index(int i) {
        if ((i < 0) || (i >= suffixes.length))
            throw new IllegalArgumentException();
        return suffixes[i].index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // String originalS = "ABRACADABRA!";
        String originalS = "BANANA";
        // correct result: 11, 10, 7, 0, 3, 5, 8, 1, 4,6, 9, 2
        CircularSuffixArray myCircularSArr = new CircularSuffixArray(originalS);
        StdOut.println("The index of string is: " + originalS);
        for (int i = 0; i < originalS.length(); i++) {
            StdOut.println(i + ":\t" + myCircularSArr.index(i));
        }
    }

}

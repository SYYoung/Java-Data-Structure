/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> myRandomQ = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            myRandomQ.enqueue(s);
        }
        if (k > myRandomQ.size())
            throw new java.util.NoSuchElementException();
        int j = 1;
        for (String s : myRandomQ) {
            if (j <= k) StdOut.print("\t" + s);
            else break;
            j++;
        }
    }
}

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> myRandomQ = new RandomizedQueue();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            myRandomQ.enqueue(s);
        }
        if (k < myRandomQ.size())
            throw new java.util.NoSuchElementException();

    }
}

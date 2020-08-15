import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Experiment {
    public static void main(String[] args) {
        int N = 0;
        Integer[] a = null;
        int testcase = 2;
        // testcase =1 for reading from input
        if (testcase == 1) {
            StdOut.println("This is testing.");
            N = Integer.parseInt(args[0]);
            a = new Integer[N];

            int i = 0;
            while (!StdIn.isEmpty()) {
                a[i++] = StdIn.readInt();
            }
        } else if (testcase == 2) {
            // generate a list of random number
            N = 15;
            a = new Integer[N];
            for (int i = 0; i < N; i++)
                a[i] = StdRandom.uniform(1, 100);
        } else if (testcase == 3) {
            // sorted number
            N = 15;
            a = new Integer[N];
            for (int i = 0; i < N; i++)
                a[i] = i + 1;
        } else if (testcase == 4) {
            // sorted number
            N = 15;
            a = new Integer[N];
            for (int i = 0; i < N; i++)
                a[i] = N - i;
        }

        // String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        StdOut.println("Before sorting: ");
        if (a != null)
            for (int i : a)
                StdOut.print("\t," + i);
        StdOut.println();

        // sorting algorithm:
        NewQuick mySelection = new NewQuick();
        mySelection.sort(a);
        StdOut.println("\nAfter sorting: ");
        if (a != null)
            for (int i : a)
                StdOut.print("\t," + i);
    }


}

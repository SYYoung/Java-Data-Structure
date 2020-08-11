import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Experiment {
    public static void main(String[] args) {
        StdOut.println("This is testing.");
        int N = Integer.parseInt(args[0]);
        String[] a = new String[N];
        NewInsertion mySelection = new NewInsertion();

        int i = 0;

        while (!StdIn.isEmpty()) {
            a[i++] = StdIn.readString();
        }

        // String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        StdOut.println("Before sorting: ");
        for (String s : a)
            StdOut.print("\t," + s);
        StdOut.println();
        mySelection.sort(a);
        StdOut.println("\nAfter sorting: ");
        for (String s : a)
            StdOut.print("\t," + s);
    }


}

import edu.princeton.cs.algs4.StdOut;

public class NewInsertion {

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
        /*
        int N = a.length;
        StdOut.println("i\t total iteration");
        int totalIter = 0;
        for (int i = 0; i < N; i++) {
            totalIter++;
            for (int j = i; j > 0; j--)
                if (less(a[j], a[j - 1]))
                    exch(a, j, j - 1);
                else
                    break;
            StdOut.print(i + "\t " + totalIter);
            for (int k = 0; k < a.length; k++)
                StdOut.print("\t" + a[k]);
            StdOut.println();
        }

         */
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        int N = hi + 1;
        StdOut.println("i\t total iteration");
        int totalIter = 0;
        for (int i = lo; i < N; i++) {
            totalIter++;
            for (int j = i; j > lo; j--)
                if (less(a[j], a[j - 1]))
                    exch(a, j, j - 1);
                else
                    break;
            StdOut.print(i + "\t " + totalIter);
            for (int k = 0; k < a.length; k++)
                StdOut.print("\t" + a[k]);
            StdOut.println();
        }
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }
}

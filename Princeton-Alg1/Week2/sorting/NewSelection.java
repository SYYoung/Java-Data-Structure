import edu.princeton.cs.algs4.StdOut;

public class NewSelection {
    public static void sort(Comparable[] a) {
        int N = a.length;
        StdOut.println("i\t j\t num of iteration:");
        int totalIter = 0;
        for (int i = 0; i < N; i++) {
            totalIter++;
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
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

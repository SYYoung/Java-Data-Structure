import edu.princeton.cs.algs4.StdOut;

public class NewQuick {

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo]))
                if (i == hi) break;
            while (less(a[lo], a[--j]))
                if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }

        exch(a, lo, j);
        StdOut.print(lo + "\t" + j + "\t" + hi + "\t");
        for (int m = 0; m < a.length; m++)
            StdOut.print(a[m] + "\t");
        StdOut.println();
        return j;
    }

    public static void sort(Comparable[] a) {
        // StdRandom.shuffle(a);
        StdOut.println("lo \t j \t hi");
        for (int i = 0; i < a.length; i++)
            StdOut.print(i + "\t");
        StdOut.println();
        sort(a, 0, a.length - 1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}

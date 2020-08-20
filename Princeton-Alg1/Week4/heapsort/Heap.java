import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Heap {

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int k = N / 2; k >= 1; k--)
            sink(a, k, N);
        int k = N;
        while (k > 1) {
            exch(a, 1, k--);
            sink(a, 1, k);
        }
    }

    private static void sink(Comparable[] a, int k, int N) {
        while ((2 * k) < N) {
            int j = 2 * k;
            if ((j < N) && less(a, j, j + 1)) j++;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object t = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
    }

    public static void main(String[] args) {
        int N = 8;
        int testcase = 2;
        if (testcase == 1) {
            while (!StdIn.isEmpty()) {
                String s = StdIn.readString();
            }
        } else if (testcase == 2) {
            // generate a list of random number
            Integer[] a = new Integer[N + 1];
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform(1, 100);
            }
            StdOut.println("Input: ");
            for (int i = 0; i < N; i++)
                StdOut.print("\t" + a[i]);
            Heap.sort(a);
            show((Comparable[]) a);
        }

    }
}

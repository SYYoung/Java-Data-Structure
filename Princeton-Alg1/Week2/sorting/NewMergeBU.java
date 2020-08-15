import edu.princeton.cs.algs4.StdOut;

public class NewMergeBU {

    public void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        StdOut.println("lo\t mid\t hi");
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += sz)
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
        }
    }

    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        StdOut.print(lo + "\t " + mid + "\t " + hi + "\t: ");
        for (int m = 0; m < a.length; m++)
            StdOut.print("\t " + a[m]);
        StdOut.println();
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
}

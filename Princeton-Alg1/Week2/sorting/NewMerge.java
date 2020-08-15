import edu.princeton.cs.algs4.StdOut;

public class NewMerge {
    public void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        // test if the partial array has been sorted
        if (!less(a[mid + 1], a[mid])) return;
        merge(a, aux, lo, mid, hi);
    }

    public void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        StdOut.println("lo\t mid\t hi");
        sort(a, aux, 0, a.length - 1);
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

import edu.princeton.cs.algs4.StdIn;

public class Merge extends BaseSort{
	private static Comparable[] aux;
	private final static int THRESHOLD = 15;
	
	private static void merge(Comparable[] a, int lo, int mid, int hi) {
		int i = lo;
		int j = mid + 1;
		
		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		
		for (int k = lo; k <= hi; k++)
			if ( i > mid) a[k] = aux[j++];
			else if (j > hi) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
	}
	
	public static void sort(Comparable[] a) {
		aux = new Comparable[a.length];
		sort(a, 0, a.length - 1);
	}
	
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi - lo <= THRESHOLD) {
			Insertion.sort(a, lo, hi);
			return;
		}
		if (hi <= lo) return;
		int mid = lo + (hi - lo)/2;
		sort(a, lo, mid);
		sort(a, mid + 1, hi);
		if (less(a[mid], a[mid+1])) return;
		merge(a, lo, mid, hi);
	}
	
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}

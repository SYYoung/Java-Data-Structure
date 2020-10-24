import edu.princeton.cs.algs4.StdIn;

public class MergeBU extends BaseSort{
	private static Comparable[] aux;
	
	public static void sort(Comparable[] a) {
		int N = a.length;
		aux = new Comparable[N];
		for (int sz = 1; sz < N; sz = sz + sz)
			for (int lo = 0; lo < N - sz; lo += sz + sz)
				merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1,  N-1));
		}
	
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
	
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}

import edu.princeton.cs.algs4.StdIn;

public class Insertion extends BaseSort{
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
				exch(a, j, j-1);
			//show(a);
		}
	}
	
	public static void sort(Comparable[] a, int lo, int hi) {
		int N = hi - lo + 1;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
				exch(a, j, j-1);
			//show(a);
		}
	}
	
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}

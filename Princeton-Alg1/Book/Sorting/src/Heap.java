import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Heap {
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int k = N/2; k >= 1; k--) {
			sink(a, k, N);
		}
		int k = N;
		while (k > 1) {
			Heap.exch(a, 1, k--);
			sink(a, 1, k);
		}
	}
	
	private static void swim(Comparable[] a, int k) {
		while (k > 1 && less(a, k/2, k)) {
			exch(a, k/2, k);
			k = k/2;
		}
	}
	
	private static void sink(Comparable[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2 * k;
			if (j < N && less(a, j, j+1)) j++;
			if (!less(a, k, j)) break;
			exch(a, k, j);
			k = j;
		}
	}
	
	private static boolean less(Comparable[] a, int i, int j) {
		return a[i-1].compareTo(a[j-1]) < 0;
	}
	
	private static void exch(Comparable[] a, int i, int j) {
		Comparable swap = a[i-1];
		a[i-1] = a[j-1];
		a[j-1] = swap;
	}
	
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i] +"\t ");
		StdOut.println();
	}
	
	private static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++) 
			if (less(a, i, i-1)) return false;
		return true;
	}
	
	public static void main(String[] args) {
		//String[] a = StdIn.readAllStrings();
		String[]a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		sort(a);
		assert isSorted(a);
		show(a);
	}
	
}

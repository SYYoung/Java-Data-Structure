import edu.princeton.cs.algs4.StdIn;

public class Shell extends BaseSort{
	public static void sort(Comparable[] a) {
		int N = a.length;
		int h = 1;
		while (h < N/3) h = 3 * h + 1;
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j-h]); j-=h)
					exch(a, j, j-h);
				//show(a);
			}
			h = h/3;
		}
		
	}
	
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}

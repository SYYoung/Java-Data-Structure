import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class MaxPQ<Key extends Comparable<Key>> {

    // pq[0] is not used. it starts with index 1
    private Key[] pq;
    private int N;

    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key x) {
        pq[++N] = x;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while ((2 * k) < N) {
            int j = 2 * k;
            if ((j < N) && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {
        int N = 15;
        MaxPQ myPQ = new MaxPQ(N + 1);
        int testcase = 2;
        if (testcase == 1) {
            while (!StdIn.isEmpty()) {
                String s = StdIn.readString();
                myPQ.insert(s);
            }
        } else if (testcase == 2) {
            // generate a list of random number
            int[] a = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                a[i] = StdRandom.uniform(1, 100);
                myPQ.insert(a[i]);
            }
            StdOut.println("Input: ");
            for (int i = 1; i <= N; i++)
                StdOut.print("\t" + a[i]);

            StdOut.println("via deMax: ");
            for (int i = 1; i <= N; i++)
                StdOut.print("\t" + myPQ.delMax());
        }

    }
}

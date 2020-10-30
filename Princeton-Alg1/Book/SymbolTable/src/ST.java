import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ST<Key extends Comparable<Key>, Value> {
	public ST() {
		
	}
	
	public void put(Key key, Value val) {
		if (val == null) {
			delete(key);
			return;
		}
	}
	
	public Value get(Key key) {
		return null;
	}
	
	public void delete(Key key) {
		// put(key, null);
	}
	
	public boolean contains(Key key) {
		return get(key) != null;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return 0;
	}
	
	public Iterable<Key> keys() {
		// return Iterable<Key>
		return keys(min(), max());
	}
	
	public Iterable<Key> keys(Key lo, Key hi) {
		// return Iterable<Key>
		return null;
	}
	
	public Key min() {
		return null;
	}
	
	public Key max() {
		return null;
	}
	
	public Key floor(Key key) {
		return null;
	}

	public Key ceiling(Key key) {
		return null;
	}

	// number of keys less than key
	public int rank(Key key) {
		return 0;
	}

	// key of rank k
	public Key select(Key key) {
		return null;
	}

	public void deleteMin() {
		delete(min());
	}
	
	public void deleteMax() {
		delete(max());
	}
	
	public int size(Key lo, Key hi) {
		if (hi.compareTo(lo) < 0)
			return 0;
		else if (contains(hi))
			return rank(hi) - rank(lo) + 1;
		else
			return rank(hi) - rank(lo);
	}
	
	public static void main(String[] args) {
		ST<String, Integer> st;
		st = new ST<String, Integer>();
		
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}
		
		for (String s : st.keys()) 
			StdOut.println(s + " " + st.get(s));
	}
}

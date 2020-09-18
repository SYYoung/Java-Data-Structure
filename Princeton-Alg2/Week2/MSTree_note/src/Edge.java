import edu.princeton.cs.algs4.*;

public class Edge implements Comparable<Edge> {

	private final int v, w;
	private final double weight;
	
	public Edge(int v, int w, double weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	public int either() {
		return v;
	}
	
	public int other(int vertex) {
		if (v == vertex) return w;
		else return v;
	}
	
	public int compareTo(Edge that) {
		if (this.weight < that.weight) return -1;
		else if (this.weight > that.weight) return 1;
		else return 0;
	}
	
	public double weight() {
		return weight;
	}
	
	public String toString() {
		String s = this.v + "--" + this.w + "  " + this.weight;
		return s;
	}
		
}

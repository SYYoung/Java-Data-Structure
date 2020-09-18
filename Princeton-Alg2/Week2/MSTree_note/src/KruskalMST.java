import edu.princeton.cs.algs4.*;

public class KruskalMST {

	private Queue<Edge> mst = new Queue<Edge>();
	private double totalWeight;
	
	public KruskalMST(EdgeWeightedGraph G) {
		MinPQ<Edge> pq = new MinPQ<Edge>();
		for (Edge e: G.edges())
			pq.insert(e);
		
		UF uf = new UF(G.V());
		double totalWt = 0;
		while (!pq.isEmpty() && mst.size() < G.V()-1) {
			Edge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if (uf.find(v) != uf.find(w)) {
				uf.union(v, w);
				mst.enqueue(e);
				totalWt += e.weight();
			}
		}
		totalWeight = totalWt;
	}
	
	public Iterable<Edge> edges() {
		return mst;
	}
	
	public double weight() {
		return totalWeight;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedGraph G = new EdgeWeightedGraph(in);
		KruskalMST mst = new KruskalMST(G);
		for (Edge e: mst.edges())
			StdOut.println(e);
		StdOut.print("%.2f\n" + mst.weight());
	}
}

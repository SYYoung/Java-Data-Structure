import edu.princeton.cs.algs4.*;

public class LazyPrimMST {

	private boolean[] marked;
	private Queue<Edge> mst = new Queue<Edge>();
	private double totalWeight;
	
	public LazyPrimMST(EdgeWeightedGraph G) {
		MinPQ<Edge> pq = new MinPQ<Edge>();
		mst = new Queue<Edge>();
		marked = new boolean[G.V()];
		double totalWt = 0;
		visit(G, 0, pq);
		
		while (!pq.isEmpty() && mst.size() < G.V()-1) {
			Edge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if (marked[v] && marked[w]) continue;
			mst.enqueue(e);
			totalWt += e.weight();
			if (!marked[v])
				visit(G, v, pq);
			if (!marked[w])
				visit(G, w, pq);
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
	
	private void visit(EdgeWeightedGraph G, int v, MinPQ<Edge> pq) {
		marked[v] = true;
		for (Edge e: G.adj(v))
			if (!marked[e.other(v)])
				pq.insert(e);
	}
}

import edu.princeton.cs.algs4.*;

public class EdgeWeightedGraph {
	private final int V;
	private final int E;
	private final Bag<Edge>[] adj;
	
	public EdgeWeightedGraph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Bag<Edge>[])new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Edge>();
	}
	
	public EdgeWeightedGraph(In in) {
		this.V = in.readInt();
		adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
        this.E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge newEdge = new Edge(v, w, weight);
            addEdge(newEdge);
        }
        
	}
	
	public void addEdge(Edge e) {
		int v = e.either();
		int w = e.other(v);
		adj[v].add(e);
		adj[w].add(e);
	}
	
	public Iterable<Edge> adj(int v) {
		return adj[v];
	}
	
	
	public Iterable<Edge> edges() {
		Bag<Edge> edgeList = new Bag<Edge>();
		for (int i = 0; i < V; i++)
			for (Edge e : adj[i])
				edgeList.add(e);
		return edgeList;
	}
	
	
	public int V() {
		return V;
	}
	
	public int E() {
		return E;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < V; i++)
			for (Edge e : adj[i])
				s.append(e);
		return s.toString();	
	}
}

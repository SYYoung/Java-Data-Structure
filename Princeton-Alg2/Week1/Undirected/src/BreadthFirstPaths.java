import edu.princeton.cs.algs4.*;

public class BreadthFirstPaths {
	private boolean[] marked;
	private int[] edgeTo;
	private int[] dist;
	private int s;
	
	public BreadthFirstPaths(Graph G, int vertex) {
		this.s = vertex;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		dist = new int[G.V()];
		bfs(G, vertex);
	}
	
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v)) 
			return null;
		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x!=s; x = edgeTo[x]) 
			path.push(x);
		path.push(s);
		return path;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph G = new Graph(in);
		int s =  0;
		BreadthFirstPaths paths = new BreadthFirstPaths(G, s);
		StdOut.println("Path from: " +s);
		for (int v = 0; v<G.V(); v++)
			if (paths.hasPathTo(v))
				StdOut.println(v + "\t" +paths.edgeTo[v] + "\t" 
							+paths.dist[v]);
	}
	
	private void bfs(Graph G, int vertex) {
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(vertex);
		marked[vertex] = true;
		dist[vertex] = 0;
		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					q.enqueue(w);
					marked[w] = true;
					edgeTo[w] = v;
					dist[w] = dist[v] + 1;
				}
			}
		}
	}
}

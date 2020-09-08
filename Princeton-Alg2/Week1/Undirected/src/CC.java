import edu.princeton.cs.algs4.*;

public class CC {
	private boolean[] marked;
	private int[] id;
	private int count;
	
	public CC(Graph G) {
		marked = new boolean[G.V()];
		for (int v=0; v<G.V(); v++)
			marked[v] = false;
		id = new int[G.V()];
		count = 0;
		for (int v=0; v<G.V(); v++) {
			if (!marked[v]) {
				dfs(G, v);
				count++;
			}
		}
	}
	
	public boolean connected(int v, int w) {
		return id[v] == id[w];
	}
	
	public int count() {
		return count;
	}
	
	public int id(int v) {
		return id[v];
	}
	
	private void dfs(Graph G, int v) {
		id[v] = count;
		for (int w : G.adj(v))
			if (!marked[w]) {
				marked[w] = true;
				dfs(G, w);
			}	
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph G = new Graph(in);
		CC cc = new CC(G);
		for (int v=0; v<G.V(); v++)
			StdOut.println(v +"\t" +cc.id[v]);
	}
}

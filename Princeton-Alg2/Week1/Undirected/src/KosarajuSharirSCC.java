import edu.princeton.cs.algs4.*;

public class KosarajuSharirSCC {

	private boolean[] marked;
	private int[] id;
	private int count;
	
	public KosarajuSharirSCC(Digraph G) {
		marked = new boolean[G.V()];
		for (int v=0; v<G.V(); v++)
			marked[v] = false;
		id = new int[G.V()];
		DepthFirstOrder dfs1 = new DepthFirstOrder(G.reverse());
		count = 0;
		for (int v: dfs1.reversePost()) {
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
	
	private void dfs(Digraph G, int v) {
		id[v] = count;
		for (int w : G.adj(v))
			if (!marked[w]) {
				marked[w] = true;
				dfs(G, w);
			}	
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		KosarajuSharirSCC cc = new KosarajuSharirSCC(G);
		for (int v=0; v<G.V(); v++)
			StdOut.println(v +"\t" +cc.id[v]);
	}
}

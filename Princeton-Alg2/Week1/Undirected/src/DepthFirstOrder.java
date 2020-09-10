import edu.princeton.cs.algs4.*;

public class DepthFirstOrder {

	private boolean[] marked;
	private Stack<Integer> reversePost;
	
	public DepthFirstOrder(Digraph G) {
		reversePost = new Stack<Integer>();
		marked = new boolean[G.V()];
		for (int v=0; v<G.V(); v++) {
			if (!marked[v])
				dfs(G, v);
		}
	}
	
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	public Iterable<Integer> reversePost() {
		return reversePost;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		int s =  0;
		DepthFirstOrder paths = new DepthFirstOrder(G);
		for (int i: paths.reversePost())
			StdOut.print("\t" +i);
	}
	
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v))
			if (!marked[w]) {
				dfs(G, w);
			}
		reversePost.push(v);;
	}
	
}

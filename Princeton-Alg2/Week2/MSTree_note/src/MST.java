import edu.princeton.cs.algs4.*;

public class MST {
		private double weight;
		Queue<Edge> edge;

		public MST(EdgeWeightedGraph G) {
			
		}
		
		public Iterable<Edge> edges() {
			return edge;
		}
		
		public double weight() {
			return weight;
		}
		
		public static void main(String[] args) {
			In in = new In(args[0]);
			EdgeWeightedGraph G = new EdgeWeightedGraph(in);
			MST mst = new MST(G);
			for (Edge e: mst.edges())
				StdOut.println(e);
			StdOut.printf("%.2f\n" + mst.weight());
		}
}

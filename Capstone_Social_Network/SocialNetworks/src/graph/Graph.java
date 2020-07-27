package graph;

import java.util.List;

public interface Graph {
	/* Creates a vertex with the given number. */
	public void addVertex(int num);
	
	public void addEdge(int from, int to);
	
	public Graph getEgonet(int center);
	
	public List<Graph> getSCCs();
}

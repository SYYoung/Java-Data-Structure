package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface Graph {
	/* Creates a vertex with the given number. */
	public void addVertex(int num);
	
	public void addEdge(int from, int to);
	
	public HashMap<Integer, HashSet<Integer>> exportGraph();
	
	/* Finds the egonet centered at a given node. */
	public Graph getEgonet(int center);
	
	/* returns all strongly connected components in a directed graph. */
	public List<Graph> getSCCs();
	
	public void printGraph();
}

package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import util.GraphLoader;

/**
 * @author Sudharaka Palamakumbura
 * 
 * Implements a Graph. This class uses an adjacency list to represent
 * the Graph's edges and vertices. Moreover it carries a list of 
 * Strongly Connected Components of the graph.
 *
 */
public class CapGraph implements Graph {
	
	private int size;

	private Map<Integer, HashSet<Integer>> adjList = new HashMap<Integer, HashSet<Integer>>();
	private List<CapGraph> stronglyConnected = new ArrayList<CapGraph>();
	
	/**
	 * @return the size of the graph
	 */
	public int getSize() {
		return size;
	}
	
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		adjList.put(num, new HashSet<Integer>());
		size++;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		if (this.exportGraph().containsKey(from) && this.exportGraph().containsKey(to))
			adjList.get(from).add(to);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		
		// If "center" is not contained in graph return empty graph
		if (!adjList.containsKey(center)) 
			return new CapGraph();
		
		CapGraph egonet = new CapGraph();
		egonet.addVertex(center);
		
		for (Integer vertex: adjList.get(center))
			egonet.addVertex(vertex);
		
		for (Integer from: egonet.adjList.keySet())
			for (Integer to: egonet.adjList.keySet())
				if (adjList.get(from).contains(to))
					egonet.addEdge(from, to);
	
		return egonet;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs(): Returns all Strongly Connected Components (SSCs) 
	 * of the graph.
	 */
	@Override
	public List<Graph> getSCCs() {

		Stack<Integer> finished = DFS(this.vertices(), false);
		
		CapGraph transpose = this.graphTanspose();
		
		transpose.DFS(finished, true);
		
		for (CapGraph graph: transpose.stronglyConnected)
			this.stronglyConnected.add(graph.graphTanspose());
		
		List<Graph> stronglyConnectedGraphs = new ArrayList<Graph>();
		
		for (CapGraph graph: stronglyConnected)
			stronglyConnectedGraphs.add(graph);
		
		return stronglyConnectedGraphs;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		return (HashMap<Integer, HashSet<Integer>>)adjList;
	}
	
	/**
	 * This is a helper method for the getSSC method
	 * 
	 * @param vertices the vertices of the graph as a Stack
	 * @param sccList whether or not to add to the list of SCCs
	 * @strongCon if this parameter is "true" then graphs (strongly connected components)
	 * formed by the vertices are stored in "stronglyConnected"
	 * @return Stack<Integer> 
	 */
	public Stack<Integer> DFS(Stack<Integer> vertices, boolean strongCon){
		HashSet<Integer> visited = new HashSet<Integer>();
		Stack<Integer> finished = new Stack<Integer>();
		
		while (!vertices.isEmpty()){
			Integer v = vertices.pop();
			
			if (!visited.contains(v)){
				DFSVisit(v, visited, finished);
				
				if (strongCon)
				    stronglyConnected(finished);
			}
		}
		return finished;
	}
	
	/**
	 * This is a helper method of BFS
	 * 
	 * @param vertex
	 * @param visited 
	 * @param finished
	 */
	public void DFSVisit(Integer vertex, HashSet<Integer> visited, Stack<Integer> finished){
		
		visited.add(vertex);
		
		for (Integer n: exportGraph().get(vertex))
			if (!visited.contains(n))
				DFSVisit(n, visited, finished);
		
		finished.push(vertex);
	}
	
	
	/**
	 * Helper method of the DFS method. This method constructs the strongly
	 * connected components of the graph and stores them in the stronglyConnected
	 * field.
	 *  
	 * @param finished 
	 */
	public void stronglyConnected(Stack<Integer> finished){
		
		// piece together the strongly connected components
		CapGraph graph = new CapGraph();
		List<Integer> vertices = new ArrayList<Integer>();
		
		int sizeSCG = stronglyConnected.size();
		
		int i = 0, j = 0;
		while (j < sizeSCG){
			i += stronglyConnected.get(j).getSize();
			j++;
		}
		
		while (i < finished.size()){
			vertices.add(finished.get(i));
			i++;
		}
		
		// add all the vertices of the strongly connected components
		for (Integer vertex: vertices)
			graph.addVertex(vertex);
		
		// add all the edges of the strongly connected components
		for (Integer vertex: vertices)
			for (Integer a: adjList.get(vertex))
				graph.addEdge(vertex, a);
		
		stronglyConnected.add(graph);
	}
	
	
	/**
	 * @return Returns the vertices of a graph
	 */
	public Stack<Integer> vertices(){
		Stack<Integer> vertices = new Stack<Integer>();
		
		// creating the list of vertices
		for (Integer vertex: adjList.keySet()){
			vertices.push(vertex);
		}
		
		return vertices;
	}
	
	/**
	 * This method returns the transpose of the graph
	 * 
	 * @param graph
	 * @return transpose of graph
	 */
	public CapGraph graphTanspose(){
		
		CapGraph transpose = new CapGraph();
		
		// copy over the vertices
		for (Integer vertex: adjList.keySet())
			transpose.addVertex(vertex);
		
		//copy the transpose of edges
		for (Integer vertex: adjList.keySet()){
			HashSet<Integer> edgeSet = adjList.get(vertex);
			for (Integer v: edgeSet)
				transpose.addEdge(v, vertex);
		}
		return transpose;
	}
	
	public static void main(String[] args){
		CapGraph testGraph = new CapGraph();
		GraphLoader.loadGraph(testGraph, "data/facebook_1000.txt");
		System.out.println(testGraph.exportGraph());
	}



}

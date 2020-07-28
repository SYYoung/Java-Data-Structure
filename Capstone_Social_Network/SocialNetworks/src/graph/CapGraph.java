package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import util.GraphLoader;

public class CapGraph implements Graph {
	HashMap<Integer, HashSet<Integer>> nodeMap;
	int numVertex;
	
	public CapGraph() {
		nodeMap = new HashMap<Integer, HashSet<Integer>>();
		numVertex = 0;
	}
	
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!nodeMap.containsKey(num)) 
		{
			nodeMap.put(num, new HashSet<Integer>());
			numVertex++;
		}
	}

	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (nodeMap.containsKey(from) && (nodeMap.containsKey(to))) 
		{
			nodeMap.get(from).add(to);
		}
	}

	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		CapGraph newGraph = new CapGraph();
		// 1. get the edge list of the center node
		HashSet<Integer> edgeList = nodeMap.get(center);
		if (edgeList == null)
			return newGraph; // the center is not a valid node
		
		newGraph.addVertex(center);
		for (int outNode : edgeList) 
			newGraph.addVertex(outNode);

		// 2. for each new node, add the corresponding edges inside this subgraph
		for (int from : newGraph.nodeMap.keySet()) {
			for (int to : newGraph.nodeMap.keySet()) {
				if (nodeMap.get(from).contains(to)) {
					newGraph.addEdge(from, to);
				}
			}
		}
		return newGraph;
	}

	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		// 1. perform DFS with the original graph
		Stack finished = DFS(getVertices(), false);
		CapGraph transposeGraph = graphTranspose();
		Stack reverseFinish = DFS(finished, true);
		return null;
	}
	
	public HashMap<Integer, HashSet<Integer>> exportGraph()
	{
		return new HashMap<Integer, HashSet<Integer>>(this.nodeMap);
	}
	
	public void printGraph() {
		for (int num : nodeMap.keySet()) {
			System.out.print(num +",\t:{" );
			for (int i: nodeMap.get(num))
				System.out.print(i +",\t");
			System.out.println("}");
		}
		
	}
	
	public Stack DFS(Stack vertices, boolean scc) {
		HashSet<Integer> visited = new HashSet<Integer>();
		Stack finished = new Stack();
		
		//initial set visited and stack finished
		while (!vertices.empty()) {
			int v = (int)vertices.pop();
			System.out.println("poped node: " +v);
			if (!visited.contains(v))
				DFS_Visit(v, visited, finished);
			System.out.println("DFS finished: finished = " +finished);
		}
		return finished;
	}

	private void DFS_Visit(int v, HashSet<Integer> visited, Stack finished) {
		// TODO Auto-generated method stub
		visited.add(v);
		System.out.println("Inside DFS_Visited, visited node: " +v);
		for (int n : nodeMap.get(v)) {
			if (!visited.contains(n))
				DFS_Visit(n, visited, finished);
		}
		finished.push(v);
	}
	
	private List<Integer> getNeighbor(int v) {
		return (List<Integer>) nodeMap.get(v);
	}
	
	public Stack getVertices() {
		Stack vertices = new Stack();
		for (int i : nodeMap.keySet())
			vertices.push(i);
		return vertices;
	}
	
	private CapGraph graphTranspose() {
		CapGraph newGraph = new CapGraph();
		
		for (int i : nodeMap.keySet())
			newGraph.addVertex(i);
		for (int from : nodeMap.keySet()) {
			HashSet<Integer> edge = nodeMap.get(from);
			for (int to : edge)
				newGraph.addEdge(to, from);
		}
		return newGraph;
	}
	
	public static void main(String[] args){
		CapGraph testGraph = new CapGraph();
		GraphLoader.loadGraph(testGraph, "data/note_graph5.txt");
		testGraph.printGraph();
		
		/* test DFS */
		Stack finished = testGraph.DFS(testGraph.getVertices(), false);
		System.out.println("test DFS.");
		System.out.println(finished);
		CapGraph newGraph = testGraph.graphTranspose();
		newGraph.printGraph();
	}

}



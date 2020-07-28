package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
		for (int outNode : edgeList) {
			newGraph.addVertex(outNode);
			newGraph.addEdge(center, outNode);
		}
		HashSet<Integer> newNodeList = new HashSet<Integer>(edgeList);
		newNodeList.add(center);
		
		// 2. for each new node, add the corresponding edges inside this subgraph
		for (int outnode : edgeList) {
			HashSet<Integer> curEdgeList = nodeMap.get(outnode);
			for (int node : curEdgeList) {
				if (newNodeList.contains(node)) {
					newGraph.addEdge(outnode, node);
				}
			}
		}
		return newGraph;
	}

	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
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

}



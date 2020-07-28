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
		return null;
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



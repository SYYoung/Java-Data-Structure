package graph;

public class CapGraphVerify {

	private static void main(String[] args) {
		CapGraph testGraph = new CapGraph();
		
		testGraph.addVertex(32);
		testGraph.addVertex(50);
		testGraph.addVertex(44);
		testGraph.addVertex(25);
		testGraph.addVertex(23);
		testGraph.addVertex(65);
		testGraph.addVertex(18);
		
		testGraph.addEdge(32, 50);
		testGraph.addEdge(32, 44);
		testGraph.addEdge(44, 50);
		
		testGraph.addEdge(25, 23);
		testGraph.addEdge(25, 65);
		testGraph.addEdge(25, 18);
		
		testGraph.addEdge(65, 23);
		testGraph.addEdge(44, 18);
		testGraph.addEdge(18, 23);
		testGraph.addEdge(23, 25);
		testGraph.addEdge(23, 18);
		
		testGraph.printGraph();
 
	}
	

}

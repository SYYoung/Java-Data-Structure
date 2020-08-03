import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
	int[][] grid;
	QuickUnionUF myUF;
	int HEAD = 0; // refers the node 0 which connects the top row
	int TAIL;
	
	public Percolation(int N, double prob)
	{
		grid = new int[N][N];
		// node 0 is head node which connects top row, node N^2+1 connects
		// the bottom row
		myUF = new QuickUnionUF(N*N+2); 
		TAIL = N*N+1; //refers the last node which connects the bottom row
		
		createPercolate(prob);
	}

	private void createPercolate(double prob) {
		// TODO Auto-generated method stub
		// 1. for top row, i.e. node 1 to node N: connet to HEAD. 
		// if the node is open, connect to adj nodes
		// 2. for bottom row, i.e. row N*(N-1)+1 to N*N
	}
}

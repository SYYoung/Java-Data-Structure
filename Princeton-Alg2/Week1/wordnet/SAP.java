/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if ((!isValidVertex(v)) || (!isValidVertex(w)))
            throw new IllegalArgumentException();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if ((v == null) || (w == null))
            throw new IllegalArgumentException();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if ((v == null) || (w == null))
            throw new IllegalArgumentException();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String fname = "digraph1.txt";
        In in = new In(fname);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private boolean isValidVertex(int node) {
        int numVertices = G.V();
        if ((node < 0) || (node >= numVertices))
            return false;
        else
            return true;
    }
}

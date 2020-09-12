/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SAP {
    private Digraph graph;
    private HashMap<Integer, BreadthFirstDirectedPaths> nodeBFS;
    private HashMap<Integer, AncestorCache> nodeAncestor;
    private static final int invalidAncestor = -1;
    private static final int invalidLength = -1;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        graph = new Digraph(G);
        nodeBFS = new HashMap<Integer, BreadthFirstDirectedPaths>();
        nodeAncestor = new HashMap<Integer, AncestorCache>();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if ((!isValidVertex(v)) || (!isValidVertex(w)))
            throw new IllegalArgumentException();
        if (nodeAncestor.containsKey(v))
            if (nodeAncestor.get(v).node2 == w)
                return nodeAncestor.get(v).minDist;
        if (nodeAncestor.containsKey(w))
            if (nodeAncestor.get(w).node2 == v)
                return nodeAncestor.get(w).minDist;
        AncestorCache newNode = findAncestor(v, w);
        return newNode.minDist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (nodeAncestor.containsKey(v))
            if (nodeAncestor.get(v).node2 == w)
                return nodeAncestor.get(v).commonAncestor;
        if (nodeAncestor.containsKey(w))
            if (nodeAncestor.get(w).node2 == v)
                return nodeAncestor.get(w).commonAncestor;
        AncestorCache newNode = findAncestor(v, w);
        return newNode.commonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if ((v == null) || (w == null))
            throw new IllegalArgumentException();
        // check if every argument in v and in w are valid or not
        for (Integer v1 : v)
            if ((v1 == null) || (!isValidVertex(v1)))
                throw new IllegalArgumentException();
        for (Integer w1 : w)
            if ((w1 == null) || (!isValidVertex(w1)))
                throw new IllegalArgumentException();
        int common = invalidAncestor;
        int distSoFar = Integer.MAX_VALUE;
        for (int v1 : v) {
            for (int w1 : w) {
                int dist = length(v1, w1);
                if ((dist != invalidLength) && (dist < distSoFar)) {
                    common = ancestor(v1, w1);
                    distSoFar = dist;
                }
            }
        }
        if (common != invalidAncestor)
            return distSoFar;
        else
            return invalidLength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if ((v == null) || (w == null))
            throw new IllegalArgumentException();
        // check if every argument in v and in w are valid or not
        for (Integer v1 : v)
            if ((v1 == null) || (!isValidVertex(v1)))
                throw new IllegalArgumentException();
        for (Integer w1 : w)
            if ((w1 == null) || (!isValidVertex(w1)))
                throw new IllegalArgumentException();
        int common = invalidAncestor;
        int distSoFar = Integer.MAX_VALUE;
        for (int v1 : v) {
            for (int w1 : w) {
                int dist = length(v1, w1);
                if ((dist != invalidLength) && (dist < distSoFar)) {
                    common = ancestor(v1, w1);
                    distSoFar = dist;
                }
            }
        }
        if (common != invalidAncestor)
            return common;
        else
            return invalidAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        int test = 4; // test indiv length and ancestor

        if (test == 1) {
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
        if (test == 2) {
            // test group of nodes
            String fname = "digraph1.txt";
            In in = new In(fname);
            Digraph G = new Digraph(in);
            SAP sap = new SAP(G);
            Integer[] a1 = { 4, 6, -1 };
            Integer[] b1 = { 6, 16, 17 };
            List<Integer> a = Arrays.asList(a1);
            List<Integer> b = Arrays.asList(b1);
            int length = sap.length(a, b);
            int ancestor = sap.ancestor(a, b);
            StdOut.println("set A: " + a);
            StdOut.println("set B: " + b);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        if (test == 3) {
            String fname = "digraph-wordnet.txt";
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
        if (test == 4) {
            // test group of nodes
            String fname = "digraph-wordnet.txt";
            In in = new In(fname);
            Digraph G = new Digraph(in);
            SAP sap = new SAP(G);
            int total = 0;
            while ((!StdIn.isEmpty()) && (total < 10)) {
                // get 2 sets of 5 vertices randomly
                Integer[] a1 = new Integer[5];
                Integer[] b1 = new Integer[5];
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = StdRandom.uniform(G.V() / 2);
                    b1[i] = StdRandom.uniform(G.V() / 2, G.V());
                }
                List<Integer> a = Arrays.asList(a1);
                List<Integer> b = Arrays.asList(b1);
                int length = sap.length(a, b);
                int ancestor = sap.ancestor(a, b);
                StdOut.println("set A: " + a);
                StdOut.println("set B: " + b);
                StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
                // print the keys of nodeBFS
                StdOut.println("The keys of nodeBFS: ");
                StdOut.println(sap.nodeBFS.keySet());

                total++;
            }
        }
    }

    private boolean isValidVertex(int node) {
        int numVertices = graph.V();
        if ((node < 0) || (node >= numVertices))
            return false;
        else
            return true;
    }

    private AncestorCache findAncestor(int v, int w) {
        BreadthFirstDirectedPaths vPath, wPath;
        if (!nodeBFS.containsKey(v)) {
            vPath = new BreadthFirstDirectedPaths(graph, v);
            nodeBFS.put(v, vPath);
        }
        else
            vPath = nodeBFS.get(v);
        if (!nodeBFS.containsKey(w)) {
            wPath = new BreadthFirstDirectedPaths(graph, w);
            nodeBFS.put(w, wPath);
        }
        else
            wPath = nodeBFS.get(w);

        // for each vertex
        int common = invalidAncestor;
        int distSoFar = Integer.MAX_VALUE;
        for (int vertex = 0; vertex < graph.V(); vertex++) {
            int vDist = vPath.distTo(vertex);
            int wDist = wPath.distTo(vertex);
            if ((vDist < distSoFar) && (wDist < distSoFar)) {
                if (vDist + wDist < distSoFar) {
                    distSoFar = vDist + wDist;
                    common = vertex;
                }
            }
        }
        AncestorCache vCache, wCache;
        if (common == invalidAncestor) {
            vCache = new AncestorCache(v, w, invalidAncestor, invalidLength);
            wCache = new AncestorCache(w, v, invalidAncestor, invalidLength);
        }
        else {
            vCache = new AncestorCache(v, w, common, distSoFar);
            wCache = new AncestorCache(w, v, common, distSoFar);
        }
        nodeAncestor.put(v, vCache);
        nodeAncestor.put(w, wCache);

        return vCache;
    }

    private class AncestorCache {
        int node1;
        int node2;
        int commonAncestor;
        int minDist;

        public AncestorCache(int n1, int n2, int cNode, int dist) {
            node1 = n1;
            node2 = n2;
            commonAncestor = cNode;
            minDist = dist;
        }
    }
}

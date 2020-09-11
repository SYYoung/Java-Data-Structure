/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashSet;

public class WordNet {
    private RedBlackBST<String, ArrayList<Integer>> synTree;
    private Digraph hyperG;
    private SAP mySap;
    private int totalVertex;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if ((synsets == null) || (hypernyms == null))
            throw new IllegalArgumentException();

        // if the input to the constructor does not correspond to a rooted DAG,
        // throw exception
        totalVertex = readSynset(synsets);
        int totalChildNode = readHypernyms(hypernyms);
        // if the number of node which does not have parent is not one, reject
        if (totalVertex - totalChildNode != 1)
            throw new IllegalArgumentException();
        // check if the graph is a rooted DAG
        if (isDAG(hyperG))
            throw new IllegalArgumentException();

        // build SAP
        mySap = new SAP(hyperG);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synTree.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return synTree.contains(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        if ((nounA == null) || (nounB == null)
                || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        // get the lists of nodes which contain nounA and nounB respectively
        ArrayList<Integer> setA = synTree.get(nounA);
        ArrayList<Integer> setB = synTree.get(nounB);
        int dist = mySap.length(setA, setB);
        return dist;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        if ((nounA == null) || (nounB == null) ||
                !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        // get the lists of nodes which contain nounA and nounB respectively
        ArrayList<Integer> setA = synTree.get(nounA);
        ArrayList<Integer> setB = synTree.get(nounB);
        int common = mySap.ancestor(setA, setB);
        // find the corresponding string
        String nodeName = "";
        for (String key : synTree.keys()) {
            if (synTree.get(key).contains(common)) {
                nodeName = key;
                break;
            }
        }
        return nodeName;
    }

    public static void main(String[] args) {
        int test = 2;
        String fname1 = "", fname2 = "";
        if (test == 1) {
            fname1 = "synsets15.txt";
            fname2 = "hypernyms15Tree.txt";
        }
        else if (test == 2) {
            fname1 = "synsets.txt";
            fname2 = "hypernyms.txt";
        }
        else if (test == 3) {
            fname1 = "synsets3.txt";
            fname2 = "hypernyms3InvalidTwoRoots.txt";
        }

        WordNet myWordNet = new WordNet(fname1, fname2);
        StdOut.println("all nouns read:");
        // test nouns()
        if (test == 1) {
            for (String nodeName : myWordNet.synTree.keys())
                StdOut.println(nodeName + ",\t" + myWordNet.synTree.get(nodeName));
            for (String nodeName : myWordNet.nouns())
                StdOut.println(nodeName);
            StdOut.println("Edge information:");
            StdOut.println(myWordNet.hyperG);
            // test isNoun()
            String word = "w";
            StdOut.println("Is word: " + word + " in the list? " + myWordNet.isNoun(word));
        }

        // test distance and sap
        if (test == 2) {
            String noun1 = "worm";
            String noun2 = "bird";
            int len = myWordNet.distance(noun1, noun2);
            String commonNode = myWordNet.sap(noun1, noun2);
            StdOut.println("The two nouns: " + noun1 + "," + noun2);
            StdOut.println("length = " + len + ", ");
            StdOut.println("common node : " + commonNode);
        }
    }

    private int readSynset(String synset) {
        // HashSet<Integer> nodeSet = new HashSet<Integer>();
        synTree = new RedBlackBST<String, ArrayList<Integer>>();
        In in = new In(synset);
        int totalNode = 0;
        while (!in.isEmpty()) {
            String[] words = in.readLine().split(",");
            int nodeNum = Integer.parseInt(words[0]);
            String nodeName = words[1];
            if (synTree.contains(nodeName)) {
                ArrayList<Integer> curList = synTree.get(nodeName);
                curList.add(nodeNum);
                synTree.put(words[1], curList);
            }
            else {
                ArrayList<Integer> newList = new ArrayList<Integer>();
                newList.add(nodeNum);
                synTree.put(words[1], newList);
            }
            // nodeSet.add(nodeNum);
            totalNode++;
        }
        return totalNode;
    }

    private int readHypernyms(String hypernyms) {
        HashSet<Integer> childSet = new HashSet<Integer>();
        hyperG = new Digraph(totalVertex);
        In in = new In(hypernyms);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] words = line.split(",");
            int child = Integer.parseInt(words[0]);
            for (int i = 1; i < words.length; i++) {
                int parent = Integer.parseInt(words[i]);
                hyperG.addEdge(child, parent);
            }
            if (words.length > 1)
                childSet.add(child);
        }
        return childSet.size();
    }

    private boolean isDAG(Digraph graph) {
        DirectedCycle dc = new DirectedCycle(graph);
        return dc.hasCycle();
    }

}

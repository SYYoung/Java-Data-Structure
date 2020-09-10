/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    private RedBlackBST<String, Integer> synTree;
    private Digraph hyperG;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if ((synsets == null) || (hypernyms == null))
            throw new IllegalArgumentException();

        // if the input to the constructor does not correspond to a rooted DAG,
        // throw exception
        readSynset(synsets);
        readHypernyms(hypernyms);
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
        return 0;
    }

    // a synset (second field of synsets.txt) that is the commonancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        if ((nounA == null) || (nounB == null) ||
                !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return null;
    }

    public static void main(String[] args) {
        String fname1 = "synsets15.txt";
        String fname2 = "hypernyms15Path.txt";
        WordNet myWordNet = new WordNet(fname1, fname2);
        StdOut.println("all nouns read:");
        // test nouns()
        for (String nodeName : myWordNet.synTree.keys())
            StdOut.println(nodeName + ",\t" + myWordNet.synTree.get(nodeName));
        for (String nodeName : myWordNet.nouns())
            StdOut.println(nodeName);
        StdOut.println("Edge information:");
        for (String nodeName : myWordNet.synTree.keys()) {
            int nodeNum = myWordNet.synTree.get(nodeName);
            StdOut.print(nodeNum + "->\t");
            for (int each : myWordNet.hyperG.adj(nodeNum))
                StdOut.print(each + "->");
            StdOut.println();
        }
        // test isNoun()
        String word = "w";
        StdOut.println("Is word: " + word + " in the list? " + myWordNet.isNoun(word));
    }

    private void readSynset(String synset) {
        synTree = new RedBlackBST<String, Integer>();
        In in = new In(synset);
        while (!in.isEmpty()) {
            String[] words = in.readLine().split(",");
            synTree.put(words[1], Integer.parseInt(words[0]));
        }
    }

    private void readHypernyms(String hypernyms) {
        hyperG = new Digraph(synTree.size());
        In in = new In(hypernyms);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] words = line.split(",");
            int child = Integer.parseInt(words[0]);
            for (int i = 1; i < words.length; i++) {
                int parent = Integer.parseInt(words[i]);
                hyperG.addEdge(child, parent);
            }
        }
    }

}

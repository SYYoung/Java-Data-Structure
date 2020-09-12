/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet myWordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        myWordNet = wordnet;
    }

    // given an array of WordNet nouns,return an outcast
    public String outcast(String[] nouns) {
        // build up the distance matrix
        int nounLen = nouns.length;
        int[][] dist = new int[nounLen][nounLen];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = i + 1; j < nouns.length; j++)
                dist[i][j] = myWordNet.distance(nouns[i], nouns[j]);
        }
        for (int i = 0; i < nounLen; i++)
            dist[i][i] = 0;
        for (int i = 1; i < nounLen; i++)
            for (int j = 0; j < i; j++)
                dist[i][j] = dist[j][i];

        // build the d[] for each node
        int[] d = new int[nounLen];
        for (int i = 0; i < nounLen; i++) {
            for (int j = 0; j < nounLen; j++)
                d[i] += dist[i][j];
        }
        // find out the max index and value
        int whichIndex = 0, whichDist = d[0];
        for (int i = 1; i < nounLen; i++) {
            if (d[i] > whichDist) {
                whichIndex = i;
                whichDist = d[i];
            }
        }
        return nouns[whichIndex];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}

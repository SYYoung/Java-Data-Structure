/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Set;

public class BaseballElimination {
    private TeamRecord[] teamRec;
    private HashMap<String, Integer> name2Node;
    private HashMap<Integer, String> node2Name;
    private int totalTeams;
    private int[][] games;
    private int[][] gameVertex;
    private Bag<Integer>[] certificate;
    private int[] statusUpdate;
    private static final int notYet = -1;
    private int startNode;
    private int endNode;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        readTeamFile(filename);
        certificate = (Bag<Integer>[]) new Bag[totalTeams + 1];
        statusUpdate = new int[totalTeams + 1];
        for (int i = 1; i <= totalTeams; i++) {
            certificate[i] = new Bag<Integer>();
            statusUpdate[i] = notYet;
        }
        startNode = 0;
        endNode = totalTeams + 1;
    }

    // number of teams
    public int numberOfTeams() {
        return totalTeams;
    }

    // all teams
    public Iterable<String> teams() {
        Set<String> names = name2Node.keySet();
        return names;
    }

    // number of wins for given team
    public int wins(String team) {
        if (name2Node.get(team) == null)
            throw new IllegalArgumentException();
        int node = name2Node.get(team);
        return teamRec[node].wins;
    }

    // number of losses for given team
    public int losses(String team) {
        if (name2Node.get(team) == null)
            throw new IllegalArgumentException();
        int node = name2Node.get(team);
        return teamRec[node].losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (name2Node.get(team) == null)
            throw new IllegalArgumentException();
        int node = name2Node.get(team);
        return teamRec[node].remaining;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if ((name2Node.get(team1) == null) || (name2Node.get(team2) == null))
            throw new IllegalArgumentException();
        int node1 = name2Node.get(team1);
        int node2 = name2Node.get(team2);
        return games[node1][node2];
    }

    // is given team eliminated
    public boolean isEliminated(String team) {
        if (name2Node.get(team) == null)
            throw new IllegalArgumentException();
        int teamNode = name2Node.get(team);
        if (statusUpdate[teamNode] == notYet)
            updatedEliminated(teamNode);
        return (certificate[teamNode].size() != 0);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (name2Node.get(team) == null)
            throw new IllegalArgumentException();
        int teamNode = name2Node.get(team);
        if (statusUpdate[teamNode] == notYet) {
            statusUpdate[teamNode] = 1;
            updatedEliminated(teamNode);
        }
        if (certificate[teamNode].size() == 0)
            return null;
        Bag<String> s = new Bag<String>();
        Bag<Integer> ans = certificate[teamNode];
        for (int i : ans) {
            s.add(node2Name.get(i));
        }
        return s;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        // display the record
        int teamExcluded = 3;
        String teamName = "New_York";
        //division.displayTeamRecord();

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = {");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }

    }

    private void readTeamFile(String filename) {
        In in = new In(filename);
        int num = 1;
        while (!in.isEmpty()) {
            totalTeams = in.readInt();
            teamRec = new TeamRecord[totalTeams + 1];
            games = new int[totalTeams + 1][totalTeams + 1];
            gameVertex = new int[totalTeams + 1][totalTeams + 1];
            name2Node = new HashMap<String, Integer>();
            node2Name = new HashMap<Integer, String>();

            for (int k = 1; k <= totalTeams; k++) {
                // each line format: Atlanta       83 71  8  0 1 6 1
                String name = in.readString();
                int wins = in.readInt();
                int losses = in.readInt();
                int remaining = in.readInt();
                teamRec[num] = new TeamRecord(name, wins, losses, remaining);
                // read games
                for (int i = 1; i <= totalTeams; i++) {
                    games[num][i] = in.readInt();
                }
                // build hashmap
                name2Node.put(name, num);
                node2Name.put(num, name);
                num++;
            }
        }
    }

    private void displayTeamRecord() {
        // for testing purpose
        for (int i = 1; i <= numberOfTeams(); i++) {
            String teamName = node2Name.get(i);
            StdOut.println(
                    teamName + "\t" + wins(teamName) + "\t" + losses(teamName) + "\t"
                            + remaining(teamName));
        }
        for (int i = 1; i <= numberOfTeams(); i++) {
            String teamName = node2Name.get(i);
            StdOut.print(teamName + ":");
            for (int j = 1; j <= numberOfTeams(); j++)
                StdOut.print("\t" + games[i][j]);
            StdOut.println();
        }
    }

    private boolean isTrivial(int teamExclude, Bag<Integer> R) {
        int upperBound = teamRec[teamExclude].wins + teamRec[teamExclude].remaining;
        for (int i = 1; i <= numberOfTeams(); i++) {
            if (i == teamExclude) continue;
            if (teamRec[i].wins > upperBound)
                R.add(i);
        }
        return (R.size() > 0);
    }

    private void nonTrivial(int teamExclude, Bag<Integer> R) {
        FlowNetwork baseNetwork = buildFlowNetWork(teamExclude);
        FordFulkerson ff = new FordFulkerson(baseNetwork, startNode, endNode);
        double val = ff.value();
        int totalCapacity = 0;
        for (FlowEdge e : baseNetwork.adj(startNode))
            totalCapacity += e.capacity();
        for (int i = 1; i <= totalTeams; i++) {
            if ((i != teamExclude) && (ff.inCut(i)))
                R.add(i);
        }
    }

    private void updatedEliminated(int teamNode) {
        statusUpdate[teamNode] = 1;
        Bag<Integer> R = new Bag<Integer>();
        if (isTrivial(teamNode, R)) {
            for (int i : R)
                certificate[teamNode].add(i);
        }
        else {
            nonTrivial(teamNode, R);
            if (R.size() > 0)
                for (int i : R)
                    certificate[teamNode].add(i);
        }
    }

    private FlowNetwork buildFlowNetWork(int teamExclude) {
        int s = startNode;
        int t = endNode;
        int totalGameVertex = 0;
        for (int i = 1; i <= totalTeams; i++)
            totalGameVertex += teamRec[i].remaining;
        totalGameVertex = totalGameVertex / 2;
        FlowNetwork baseNetwork = new FlowNetwork(totalGameVertex + 2 + numberOfTeams());
        buildNode();
        // build the edge
        for (int i = 1; i <= totalTeams; i++) {
            if (i == teamExclude) continue;
            for (int j = i + 1; j <= numberOfTeams(); j++) {
                if (j == teamExclude) continue;
                if (games[i][j] == 0) continue;
                FlowEdge e1 = new FlowEdge(s, gameVertex[i][j], games[i][j]);
                baseNetwork.addEdge(e1);
                baseNetwork.addEdge(new FlowEdge(gameVertex[i][j], i, Integer.MAX_VALUE));
                baseNetwork.addEdge(new FlowEdge(gameVertex[i][j], j, Integer.MAX_VALUE));
            }
        }
        int upperBound = teamRec[teamExclude].wins + teamRec[teamExclude].remaining;
        for (int i = 1; i <= numberOfTeams(); i++) {
            if (i == teamExclude) continue;
            int capacity = upperBound - teamRec[i].wins;
            baseNetwork.addEdge(new FlowEdge(i, t, capacity));
        }
        // for testing only
        //StdOut.println("Test the network");
        //StdOut.println(baseNetwork);
        return baseNetwork;
    }

    private void buildNode() {
        // 1 for node s and 1 for node t
        int startIndex = totalTeams + 2;
        for (int i = 1; i <= totalTeams; i++)
            for (int j = i; j <= totalTeams; j++)
                if (games[i][j] > 0)
                    gameVertex[i][j] = startIndex++;
    }

    private class TeamRecord {
        private String name;
        private int wins;
        private int losses;
        private int remaining;

        public TeamRecord(String name, int win, int loss, int remaining) {
            this.name = name;
            this.wins = win;
            this.losses = loss;
            this.remaining = remaining;
        }
    }
}

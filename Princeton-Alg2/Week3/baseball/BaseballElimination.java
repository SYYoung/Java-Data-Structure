/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BaseballElimination {
    private TeamRecord[] teamRec;
    private HashMap<String, Integer> nameNode = new HashMap<String, Integer>();
    private int totalTeams;
    private int[][] games;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        readTeamFile(filename);
    }

    // number of teams
    public int numberOfTeams() {
        return totalTeams;
    }

    // all teams
    public Iterable<String> teams() {
        Set<String> names = nameNode.keySet();
        return names;
    }

    // number of wins for given team
    public int wins(String team) {
        if (nameNode.get(team) == null)
            throw new IllegalArgumentException();
        int node = nameNode.get(team);
        return teamRec[node].wins;
    }

    // number of losses for given team
    public int losses(String team) {
        if (nameNode.get(team) == null)
            throw new IllegalArgumentException();
        int node = nameNode.get(team);
        return teamRec[node].losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (nameNode.get(team) == null)
            throw new IllegalArgumentException();
        int node = nameNode.get(team);
        return teamRec[node].remaining;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if ((nameNode.get(team1) == null) || (nameNode.get(team2) == null))
            throw new IllegalArgumentException();
        int node1 = nameNode.get(team1);
        int node2 = nameNode.get(team2);
        return games[node1][node2];
    }

    // is given team eliminated
    public boolean isEliminated(String team) {
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        ArrayList<String> s = new ArrayList<String>();
        s.add("A");
        s.add("B");
        return s;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        // display the record
        division.displayTeamRecord();
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
        int num = 0;
        while (!in.isEmpty()) {
            totalTeams = in.readInt();
            teamRec = new TeamRecord[totalTeams];
            games = new int[totalTeams][totalTeams];
            //nameNode = new HashMap<String, Integer>();

            for (int k = 0; k < totalTeams; k++) {
                // each line format: Atlanta       83 71  8  0 1 6 1
                String name = in.readString();
                int wins = in.readInt();
                int losses = in.readInt();
                int remaining = in.readInt();
                teamRec[num] = new TeamRecord(name, wins, losses, remaining);
                // read games
                for (int i = 0; i < totalTeams; i++) {
                    games[num][i] = in.readInt();
                }
                // build hashmap
                nameNode.put(name, num);
                num++;
            }
        }
    }

    private void displayTeamRecord() {
        // for testing purpose
        for (String name : teams()) {
            StdOut.println(name + "\t" + wins(name) + "\t" + losses(name) + "\t" + remaining(name));
        }
        for (String name : teams()) {
            int node = nameNode.get(name);
            StdOut.print(name + ":");
            for (int j = 0; j < numberOfTeams(); j++)
                StdOut.print("\t" + games[node][j]);
        }
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

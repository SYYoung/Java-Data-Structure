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
    private HashMap<String, Integer> name2Node;
    private HashMap<Integer, String> node2Name;
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
        int num = 1;
        while (!in.isEmpty()) {
            totalTeams = in.readInt();
            teamRec = new TeamRecord[totalTeams + 1];
            games = new int[totalTeams + 1][totalTeams + 1];
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

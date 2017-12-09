import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {
	private final int wins[];  // number of wins for team i
	private final int losses[];  // number of losses for team i
	private final int remainingGames[];
	private final int gamesBetween[][];
	private final ST<String, Integer> teamNamesAndIndices;
	private final int numTeams;
	private final String[] teamNames;
	private final boolean isEliminated;
	private final SET<String> certificateOfElimination;
	
	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		In input = new In(filename);
		String inN = input.readLine();  // read number of teams
		int n = Integer.parseInt(inN);
		numTeams = n;
		wins = new int[n];
		losses = new int[n];
		remainingGames = new int[n];
		gamesBetween = new int[n][n];
		teamNamesAndIndices = new ST<String, Integer>();
		teamNames = new String[n];
		String in;
		String[] inArray;
		
		for (int i = 0; i < n; i++) {
			in = input.readLine();
			inArray = in.split(" +");
			teamNamesAndIndices.put(inArray[0], i);
			wins[i] = Integer.parseInt(inArray[1]);
			losses[i] = Integer.parseInt(inArray[2]);
			remainingGames[i] = Integer.parseInt(inArray[3]);
			teamNames[i] = inArray[0];
			for (int j = 0; j < n; j++) {
				gamesBetween[i][j] = Integer.parseInt(inArray[4 + j]);
			}
		}
		isEliminated = false;
	}
	
	// number of teams
	public int numberOfTeams() {
		return this.numTeams;
	}
	
	// all teams
	public Iterable<String> teams() {
		return teamNamesAndIndices.keys();
	}
	
	// number of wins for given team
	public int wins(String team) {
	    if (!teamNamesAndIndices.contains(team)) {
	        throw new java.lang.IllegalArgumentException("wins() has incorrect argument");
	    }
		return wins[teamNamesAndIndices.get(team)];
	}
	
	// number of losses for given team
	public int losses(String team) {
        if (!teamNamesAndIndices.contains(team)) {
            throw new java.lang.IllegalArgumentException("losses() has incorrect argument");
        }
        return losses[teamNamesAndIndices.get(team)];
	}
	
	// number of remaining games for given team
	public int remaining(String team) {
        if (!teamNamesAndIndices.contains(team)) {
            throw new java.lang.IllegalArgumentException("remaining() has incorrect argument");
        }
        return remainingGames[teamNamesAndIndices.get(team)];
    }
	
	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
	    if (!teamNamesAndIndices.contains(team1)) {
	        throw new java.lang.IllegalArgumentException("team1 exception to against()");
	    }
	    if (!teamNamesAndIndices.contains(team2)) {
	        throw new java.lang.IllegalArgumentException("team2 exception to against()");
	    }
		int team1Index = teamNamesAndIndices.get(team1);
		int team2Index = teamNamesAndIndices.get(team2);
		return gamesBetween[team1Index][team2Index];
	}
	
	// is given team eliminated?
	public boolean isEliminated(String team) {
		return false;
	}
	
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
        if (!teamNamesAndIndices.contains(team)) {
            throw new java.lang.IllegalArgumentException("remaining() has incorrect argument");
        }
        if (this.isEliminated) {
            return this.certificateOfElimination;
	}
	
	private void calculateElimination(String team) {
	    // ID of team x to check for elimination by other teams
        int teamX = this.teamNamesAndIndices.get(team);
        // maximum wins team x could get
        int maxWinsTeamX = w[teamX] + r[teamX];
        certificateOfElimination = new SET<String>();
        
        // trivial solution
        for (int i = 0; i < this.numTeams; i++) {
            if (i != teamX && wins[i] > maxWinsTeamX) {
                this.certificateOfElimination.add(teamNames[i]);
            }
        }
        if (!certificateOfElimination.isEmpty()) {
            this.isEliminated = true;
        }
        
        // non-trivial solution
        if (!this.isEliminated) {
            int totalCombinations = 0;
            // calculate number of different combinations of matches
            for (int i = 0; i < this.numTeams; i++) {
                for (int j = 0; j < this.numTeams; j++) {
                    if (i != teamX && j != teamX && this.gamesBetween[i][j] != 0) {
                        totalCombinations++;
                    }
                }
            }
            
            // total vertices in flow network
            // 2 for virtual source and virtual sink
            int totalVertices = 2 + totalCombinations + (this.numTeams - 1);
            
            FlowNetwork flowNetwork = new FlowNetwork(totalVertices);
            
            
        }
        
	}
	
	public static void main(String[] args) {
		BaseballElimination be = new BaseballElimination(args[0]);
		System.out.println(be.against("Poland", "Brazil"));
		System.out.println(be.remaining("Egypt"));
	}
		
}
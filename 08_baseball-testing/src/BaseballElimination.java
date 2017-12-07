import edu.princeton.cs.algs4.In;

public class BaseballElimination {
/*	private final int w[];  // number of wins for team i
	private final int l[];  // number of losses for team i
	private final int r[];
	private final int g[][];*/
	
	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		In input = new In(filename);
		String inN = input.readLine();
		int n = Integer.parseInt(inN);
		String in = new String();
		while (input.hasNextLine()) {
			in = input.readLine();
		}
	}
	
	// number of teams
	public int numberOfTeams() {
		return 0;
	}
	
	// all teams
	public Iterable<String> teams() {
		return null;
	}
	
	// number of wins for given team
	public int wins(String team) {
		return 0;
	}
	
	// number of losses for given team
	public int losses(String team) {
		return 0;
	}
	
	// number of remaining games for given team
	public int remaining(String team) {
		return 0;
	}
	
	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		return 0;
	}
	
	// is given team eliminated?
	public boolean isEliminated(String team) {
		return false;
	}
	
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		return null;
	}
	
	public static void main(String[] args) {
		BaseballElimination be = new BaseballElimination(args[0]);
	}
		
}
package uk.ac.lincoln.games.non_league.match;

import uk.ac.lincoln.games.non_league.team.*;

public class MatchResult {
	public Team team_1, team_2;
	public int result_1, result_2; // 1 - 0, score matching team_1 team_2 etc.
	
	//TODO this is probably where the strings to deal with results belong. "Bumthorpe FC gave Ballchester United a 3-0 drumming" etc.
	public String matchDescription() {
		return "XXX received a drumming by YYY";//TODO
	}
}

package uk.ac.lincoln.games.non_league.match;

import uk.ac.lincoln.games.non_league.team.*;
/**
 * Match class deals with individual matchups between teams
 * 
 * If any of the teams are player-owned they are run live, otherwise simulated
 * 
 * @author bkirman
 *
 */

public class Match {
	public Team team_1, team_2;
	public boolean has_run;
	private MatchResult result;
	
	public Match(Team team_1, Team team_2) {
		this.team_1 = team_1;
		this.team_2 = team_2;
		has_run = false;
	}
	
	public MatchResult run() {
		MatchResult result = new MatchResult();
		
		if(this.team_1.isPlayerControlled()||this.team_2.isPlayerControlled()) {
			//load the live match engine.
			//return that result
		}
		//do match calculations here
		//use team.win_bias
		//fill matchresult with score & return
		result.result_1 = 0;
		result.result_2 = 0;
		this.result = result;
		return result;
	}
}

package uk.ac.lincoln.games.nlfs.logic;

import java.util.List;
import static java.util.Arrays.asList;
/**
 * Match class deals with individual matchups between teams
 * 
 * If any of the teams are player-owned they are run live, otherwise simulated
 * 
 * @author bkirman
 *
 */

public class Match {
	public Team home, away;
	public boolean has_run;
	public MatchResult result;
	
	public Match(Team home, Team away) {
		this.home = home;
		this.away = away;
		has_run = false;
	}
	
	public String getDescription() {
		return home.name +" vs "+away.name+" at "+home.stadium;
	}
	/*
	public MatchResult run() { Dont forget to update the league table
		if(this.team_1.isPlayerControlled()||this.team_2.isPlayerControlled()) {
			//load the live match engine.
			//return that result
		}
		//do match calculations here. This is the cumulative distribution function of scorelines in premiership matches 2012 by Conor
		List<Double> results = asList(14.0,29.5,42.8,55.0,64.6,72.0,76.4,83.8,88.2,90.0,91.1,91.8,92.5,93.6,94.3,94.7,95.8,96.5,97.6,98.0,99.4,100.0);
		List<String> scores = asList("0-0","1-0","1-1","2-0","2-1","2-2","3-0","3-1","3-2","4-0","4-1","4-2","4-3","5-0","5-1","5-2","5-3","5-4","6-0","7-0","3-3","4-4");
		
		double pip = Math.random()*100;
		String actual_result = "0-0";
		for (int i=0;i<22;i++) {
			if(pip<results.get(i)) {
				actual_result = scores.get(i);
				break;
			}
		}
		
		int res_1 = Integer.valueOf(actual_result.substring(0,1));
		int res_2 = Integer.valueOf(actual_result.substring(2));
		int result_1 = 0;
		int result_2 = 0;
		
		if(res_1 == res_2) {//tie
			result_1 = res_1;
			result_2 = res_2;
		} 
		else {
			//there was a winner, who was it (using bias)
			if((team_1.win_bias+(Math.random()*2))>(team_2.win_bias+(Math.random()*2))) {
				result_1 = res_1;//winner
				result_2 = res_2;//loser
			}
			else{
				result_1 = res_2;//loser
				result_2 = res_1;//winner
			}
		}
		
		MatchResult result = new MatchResult(team_1,team_2,result_1,result_2);
		//fill matchresult with score & return
		this.result = result;
		this.has_run = true;
		return result;
	}*/
	public boolean isTeam(Team t) {//is t in this match
		return(t.equals(home)||t.equals(away));	
	}
}

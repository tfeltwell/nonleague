package uk.ac.lincoln.games.nlfs.logic;

import java.util.List;

import com.badlogic.gdx.Gdx;

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
	public static float HOME_ADVANTAGE = 0.06f;//the slight benefit to playing at home
	public transient Team home, away;
	public transient League league;
	public boolean has_run;
	public MatchResult result;
	
	private String home_name, away_name;//used as IDs for save/load to avoid circular serialisation (i.e. don't ask & don't fuck with it)
	
	public Match(League league, Team home, Team away) {
		this.league = league;
		this.home = home;
		this.away = away;
		this.has_run = false;
		this.home_name = home.name;
		this.away_name = away.name;
	}
	
	public Match() {}
	
	public String getDescription() {
		return home.name +" vs "+away.name+" at "+home.stadium;
	}
	
	/**
	 * return the opponent for this team
	 * @param t
	 * @return
	 */
	public Team opponentFor(Team t) {
		if(this.home == t) return this.away;
		if(this.away == t) return this.home;
		return null;
	}
	
	/**
	 * Calculates the results for this match.
	 * Note, match engine should be run for player-controlled games
	 * @return
	 */
	public MatchResult run() { 
		
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
		
		int win_goals = Integer.valueOf(actual_result.substring(0,1));
		int lose_goals = Integer.valueOf(actual_result.substring(2));
		
		int home_goals, away_goals;
		home_goals = away_goals = win_goals;//presume tie
		if(win_goals!=lose_goals) {//not a tie
			//there was a winner, who was it (using bias)
			if((home.win_bias+Match.HOME_ADVANTAGE+(Math.random()*2))>(away.win_bias+(Math.random()*2))) {
				home_goals = win_goals;//winner
				away_goals = lose_goals;//loser
			}
			else{
				home_goals = lose_goals;//loser
				away_goals = win_goals;//winner
			}
		}
		
		result = new MatchResult(this,home_goals,away_goals); //fill result object, calculate scorers etc
		this.has_run = true;
		league.addResult(result);//update league table
		return result;
	}
	public boolean isTeam(Team t) {//is t in this match
		return(t.equals(home)||t.equals(away));	
	}
	
	/**
	 * Used to reinitialise upstream pointers
	 * @param league
	 */
	public void reinit(League league) {
		this.league = league;
		//find team
		for(Team t:league.teams) {
			if(t.name.equals(home_name)) this.home = t;
			if(t.name.equals(away_name)) this.away = t;
			if(home!=null&&away!=null) break;//both found
		}
		if(has_run&&result!=null) {
			result.reinit(this);
		}
		//Gdx.app.log("initing match ok", home.name+away.name);
	}
}

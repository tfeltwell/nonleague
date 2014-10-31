package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Random;


import uk.ac.lincoln.games.nlfs.logic.Footballer.Position;



public class MatchResult {
	public Team home, away;
	public int home_goals, away_goals; // 1 - 0, score matching team_1 team_2 etc.
	public ArrayList<Footballer> home_scorers, away_scorers;
	
	public MatchResult(Team home, Team away,int home_goals,int away_goals){
		this.home_goals = home_goals;
		this.away_goals = away_goals;
		this.home = home;
		this.away = away;
		this.home_scorers = new ArrayList<Footballer>();
		this.away_scorers = new ArrayList<Footballer>();
		
		//calculate scorers
		if(home_goals > 0){
			for(int i=0;i<home_goals;i++){
				home_scorers.add(pickScorer(home));
			}
		}
		if(away_goals > 0){
			for(int i=0;i<away_goals;i++){
				away_scorers.add(pickScorer(away));
			}
		}
	}

	public Team getWinner() {
		if(this.home_goals==this.away_goals) return null;
		if(this.home_goals>this.away_goals) return home; else return away;
	}
	
	/**
	 * Returns a random scoring player
	 * @param selectedTeam
	 * @return
	 */
	private Footballer pickScorer(Team team){
		//60% of goals are scored by strikers, 30% by midfielders and 9% by DF and 1% by GK!
		float s = new Random().nextFloat();
		if(s<0.6) return team.getFootballerAtPosition(Position.ST);
		if(s<0.9) return team.getFootballerAtPosition(Position.MF);
		if(s<0.99) return team.getFootballerAtPosition(Position.DF);
		else return team.getFootballerAtPosition(Position.GK);
	}
	
	/**
	 * Returns a nicely filled news story about this match, from the perspective of the supplied team
	 * @param team
	 * @return
	 */
	public String getDescription(Team team) {
		if(team!=home&&team!=away) return null;
		String scoreline;
		Team opposition;
		if(team==home) {
			scoreline = String.valueOf(home_goals)+"-"+String.valueOf(away_goals);
			opposition = away;
		}
		else {
			scoreline = String.valueOf(away_goals)+"-"+String.valueOf(home_goals);
			opposition = home;
		}
		
		ArrayList<String> news_items = GameState.assets.news_summaries.get(scoreline);
        String news_item = news_items.get(new Random().nextInt(news_items.size()));//random description
        // Tokens: yourteam, opposition, goalkeeper, defender, midfielder, attacker, stadium
        news_item = news_item.replace("{yourteam}",team.name);
        news_item = news_item.replace("{opposition}",opposition.name);
        news_item = news_item.replace("{goalkeeper}",team.getFootballerAtPosition(Position.GK).getName());
        news_item = news_item.replace("{defender}",team.getFootballerAtPosition(Position.DF).getName());
        news_item = news_item.replace("{attacker}",team.getFootballerAtPosition(Position.ST).getName());
        news_item = news_item.replace("{midfielder}",team.getFootballerAtPosition(Position.MF).getName());
        news_item = news_item.replace("{stadium}",home.stadium);
        return news_item;
	}
	
	/**
	 * Returns the letter result for this team in this match W/L/D
	 * @param t
	 * @return
	 */
	public String resultForTeam(Team t){
		if(!(t.equals(home)||t.equals(away))) return null;//team not in this match
		if(getWinner()==null) return("D");
		if(getWinner()==t) return ("W");
		return ("L");
	}
	
	/**
	 * Used for simplifying GF/GA calculations
	 * @param t
	 * @return
	 */
	public int goalsFor(Team t) {
		if(t==home) return home_goals;
		return away_goals;
	}
	public int goalsAgainst(Team t) {
		if(t==home) return away_goals;
		return home_goals;
	}
}

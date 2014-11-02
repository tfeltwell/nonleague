package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Random;


import uk.ac.lincoln.games.nlfs.logic.Footballer.Position;



public class MatchResult {
	public transient Match match;
	public int home_goals, away_goals; // 1 - 0, score matching team_1 team_2 etc.
	public transient ArrayList<Footballer> home_scorers, away_scorers;
	private ArrayList<String> home_scorers_id,away_scorers_id;//evil serialisation circular id workaround. see the match class
	
	public MatchResult(Match match,int home_goals,int away_goals){
		this.match = match;
		this.home_goals = home_goals;
		this.away_goals = away_goals;
		this.home_scorers = new ArrayList<Footballer>();
		this.away_scorers = new ArrayList<Footballer>();
		this.home_scorers_id = new ArrayList<String>();
		this.away_scorers_id = new ArrayList<String>();
		
		Footballer scorer;
		//calculate scorers
		if(home_goals > 0){
			for(int i=0;i<home_goals;i++){
				scorer = pickScorer(match.home);
				home_scorers.add(scorer);
				home_scorers_id.add(scorer.getName());
			}
		}
		if(away_goals > 0){
			for(int i=0;i<away_goals;i++){
				scorer = pickScorer(match.away);
				away_scorers.add(scorer);
				away_scorers_id.add(scorer.getName());
			}
		}
	}
	public MatchResult(){}

	public Team getWinner() {
		if(this.home_goals==this.away_goals) return null;
		if(this.home_goals>this.away_goals) return match.home; else return match.away;
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
		if(team!=match.home&&team!=match.away) return null;
		String scoreline;
		Team opposition;
		if(team==match.home) {
			scoreline = String.valueOf(home_goals)+"-"+String.valueOf(away_goals);
			opposition = match.away;
		}
		else {
			scoreline = String.valueOf(away_goals)+"-"+String.valueOf(home_goals);
			opposition = match.home;
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
        news_item = news_item.replace("{stadium}",match.home.stadium);
        return news_item;
	}
	
	/**
	 * Returns the letter result for this team in this match W/L/D
	 * @param t
	 * @return
	 */
	public String resultForTeam(Team t){
		if(!(t.equals(match.home)||t.equals(match. away))) return null;//team not in this match
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
		if(t==match.home) return home_goals;
		return away_goals;
	}
	public int goalsAgainst(Team t) {
		if(t==match.home) return away_goals;
		return home_goals;
	}
	
	//Reload circular pointers after deserialisation
	public void reinit(Match m) {
		match = m;
		this.home_scorers = new ArrayList<Footballer>();
		this.away_scorers = new ArrayList<Footballer>();
		for(String s:home_scorers_id){
			for(Footballer f:m.home.footballers) {
				if (f.getName().equals(s)) {
					home_scorers.add(f);
					break;
				}
			}
		}
		for(String s:away_scorers_id){
			for(Footballer f:m.away.footballers) {
				if (f.getName().equals(s)) {
					away_scorers.add(f);
					break;
				}
			}
		}
	}
}

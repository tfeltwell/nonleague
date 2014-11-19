package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Random;


import uk.ac.lincoln.games.nlfs.logic.Footballer.Position;



public class MatchResult {
	public transient Match match;
	public ArrayList<Goal> home_goals, away_goals;
	
	public MatchResult(Match match,int h_goals,int a_goals){
		this.match = match;
		this.home_goals = new ArrayList<Goal>();
		this.away_goals = new ArrayList<Goal>();
		//calculate scorers
		for(int i=0;i<h_goals;i++){
			this.home_goals.add(new Goal(pickScorer(match.home),(new Random().nextInt(94))));
		}
		for(int i=0;i<a_goals;i++){
			this.away_goals.add(new Goal(pickScorer(match.away),(new Random().nextInt(94))));
		}
	}
	public MatchResult(){}

	public Team getWinner() {
		if(this.home_goals.size()==this.away_goals.size()) return null;
		if(this.home_goals.size()>this.away_goals.size()) return match.home; else return match.away;
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
		if(team!=match.home&&team!=match.away) return getDescription(match.home);//fail silently and return the description for the home team
		String scoreline;
		Team opposition;
		if(team==match.home) {
			scoreline = String.valueOf(home_goals.size())+"-"+String.valueOf(away_goals.size());
			opposition = match.away;
		}
		else {
			scoreline = String.valueOf(away_goals.size())+"-"+String.valueOf(home_goals.size());
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
		if(t==match.home) return home_goals.size();
		return away_goals.size();
	}
	public int goalsAgainst(Team t) {
		if(t==match.home) return away_goals.size();
		return home_goals.size();
	}
	
	//Reload circular pointers after deserialisation
	public void reinit(Match m) {
		match = m;
		for(Goal g: home_goals) {
			g.reinit(match.home);
		}
		for(Goal g: away_goals) {
			g.reinit(match.away);
		}
	}
}

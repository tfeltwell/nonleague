package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.lincoln.games.nlfs.logic.Footballer.Position;


public class MatchResult {
	public Team team_1, team_2;
	public int result_1, result_2; // 1 - 0, score matching team_1 team_2 etc.
	public ArrayList<Footballer> team_1_scorers, team_2_scorers;
	
	public MatchResult(Team team_1, Team team_2,int result_1,int result_2){
		this.team_1 = team_1;
		this.team_2 = team_2;
		this.result_1 = result_1;
		this.result_2 = result_2;
		this.team_1_scorers = new ArrayList<Footballer>();
		this.team_2_scorers = new ArrayList<Footballer>();
		calculateScorers();
				
	}
	
	private void calculateScorers(){
		if(result_1 > 0){
			for(int i=0;i<result_1;i++){
				team_1_scorers.add(pickScorer(team_1));
			}
		}
		if(result_2 > 0){
			for(int i=0;i<result_2;i++){
				team_2_scorers.add(pickScorer(team_2));
			}
		}
	}
	
	public Team getWinner() {
		if(this.result_1==this.result_2) return null;
		if(this.result_1>this.result_2) return team_1; else return team_2;
	}
	
	public int goalsFor(Team team) {
		if(team==team_1) return result_1;
		return result_2;
	}
	public int goalsAgainst(Team team) {
		if(team==team_1) return result_2;
		return result_1;
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
	
	//TODO this is probably where the strings to deal with results belong. "Bumthorpe FC gave Ballchester United a 3-0 drumming" etc.
	public String matchDescription() {
		return "XXX received a drumming by YYY";//TODO
	}
	
	public String resultForTeam(Team t){
		if(!(t.equals(team_1)||t.equals(team_2))) return null;//team not in this match
		
		if(result_1==result_2) return("D");
		
		if(result_1>result_2) {
			if(t.equals(team_1))
				return "W";
			else
				return "L";
		}
		else{
			if(t.equals(team_1))
				return "L";
			else
				return "W";
		}
	}
	
	public Team findOpposition(Team t){
		if(!(t.equals(team_1)||t.equals(team_2))) return null;//team not in this match
		
		if(t.equals(team_1)) return team_2;
		return team_1;
	}
}

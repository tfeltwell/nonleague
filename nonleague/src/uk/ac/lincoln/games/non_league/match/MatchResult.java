package uk.ac.lincoln.games.non_league.match;

import uk.ac.lincoln.games.non_league.team.*;
import uk.ac.lincoln.games.non_league.footballer.*;
import uk.ac.lincoln.games.non_league.player.*;
import java.util.ArrayList;
import java.util.Random;

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
				team_1_scorers.add(pickFootballer(team_1));
			}
		}
		if(result_2 > 0){
			for(int i=0;i<result_2;i++){
				team_2_scorers.add(pickFootballer(team_2));
			}
		}
	}
	
	private Footballer pickFootballer(Team selectedTeam){
		Random rand = new Random();
		return selectedTeam.footballers.get(rand.nextInt(selectedTeam.footballers.size()));
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
	
	// Return the nice "1-0" result, with players team score first
	public String resultForPlayerTeam(Player p){
		if(!(p.team.equals(team_1)||p.team.equals(team_2))) return null;//team not in this match
		if(team_1.equals(p.team)){
			return result_1 +"-"+result_2;
		}
		else{
			return result_2 +"-"+result_1;
		}
	}
	
	public Team findOpposition(Team t){
		if(!(t.equals(team_1)||t.equals(team_2))) return null;//team not in this match
		
		if(t.equals(team_1)){ return team_2; }
		else{ return team_1; }
	}
}

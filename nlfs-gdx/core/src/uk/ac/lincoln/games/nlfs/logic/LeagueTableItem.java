package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;

public class LeagueTableItem implements Comparable<LeagueTableItem> {
	public transient Team team;
	public int wins,losses,draws,points,gf,ga;
	public ArrayList<Integer> position_history;
	private String team_name;//for serialisation
	public LeagueTableItem(Team team) {
		this.team = team;
		this.team_name = team.name;
		wins = 0;
		losses = 0;
		draws = 0;
		points = 0;
		gf = 0;
		ga = 0;
		position_history = new ArrayList<Integer>();
		}
	public LeagueTableItem() {}//serialisation helper
	public int getGamesPlayed() {
		return wins+losses+draws;
	}
	
	public void addWeeklyPosition(Integer new_position) { position_history.add(new_position); }
	
	//add this result to the stats for this team. 
	public void addResult(MatchResult result) {
		gf+=result.goalsFor(team);
		ga+=result.goalsAgainst(team);
		if(result.getWinner()==null){//draw
			draws++;
			points+=League.POINTS_DRAW;
			return;
		}
		if(result.getWinner()==this.team){// 1 > 2
			wins++;
			points+=League.POINTS_WIN;
		} 
		else {
			losses++;
			points+=League.POINTS_LOSE;
		}
	}
	//allows them to be sorted
	public int compareTo(LeagueTableItem another) {
		return  another.points - this.points;
	}
	
	public void reinit(League league) {
		//find team
		for(Team t:league.teams) {
			if(t.name.equals(team_name)) this.team = t;			
		}
	}
}

package uk.ac.lincoln.games.nlfs.logic;

public class LeagueTableItem implements Comparable<LeagueTableItem> {
	public Team team;
	public int wins,losses,draws,points,gf,ga;
	
	public LeagueTableItem(Team team) {
		this.team = team;
		wins = 0;
		losses = 0;
		draws = 0;
		points = 0;
		gf = 0;
		ga = 0;
		}
	public int getGamesPlayed() {
		return wins+losses+draws;
	}
	
	//add this result to the stats for this team. 
	public void addResult(MatchResult result) {
		gf+=result.goalsFor(team);
		ga+=result.goalsAgainst(team);
		if(result.getWinner()==null){//draw
			draws++;
			points+=1;
			return;
		}
		if(result.getWinner()==this.team){// 1 > 2
			wins++;
			points+=3;
		} 
		else {
			losses++;
		}
	}
	//allows them to be sorted
	public int compareTo(LeagueTableItem another) {
		return  another.points - this.points;
	}
}

package uk.ac.lincoln.games.non_league.league;

import uk.ac.lincoln.games.non_league.team.Team;
import uk.ac.lincoln.games.non_league.match.*;

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
	
	//add this result to the stats for this team. kludgy
	public void calc(MatchResult result) {
		if(result.result_1==result.result_2){//draw
			draws++;
			gf+=result.result_1;
			ga+=result.result_1;
			points+=1;
		}
		if(result.result_1>result.result_2){// 1 > 2
			if(result.team_1==this.team){
				wins++;
				gf+=result.result_1;
				ga+=result.result_2;
				points+=3;
			} else {//2nd team
				losses++;
				gf+=result.result_2;
				ga+=result.result_1;
			}
		}//1 < 2
		else{
			if(result.team_1==this.team){
				losses++;
				gf+=result.result_1;
				ga+=result.result_2;
				
			} else {//2nd team
				wins++;
				gf+=result.result_2;
				ga+=result.result_1;
				points+=3;
			}
		}
			
	}
	//allows them to be sorted
	public int compareTo(LeagueTableItem another) {
		return  another.points - this.points;
	}
}

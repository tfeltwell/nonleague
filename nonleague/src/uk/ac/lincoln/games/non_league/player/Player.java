package uk.ac.lincoln.games.non_league.player;

import java.io.IOException;
import uk.ac.lincoln.games.non_league.team.*;

/**
 * 
 * @author tom
 *
 */

public class Player {

	String name;
	public Team team;
	
	
	public Player(String playerName){
		name = playerName;
	}
	
	public void setTeam(Team newTeam){
		if (team != null){ // Clear old team control, if we do switching of support
			team.setPlayerControlled(false);
		}
		team = newTeam;
		team.setPlayerControlled(true);
	}
	
	public String getTeamName(){return team.name;}
	
	public String getPlayerName(){return name;}
}

package uk.ac.lincoln.games.non_league.team;

import java.util.ArrayList;

import android.content.res.AssetManager;

import uk.ac.lincoln.games.non_league.footballer.*;

/**
 * Leagues have many teams. Teams have many players.
 * Teams may take part in matches with other teams.
 * 
 * @author bkirman
 *
 */
public class Team {
	private boolean player_control;
	
	public double win_bias;
	public String name;
	
	public Team(String team_name){
		this.player_control = false;
		win_bias = 0.5 - Math.random(); //gain a random bias between -0.5 and +0.5
		this.name = team_name;
		
		// Generate players 11
		generateSquad();
	}
	
	public void generateSquad(){
		
	}
	
	public boolean isPlayerControlled() {return this.player_control;}
	
	public void setPlayerControlled(boolean value){
		this.player_control = value;
		this.win_bias = 0.6 - Math.random(); // Slightly higher bias. Happy to refactor this
		}
	
	
}

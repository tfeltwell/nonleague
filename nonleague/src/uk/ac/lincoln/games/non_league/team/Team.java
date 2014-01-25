package uk.ac.lincoln.games.non_league.team;

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
	
	public Team(){
		this.player_control = false;
		win_bias = 0.5 - Math.random(); //gain a random bias between -0.5 and +0.5
		this.name = "BLAH UTDx" + String.valueOf(win_bias);
	}
	
	public boolean isPlayerControlled() {return this.player_control;}
}

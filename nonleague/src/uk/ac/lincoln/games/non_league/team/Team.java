package uk.ac.lincoln.games.non_league.team;

import java.util.ArrayList;
import java.util.Random;

import android.content.res.AssetManager;
import android.util.Log;

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
	public String stadium_name;
	public ArrayList<Footballer> footballers;
	
	public Team(String team_name, String stadium_name){
		this.player_control = false;
		win_bias = 0.5 - Math.random(); //gain a random bias between -0.5 and +0.5
		this.name = team_name;
		this.stadium_name = stadium_name;
		footballers = new ArrayList<Footballer>();
	}
	
	public void generateSquad(ArrayList<String> first_names, ArrayList<String> last_names){
		//footballers.add(new Footballer( first_names.get(new Random().nextInt(first_names.size())),last_names.get(new Random().nextInt(last_names.size()),20,"Striker")));
		Random rand = new Random();
		// Assuming 4-4-2 (defenders, midfielders, strikers)
		for(int i=0;i<11;i++){
			String pos = "";
			if(i < 4){
				pos = "Defender";
			}
			else{
				if(i >= 4 && i < 8){
					pos = "Midfield";
				}
				else{
					if(i <10){
						pos = "Striker";
					}
					else{
						pos = "Goalkeeper";
					}	
				}
			}
			footballers.add(new Footballer(first_names.get(rand.nextInt(first_names.size())),last_names.get(rand.nextInt(last_names.size())),rand.nextInt(15)+18,pos));
		}
	}
	
	public boolean isPlayerControlled() {return this.player_control;}
	
	public void setPlayerControlled(boolean value){
		this.player_control = value;
		this.win_bias = 0.6 - Math.random(); // Slightly higher bias. Happy to refactor this
		}
	
	
}

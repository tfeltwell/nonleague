package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.lincoln.games.nlfs.logic.Footballer.Position;

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
			Position pos = Position.GK;
			if(i < 4){
				pos = Position.DF;
			}
			else{
				if(i >= 4 && i < 8){
					pos = Position.MF;
				}else{
					if(i <10){
						pos = Position.ST;
					}
					else{
						pos = Position.GK;
					}	
				}
			}
			footballers.add(new Footballer(first_names.get(rand.nextInt(first_names.size())),last_names.get(rand.nextInt(last_names.size())),rand.nextInt(15)+18,pos));
		}
	}
	
	public boolean isPlayerControlled() {return this.player_control;}
	
	public void setPlayerControlled(boolean value){
		this.player_control = value;
		this.win_bias = 0.1; // Slightly higher than average bias. Happy to tweak this
		}
	
	public Footballer getFootballerAtPosition(Position position){
		Random rand = new Random();
		ArrayList<Footballer> foundPlayer = new ArrayList<Footballer>();
		for(int i=0;i<footballers.size();i++){
				if(footballers.get(i).getPosition()==position){
					foundPlayer.add(footballers.get(i));
				}
		}
		if(foundPlayer.size()<=0){ return null; }//no players of that position.
		int i = rand.nextInt(foundPlayer.size());//otherwise random striker/midfielder whatever
		return foundPlayer.get(i);
	}
}

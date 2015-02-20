package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import uk.ac.lincoln.games.nlfs.Assets;
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
	public String stadium;
	public ArrayList<Footballer> footballers;
	public transient League league;
	
	public String colour_primary,colour_base;
	public ArrayList<Integer> league_positions;
			
	public transient ArrayList<Footballer> defenders,midfielders,goalkeepers,strikers;
	
	/**
	 * Generate a new team from the supplied assets
	 */
	public Team(Assets assets, League league){
		this.player_control = false;
		this.league = league;
		
		league_positions = new ArrayList<Integer>();
		win_bias = 0.5 - Math.random(); //gain a random bias between -0.5 and +0.5
		
		//generate name, stadium
		do {
		name = assets.town_names.get((new Random()).nextInt(assets.town_names.size()));//random town name 
		if (Math.random()<0.3&&name.length()<9) //not all teams have suffices
			name = name +" "+ assets.team_names.get((new Random()).nextInt(assets.team_names.size()));
		}while(league.teamNameInUse(name));
		
		
		stadium = assets.stadium_names.get(new Random().nextInt(assets.stadium_names.size())) +" "+ assets.road_names.get(new Random().nextInt(assets.road_names.size()));
		ArrayList<String> colour = assets.team_colours.get(new Random().nextInt(assets.team_colours.size()));
		
		colour_base = colour.get(1);
		colour_primary = colour.get(0);
		
		
		//generate players (4-4-2 is the only formation used in real non-league football. Hard coded for realism.)
		footballers = new ArrayList<Footballer>();
		//TODO make sure footballer names are unique within their team
		goalkeepers = new ArrayList<Footballer>();
		Footballer player = new Footballer(assets,this,Position.GK); 
		footballers.add(player);
		goalkeepers.add(player);
		
		defenders = new ArrayList<Footballer>();
		for(int i=0;i<4;i++) {
			player = new Footballer(assets,this,Position.DF);
			footballers.add(player);
			defenders.add(player);
		}
		midfielders = new ArrayList<Footballer>();
		for(int i=0;i<4;i++) {
			player = new Footballer(assets,this,Position.MF);
			footballers.add(player);
			midfielders.add(player);
		}
		strikers = new ArrayList<Footballer>();
		for(int i=0;i<2;i++) {
			player = new Footballer(assets,this,Position.ST);
			footballers.add(player);
			strikers.add(player);
		}
		
	}
	
	public Team() {}
	
	public boolean isPlayerControlled() {return this.player_control;}
	
	public void setPlayerControlled(boolean value){
		this.player_control = value;
		this.win_bias = 0.15; // Slightly higher than average bias. Happy to tweak this
		}

	/**
	 * Return random player at given position
	 * @param position
	 * @return
	 */
	public Footballer getFootballerAtPosition(Position position){
		if(position==Position.GK) return goalkeepers.get(new Random().nextInt(goalkeepers.size()));
		if(position==Position.ST) return strikers.get(new Random().nextInt(strikers.size()));
		if(position==Position.DF) return defenders.get(new Random().nextInt(defenders.size()));
		return midfielders.get(new Random().nextInt(midfielders.size()));
	}
	
	public int getLeaguePosition() {
		for(int i=0;i<league.table.size();i++) {
			if(league.table.get(i).team.equals(this)){
				return i+1;
			}
		}
		return league.table.size()+1;
	}
	
	public void updateLeaguePositionHistory() {
		league_positions.add(Integer.valueOf(this.getLeaguePosition()));
	}
	public void resetLeaguePositionHistory() {
		league_positions.clear();
	}
	
	public int countUnplayedMatches() {
		int i = 0;
		for(Match m:league.fixtures) {
			if(!m.has_run&&m.isTeam(this)) i++;
		}
		return i;
	}
	
	public boolean footballerNameInUse(String full_name) {
		for(Footballer f:footballers) {
			if(f.getName().equals(full_name)) return true;
		}
		return false;
	}
	
	/**
	 * Reset transient pointers
	 */
	public void reinit(League league) {
		this.league = league;
		goalkeepers = new ArrayList<Footballer>();
		defenders = new ArrayList<Footballer>();
		midfielders = new ArrayList<Footballer>();
		strikers = new ArrayList<Footballer>();
		for (Footballer f: footballers){
			f.team = this;
			if(f.getPosition()==Position.GK) goalkeepers.add(f);
			if(f.getPosition()==Position.DF) defenders.add(f);
			if(f.getPosition()==Position.MF) midfielders.add(f);
			if(f.getPosition()==Position.ST) strikers.add(f);
		}
	}
}

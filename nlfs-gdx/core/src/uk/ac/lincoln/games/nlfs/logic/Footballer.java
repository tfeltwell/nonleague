package uk.ac.lincoln.games.nlfs.logic;

import java.util.Random;
import uk.ac.lincoln.games.nlfs.Assets;

public class Footballer {
	private String first_name, surname;
	private int age;
	
	private Position position;
	public transient Team team;
	public static enum Position {GK,DF,MF,ST}
	
	/**
	 * Generate a random new Footballer, of given position.
	 * @param assets
	 * @param pos
	 */
	public Footballer(Assets assets, Team team, Position pos){
		position = pos;
		this.team = team;
		
		do{
		first_name = assets.first_names.get(GameState.rand.nextInt(assets.first_names.size()));
		surname = assets.surnames.get(GameState.rand.nextInt(assets.surnames.size()));
		}while(team.footballerNameInUse(getName()));
		age = GameState.rand.nextInt(15)+18;
		//Gdx.app.log("ftgen", getName()+" is a "+getPositionName(position)+" for "+team.name+" at "+team.stadium);
		
	}
	
	public Footballer(){}
	
	public void setPostion(Position newPos){ position = newPos;}
	
	public int getAge(){ return age; }
	
	public Position getPosition(){ return position; }
	
	public String getPositionName(Position p) {
		if(p==Position.GK) return "Goalkeeper";
		if(p==Position.DF) return "Defender";
		if(p==Position.MF) return "Midfielder";
		return "Striker";
	}
	
	public String getFirstName(){ return first_name; }
	
	public String getSurname(){ return surname; }
	
	public String getName() { return first_name+" "+surname; }

}

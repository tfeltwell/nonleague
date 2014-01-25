package uk.ac.lincoln.games.non_league.action;

/*
 * Actions carried out by the player
 * 
 * 
 * @author tom
 *
 */

public class Action {

	String action_name;
	
	Action(String action){
		action_name = action;
	}
	
	public String getActionName(){
		return action_name;
	}
}

package uk.ac.lincoln.games.nlfs.logic;

import uk.ac.lincoln.games.nlfs.Assets;

/**
 * GameState is the singleton that holds all current game information.
 * It manages loading/saving/restoring of game data.
 * @author bkirman
 *
 */
public class GameState {
	public static GameState state;
	public static Assets assets;
	public static League league;
	
	public static GameState getGameState() {
		if(state==null) {
			state = new GameState();
		}
		return state;
	}
	
	public GameState() {
		assets = new Assets();
		
		//Try to load existing data from storage.
		//TODO
		//league =....
		//return;
		//None exists, time to generate a new world
		league = new League(assets);
	}
	
}

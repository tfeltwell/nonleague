package uk.ac.lincoln.games.nlfs.logic;

import uk.ac.lincoln.games.nlfs.Assets;

/**
 * GameState is the singleton that holds all current game information.
 * It manages loading/saving/restoring of game data.
 * @author bkirman
 *
 */
public class GameState {
	public static int LEAGUE_SIZE = 8; //number of teams in the league. Even numbers only, dickhead.
	public static int PROMOTION = 2; //number of teams promoted/relegated at the end of the season. (promotion + relegation) < league size, dickhead.
	public static int RELEGATION = 2;
	
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

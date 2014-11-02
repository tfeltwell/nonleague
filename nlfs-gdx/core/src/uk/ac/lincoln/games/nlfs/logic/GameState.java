package uk.ac.lincoln.games.nlfs.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
	
	public static String SAVEFILE = "nlfs.dat";
	
	
	public static GameState getGameState() {
		if(state==null) {
			state = new GameState();
		}
		return state;
	}
	
	public GameState() {
		assets = new Assets();
		
		//Try to load game from storage. If none exists create a new league
		if(!this.loadGame()) {
			Gdx.app.log("Start","no savefile found, creating new league");
			league = new League(assets);
		} else {Gdx.app.log("Start","Savefile found, loaded league");}
		
		//TODO: assign player a team, implement actual game, etc
	}
	
	/**
	 * Methods to load/save game from local storage
	 */
	public void saveGame() {
		Json json = new Json();
		String o = json.toJson(league);
		FileHandle file = Gdx.files.local(SAVEFILE);
	    file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(o), false);
	    Gdx.app.log("SAVE","Game Saved to "+SAVEFILE);
	}
	public boolean loadGame() {
		Json json = new Json();
		String s = "";
		FileHandle file = Gdx.files.local(SAVEFILE);
	    if (file != null && file.exists()) {
	    	s = file.readString();
	    }
	    if(s.isEmpty()) return false;
	    league = json.fromJson(League.class, com.badlogic.gdx.utils.Base64Coder.decodeString(s));
		league.reinit();
		return true;
	}
}

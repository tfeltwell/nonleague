package uk.ac.lincoln.games.non_league;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import uk.ac.lincoln.games.non_league.league.League;
import uk.ac.lincoln.games.non_league.player.Player;

public class GameState {
	public static final String TAG = "uk.ac.lincoln.games.non_league.GameState";
	private static GameState game_state;
	private Context context;
	public Player player;
	public League league;
	private ArrayList<String> team_names;
	private ArrayList<String> town_names;
	private ArrayList<String> first_names;
	private ArrayList<String> last_names;
	private ArrayList<String> stadium_names;
	private ArrayList<String> road_names;
	
	public GameState(Context c){
		//setup new game state
		this.context = c;
		//Get data from files
        team_names = new ArrayList<String>();
        town_names = new ArrayList<String>();
        first_names = new ArrayList<String>();
        last_names = new ArrayList<String>();
        stadium_names = new ArrayList<String>();
        road_names = new ArrayList<String>();
        
		
		//get the potential team names and suffixes, first names, last names and stadium names
		try{
			InputStream input = c.getAssets().open("teamnames.txt");
			BufferedReader buffreader = new BufferedReader(new InputStreamReader(input));
			String line = buffreader.readLine();
			while(line!=null) {
				team_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			input = c.getAssets().open("townnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				town_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			
			// Footballer names
			input = c.getAssets().open("firstnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				first_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			input = c.getAssets().open("surnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				last_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			
			// Stadium names
			
			input = c.getAssets().open("roadnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				road_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			input = c.getAssets().open("stadiumnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				stadium_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			
		} catch (IOException e) {
            Log.e(TAG,e.getMessage());
            //crash
        }
		//build league 
		
		Log.v(TAG,"Generating League");
        league = new League(town_names,team_names, first_names, last_names,road_names,stadium_names,14);
        /*Log.v(TAG,"Teams in the League:");
        for(int i=0;i<new_league.teams.size();i++) {
        	 Log.v(TAG,new_league.teams.get(i).name);
        }*/
	}
	
	public static synchronized GameState getGameState(Context c) {
		if(game_state==null) {
			Log.v(TAG,"Game State initialised to empty");
			game_state = new GameState(c);
		}
		return game_state;
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
}

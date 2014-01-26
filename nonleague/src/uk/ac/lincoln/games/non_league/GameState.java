package uk.ac.lincoln.games.non_league;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
	public HashMap<String, ArrayList<String>> news_items;
	private ArrayList<String> team_names;
	private ArrayList<String> town_names;
	private ArrayList<String> first_names;
	private ArrayList<String> last_names;
	private ArrayList<String> stadium_names;
	private ArrayList<String> road_names;
	private ArrayList<String> news_summaries;
	
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
        news_summaries = new ArrayList<String>();
        news_items = new HashMap();
        
		
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
			input = c.getAssets().open("newssummaries.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				news_summaries.add(line.replace("\n","").replace("\r",""));
				line = buffreader.readLine();
			}
			input.close();
			// Build an dictionary of arraylists (for each score)
			// Sorry
			String score = "";
			for(int i=0;i<news_summaries.size();i++){
				String currentRecord = news_summaries.get(i);
				//if(currentRecord.contentEquals("3-0")){Log.v(TAG,"MATCH!!!"+currentRecord); }
				// Win
				if(currentRecord.contentEquals("0-0")){score = "0-0";}
				else if(currentRecord.contentEquals("1-1")){score = "1-1";}
				else if(currentRecord.contentEquals("2-2")){score = "2-2";}
				else if(currentRecord.contentEquals("3-3")){score = "3-3";}
				else if(currentRecord.contentEquals("4-4")){score = "4-4";}
				// Lose
				else if(currentRecord.contentEquals("0-1")){score = "0-1";}
				else if(currentRecord.contentEquals("0-2")){score = "0-2";}
				else if(currentRecord.contentEquals("1-2")){score = "1-2";}
				else if(currentRecord.contentEquals("0-3")){score = "0-3";}
				else if(currentRecord.contentEquals("1-3")){score = "1-3";}
				else if(currentRecord.contentEquals("2-3")){score = "2-3";}
				else if(currentRecord.contentEquals("0-4")){score = "0-4";}
				else if(currentRecord.contentEquals("1-4")){score = "1-4";}
				else if(currentRecord.contentEquals("2-4")){score = "2-4";}
				else if(currentRecord.contentEquals("3-4")){score = "3-4";}
				else if(currentRecord.contentEquals("0-5")){score = "0-5";}
				else if(currentRecord.contentEquals("1-5")){score = "1-5";}
				else if(currentRecord.contentEquals("2-5")){score = "2-5";}
				else if(currentRecord.contentEquals("3-5")){score = "3-5";}
				else if(currentRecord.contentEquals("4-5")){score = "4-5";}
				else if(currentRecord.contentEquals("0-6")){score = "0-6";}
				else if(currentRecord.contentEquals("0-7")){score = "0-7";}
				// Win
				else if(currentRecord.contentEquals("1-0")){score = "1-0";}
				else if(currentRecord.contentEquals("2-0")){score = "2-0";}
				else if(currentRecord.contentEquals("2-1")){score = "2-1";}
				else if(currentRecord.contentEquals("3-0")){score = "3-0";}
				else if(currentRecord.contentEquals("3-1")){score = "3-1";}
				else if(currentRecord.contentEquals("3-2")){score = "3-2";}
				else if(currentRecord.contentEquals("4-1")){score = "4-1";}
				else if(currentRecord.contentEquals("4-2")){score = "4-2";}
				else if(currentRecord.contentEquals("4-3")){score = "4-3";}
				else if(currentRecord.contentEquals("5-0")){score = "5-0";}
				else if(currentRecord.contentEquals("5-1")){score = "5-1";}
				else if(currentRecord.contentEquals("5-2")){score = "5-2";}
				else if(currentRecord.contentEquals("5-3")){score = "5-3";}
				else if(currentRecord.contentEquals("5-4")){score = "5-4";}
				else if(currentRecord.contentEquals("6-0")){score = "6-0";}
				else if(currentRecord.contentEquals("7-0")){score = "7-0";}
				// Else its a normal line
				else{
					ArrayList<String> temp = new ArrayList<String>();
					if(news_items.get(score)!=null){
						temp = news_items.get(score);
						temp.add(currentRecord);
					}
					news_items.put(score,temp);
					//Log.v(TAG,currentRecord+" saved in "+score);
				}	
			}
			
			
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

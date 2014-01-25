package uk.ac.lincoln.games.non_league;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import uk.ac.lincoln.games.non_league.league.*;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;
import uk.ac.lincoln.games.non_league.player.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	public static final String TAG = "uk.ac.lincoln.games.non_league.MainActivity";
	private ArrayList<String> team_names;
	private ArrayList<String> town_names;
	private ArrayList<String> first_names;
	private ArrayList<String> last_names;
	private ArrayList<String> stadium_names;
	private ArrayList<String> road_names;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Load data from assets
        
        team_names = new ArrayList<String>();
        town_names = new ArrayList<String>();
        first_names = new ArrayList<String>();
        last_names = new ArrayList<String>();
        stadium_names = new ArrayList<String>();
        road_names = new ArrayList<String>();
        
		
		//get the potential team names and suffixes, first names, last names and stadium names
		try{
			InputStream input = this.getAssets().open("teamnames.txt");
			BufferedReader buffreader = new BufferedReader(new InputStreamReader(input));
			String line = buffreader.readLine();
			while(line!=null) {
				team_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			input = this.getAssets().open("townnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				town_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			
			// Footballer names
			input = this.getAssets().open("firstnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				first_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			input = this.getAssets().open("surnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				last_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			
			// Stadium names
			
			input = this.getAssets().open("roadnames.txt");
			buffreader = new BufferedReader(new InputStreamReader(input));
			line = buffreader.readLine();
			while(line!=null) {
				road_names.add(line);
				line = buffreader.readLine();
			}
			input.close();
			input = this.getAssets().open("stadiumnames.txt");
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
        
        Log.v(TAG,"Generating League");
        League new_league = new League(town_names,team_names, first_names, last_names,road_names,stadium_names,14);
        Log.v(TAG,"Teams in the League:");
        for(int i=0;i<new_league.teams.size();i++) {
        	 Log.v(TAG,new_league.teams.get(i).name);
        }
        
        // Force select player team.
        Player player = new Player("Tom Kirmhan");
        int teamID = new Random().nextInt(new_league.teams.size());
        player.setTeam(new_league.teams.get(teamID));
        Log.v(TAG,player.getPlayerName()+" is supporting: "+player.getTeamName());
        Log.v(TAG,player.getTeamName()+" players:");
        for(int i=0;i<player.team.footballers.size();i++){
        	Log.v(TAG,player.team.footballers.get(i).getFull());
        }
        
        /*Log.v(TAG,"Fixtures in the League:");
        for(int i=0;i<new_league.fixtures.size();i++) {
        	 Log.v(TAG,new_league.fixtures.get(i).team_1.name+" vs "+new_league.fixtures.get(i).team_2.name);
        }*/
        
        Log.v(TAG,"Running Fixtures:");
        Match fixture = new_league.nextFixture();
        MatchResult result;
        while(fixture!=null){
        	result = fixture.run();
        	//Log.v(TAG,result.team_1.name + " "+String.valueOf(result.result_1)+"-"+String.valueOf(result.result_2)+" "+result.team_2.name);
        	fixture = new_league.nextFixture();
        }
        
        Log.v(TAG,"Printing League Table:");
        ArrayList<LeagueTableItem> table = new_league.getLeagueTable();
        Log.v(TAG,"Name\t\tW\tL\tD\tPoints");
        for(int i=0;i<table.size();i++) {
        	LeagueTableItem line = table.get(i);
        	Log.v(TAG,line.team.name+"\t\t"+String.valueOf(line.wins)+"\t"+String.valueOf(line.losses)+"\t"+String.valueOf(line.draws)+"\t"+String.valueOf(line.points));
       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

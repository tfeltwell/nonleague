package uk.ac.lincoln.games.non_league;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import uk.ac.lincoln.games.non_league.league.*;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	public static final String TAG = "uk.ac.lincoln.games.non_league.MainActivity";
	private ArrayList<String> team_names;
	private ArrayList<String> town_names;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Load data from assets
        
        team_names = new ArrayList<String>();
        town_names = new ArrayList<String>();
		
		//get the potential team names and suffixes
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
		} catch (IOException e) {
            Log.e(TAG,e.getMessage());
            //crash
        }
        
        Log.v(TAG,"Generating League");
        League new_league = new League(town_names,team_names);
        
        Log.v(TAG,"Teams in the League:");
        for(int i=0;i<new_league.teams.size();i++) {
        	 Log.v(TAG,new_league.teams.get(i).name);
        }
        
        Log.v(TAG,"Fixtures in the League:");
        for(int i=0;i<new_league.fixtures.size();i++) {
        	 Log.v(TAG,new_league.fixtures.get(i).team_1.name+" vs "+new_league.fixtures.get(i).team_2.name);
        }
        
        Log.v(TAG,"Running Fixtures:");
        Match fixture = new_league.nextFixture();
        MatchResult result;
        while(fixture!=null){
        	result = fixture.run();
        	//Log.d("TEST","GOT HERE"+result.team_1.name);
        	Log.v(TAG,result.team_1.name + " "+String.valueOf(result.result_1)+"-"+String.valueOf(result.result_2)+" "+result.team_2.name);
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

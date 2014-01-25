package uk.ac.lincoln.games.non_league;

import uk.ac.lincoln.games.non_league.league.*;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private static final String TAG = "uk.ac.lincoln.games.non_league.MainActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"Generating League");
        League new_league = new League();
        
        Log.v(TAG,"Teams in the League:");
        for(int i=0;i<new_league.teams.size();i++) {
        	 Log.v(TAG,new_league.teams.get(i).name);
        }
        
        Log.v(TAG,"Fixtures in the League:");
        for(int i=0;i<new_league.fixtures.size();i++) {
        	 Log.v(TAG,new_league.fixtures.get(i).team_1.name+" vs "+new_league.fixtures.get(i).team_2.name);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

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
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
	public static final String TAG = "uk.ac.lincoln.games.non_league.MainActivity";
	private Button go;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        GameState state = GameState.getGameState(this);
        // Force select player team. TODO this should be player entry
        Player player = new Player("Tom Kirmhan");
        int teamID = new Random().nextInt(state.league.teams.size());
        player.setTeam(state.league.teams.get(teamID));
        Log.v(TAG,player.getPlayerName()+" is following "+player.getTeamName()+" from "+player.team.stadium_name);
        Log.v(TAG,player.getTeamName()+" players:");
        for(int i=0;i<player.team.footballers.size();i++){
        	Log.v(TAG,player.team.footballers.get(i).getFull()+" - "+player.team.footballers.get(i).getPosition());
        }
        state.setPlayer(player);
        
        go = (Button)this.findViewById(R.id.startNewGame);
        go.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
        	  
        	  Intent myIntent = new Intent(MainActivity.this,TeamStatus.class);
        	  
        	  MainActivity.this.startActivity(myIntent);
          }
        });
        
        for(int i=0;i<state.league.teams.size();i++){
        	Log.v(TAG,state.league.teams.get(i).name +" play at "+state.league.teams.get(i).stadium_name);
        }
        
        
    }


    
    
}

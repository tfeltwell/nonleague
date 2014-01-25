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
import android.widget.Button;

public class MainActivity extends Activity {
	public static final String TAG = "uk.ac.lincoln.games.non_league.MainActivity";
	private Button go;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        
        /*Log.v(TAG,"Fixtures in the League:");
        for(int i=0;i<new_league.fixtures.size();i++) {
        	 Log.v(TAG,new_league.fixtures.get(i).team_1.name+" vs "+new_league.fixtures.get(i).team_2.name);
        }
        
        Log.v(TAG,"Running Fixtures:");
        Match fixture = state.league.nextFixture();
        MatchResult result;
        while(fixture!=null){
        	result = fixture.run();
        	Log.v(TAG,"Game between "+result.team_1.name+" and "+result.team_2.name+" played at "+result.team_1.stadium_name+" | Score: "+result.result_1+" - "+result.result_2);
        	for(int i=0;i<result.team_1_scorers.size();i++){
        		Log.v(TAG,result.team_1_scorers.get(i).getFull()+" of "+result.team_1.name+" scored!");
        	}
        	for(int i=0;i<result.team_2_scorers.size();i++){
        		Log.v(TAG,result.team_2_scorers.get(i).getFull()+" of "+result.team_2.name+" scored!");
        	}
        	//Log.v(TAG,result.team_1.name + " "+String.valueOf(result.result_1)+"-"+String.valueOf(result.result_2)+" "+result.team_2.name);
        	fixture = state.league.nextFixture();
        }/*
        
        Log.v(TAG,"Printing League Table:");
        ArrayList<LeagueTableItem> table = new_league.getLeagueTable();
        Log.v(TAG,"Name\t\tW\tL\tD\tPoints");
        for(int i=0;i<table.size();i++) {
        	LeagueTableItem line = table.get(i);
        	Log.v(TAG,line.team.name+"\t\t"+String.valueOf(line.wins)+"\t"+String.valueOf(line.losses)+"\t"+String.valueOf(line.draws)+"\t"+String.valueOf(line.points));
       }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

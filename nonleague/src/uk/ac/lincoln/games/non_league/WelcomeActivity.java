
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
import android.widget.TextView;

public class WelcomeActivity extends Activity {
	public static final String TAG = "uk.ac.lincoln.games.non_league.WelcomeActivity";
	private Button go;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        
        GameState state = GameState.getGameState(this);
        
        ((TextView)this.findViewById(R.id.welcomeTeam)).setText(state.player.team.name);
        String stadium = state.player.team.stadium_name;
        String teamname = state.player.team.name;
        ((TextView)this.findViewById(R.id.introText)).setText("Since you were a nipper, your dad took you down to "+stadium+" to see "+teamname+" play. Today, you get to follow in his footsteps as "+teamname+" battle honestly against the odds to achieve a respectable league position");
        
        go = (Button)this.findViewById(R.id.welcomeStartGameButton);
        go.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
        	  
        	  Intent myIntent = new Intent(WelcomeActivity.this,TeamStatus.class);
        	  
        	  WelcomeActivity.this.startActivity(myIntent);
          }
        });
        
        
        
    }


    
    
}

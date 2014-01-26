package uk.ac.lincoln.games.non_league;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;
import uk.ac.lincoln.games.non_league.team.*;

/**
 * This screen shows the current status of the player's team, and info about the next match.
 * @author bkirman
 *
 */

public class PlayerMatchResult extends Activity {
	private GameState state;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_postmatchresult);
	        
	        state = GameState.getGameState(this);
	        if(!state.league.nextFixture().isTeam(state.player.team)) {
	        	//first time we need to skip some matches.
	        	while (!state.league.nextFixture().isTeam(state.player.team)) {
	        		state.league.nextFixture().run();
	        	}
	        }
	        MatchResult result = state.league.nextFixture().run();
	        //((TextView)this.findViewById(R.id.resultScoreline)).setText(result.team_1.name+" "+String.valueOf(result.result_1)+" - "+String.valueOf(result.result_2)+" "+result.team_2.name);
	        ((TextView)this.findViewById(R.id.resultTeam1)).setText(result.team_1.name);
	        ((TextView)this.findViewById(R.id.resultTeam1Score)).setText(String.valueOf(result.result_1));
	        ((TextView)this.findViewById(R.id.resultTeam2)).setText(result.team_2.name);
	        ((TextView)this.findViewById(R.id.resultTeam2Score)).setText(String.valueOf(result.result_2));
	        //Go see other weekly results.
	        
	        Button go = (Button)this.findViewById(R.id.seeOtherResultsButton);
	        go.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
	        	  Intent myIntent = new Intent(PlayerMatchResult.this,PostMatchResults.class);
	        	  PlayerMatchResult.this.startActivity(myIntent);
	          }
	        });
	 }
	 
}

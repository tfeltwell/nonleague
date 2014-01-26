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
import uk.ac.lincoln.games.non_league.team.*;

/**
 * This screen shows the current status of the player's team, and info about the next match.
 * @author bkirman
 *
 */

public class TeamStatus extends Activity {
	private GameState state;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_teamstatus);
	        state = GameState.getGameState(this);
	        Match nextFixture = state.league.findTeamsNextFixture(state.player.team);
	        Team oppTeam;
	        if(nextFixture.team_1.name == state.player.team.name){
	        	oppTeam = nextFixture.team_2;
	        }
	        else{
	        	oppTeam = nextFixture.team_1;
	        }
	        ((TextView) findViewById(R.id.teamName)).setText(state.player.team.name);
	        ((TextView) findViewById(R.id.stadiumName)).setText(state.player.team.stadium_name);
	        ((TextView) findViewById(R.id.recentForm)).setText(state.league.getForm(state.player.team));

	        ((TextView) findViewById(R.id.leaguePosition)).setText(String.valueOf(state.league.getPosition(state.player.team)));

	        ((TextView) findViewById(R.id.opponentName)).setText(oppTeam.name);
	        ((TextView) findViewById(R.id.fixtureStadiumName)).setText(nextFixture.team_1.stadium_name);
	        Log.d("DEBUG","got here");
	        
	        //go to prematch screen
	        Button go = (Button)this.findViewById(R.id.buttonGoToGame);
	        go.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
	        	  Intent myIntent = new Intent(TeamStatus.this,PreMatchActions.class);
	        	  TeamStatus.this.startActivity(myIntent);
	          }
	        });
	 }
	 
}

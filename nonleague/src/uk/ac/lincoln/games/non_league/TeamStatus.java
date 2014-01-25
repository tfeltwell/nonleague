package uk.ac.lincoln.games.non_league;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
	        
	        ((TextView) findViewById(R.id.teamName)).setText(state.player.team.name);
	        ((TextView) findViewById(R.id.stadiumName)).setText(state.player.team.stadium_name);
	        ((TextView) findViewById(R.id.recentForm)).setText(state.league.getForm(state.player.team));
	        ((TextView) findViewById(R.id.leaguePosition)).setText(String.valueOf(state.league.getPosition(state.player.team)));
	        Log.d("DEBUG","got here");
	        
	        
	 }
	 
}

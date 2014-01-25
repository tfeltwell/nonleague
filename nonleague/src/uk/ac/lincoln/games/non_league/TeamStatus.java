package uk.ac.lincoln.games.non_league;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
	        ((TextView) findViewById(R.id.stadiumName)).setText(nextFixture.team_1.stadium_name);
	        ((TextView) findViewById(R.id.recentForm)).setText(state.league.getForm(state.player.team));
	        ((TextView) findViewById(R.id.opponentName)).setText(oppTeam.name);
	        Log.d("DEBUG","got here");
	        
	        
	 }
	 
}

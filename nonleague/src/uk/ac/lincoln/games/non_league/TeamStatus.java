package uk.ac.lincoln.games.non_league;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
	        String form = state.league.getForm(state.player.team);
	        
	        ((TextView) findViewById(R.id.recentForm1)).setText(form.substring(0, 1));
	        if(form.substring(0, 1).contentEquals("W"))
	        	((TextView) findViewById(R.id.recentForm1)).setBackgroundColor(Color.GREEN);
	        if(form.substring(0, 1).contentEquals("L"))
	        	((TextView) findViewById(R.id.recentForm1)).setBackgroundColor(Color.RED);
	        if(form.substring(0, 1).contentEquals("D"))
	        	((TextView) findViewById(R.id.recentForm1)).setBackgroundColor(Color.YELLOW);
	        if(form.substring(0, 1).contentEquals("-"))
	        	((TextView) findViewById(R.id.recentForm1)).setBackgroundColor(Color.LTGRAY);
	        ((TextView) findViewById(R.id.recentForm2)).setText(form.substring(1, 2));
	        if(form.substring(1,2).contentEquals("W"))
	        	((TextView) findViewById(R.id.recentForm2)).setBackgroundColor(Color.GREEN);
	        if(form.substring(1,2).contentEquals("L"))
	        	((TextView) findViewById(R.id.recentForm2)).setBackgroundColor(Color.RED);
	        if(form.substring(1,2).contentEquals("D"))
	        	((TextView) findViewById(R.id.recentForm2)).setBackgroundColor(Color.YELLOW);
	        if(form.substring(1,2).contentEquals("-"))
	        	((TextView) findViewById(R.id.recentForm2)).setBackgroundColor(Color.LTGRAY);
	        ((TextView) findViewById(R.id.recentForm3)).setText(form.substring(2, 3));
	        if(form.substring(2,3).contentEquals("W"))
	        	((TextView) findViewById(R.id.recentForm3)).setBackgroundColor(Color.GREEN);
	        if(form.substring(2,3).contentEquals("L"))
	        	((TextView) findViewById(R.id.recentForm3)).setBackgroundColor(Color.RED);
	        if(form.substring(2,3).contentEquals("D"))
	        	((TextView) findViewById(R.id.recentForm3)).setBackgroundColor(Color.YELLOW);
	        if(form.substring(2,3).contentEquals("-"))
	        	((TextView) findViewById(R.id.recentForm3)).setBackgroundColor(Color.LTGRAY);
	        ((TextView) findViewById(R.id.recentForm4)).setText(form.substring(3, 4));
	        if(form.substring(3,4).contentEquals("W"))
	        	((TextView) findViewById(R.id.recentForm4)).setBackgroundColor(Color.GREEN);
	        if(form.substring(3,4).contentEquals("L"))
	        	((TextView) findViewById(R.id.recentForm4)).setBackgroundColor(Color.RED);
	        if(form.substring(3,4).contentEquals("D"))
	        	((TextView) findViewById(R.id.recentForm4)).setBackgroundColor(Color.YELLOW);
	        if(form.substring(3,4).contentEquals("-"))
	        	((TextView) findViewById(R.id.recentForm4)).setBackgroundColor(Color.LTGRAY);
	        ((TextView) findViewById(R.id.recentForm5)).setText(form.substring(4, 5));
	        if(form.substring(4,5).contentEquals("W"))
	        	((TextView) findViewById(R.id.recentForm5)).setBackgroundColor(Color.GREEN);
	        if(form.substring(4,5).contentEquals("L"))
	        	((TextView) findViewById(R.id.recentForm5)).setBackgroundColor(Color.RED);
	        if(form.substring(4,5).contentEquals("D"))
	        	((TextView) findViewById(R.id.recentForm5)).setBackgroundColor(Color.YELLOW);
	        if(form.substring(4,5).contentEquals("-"))
	        	((TextView) findViewById(R.id.recentForm5)).setBackgroundColor(Color.LTGRAY);

	        ((TextView) findViewById(R.id.leaguePosition)).setText(String.valueOf(state.league.getPosition(state.player.team)));

	        ((TextView) findViewById(R.id.opponentName)).setText(oppTeam.name);
	        ((TextView) findViewById(R.id.fixtureStadiumName)).setText("at "+nextFixture.team_1.stadium_name);
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

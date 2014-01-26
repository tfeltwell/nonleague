package uk.ac.lincoln.games.non_league;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import uk.ac.lincoln.games.non_league.league.LeagueTableItem;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;
import uk.ac.lincoln.games.non_league.team.*;

/** 
 * View the current state of the league
 * @author bkirman
 *
 */
public class LeagueTableView extends Activity{
	private GameState state;
	public static final String TAG = "uk.ac.lincoln.games.non_league.LeagueTableView";
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_leaguetableview);
	        
	        state = GameState.getGameState(this);
	        
	        //Log.d("DEBUG","got here");
	        TableLayout tl = (TableLayout) findViewById(R.id.leagueTableTable);
	        ArrayList<LeagueTableItem> table = state.league.getLeagueTable();
	        
	        Iterator<LeagueTableItem> i = table.iterator();
			LeagueTableItem m;
			while(i.hasNext()) {
				m = i.next();
				Log.d("DEBUG","got here:"+m.team.name);
				// Create a TableRow and give it an ID
	            TableRow tr = new TableRow(this);
	            //tr.setId(100+current);
	            //tr.setLayoutParams(new LayoutParams(
	            //        LayoutParams.FILL_PARENT,
	            //        LayoutParams.WRAP_CONTENT));   

	            // Team name
	            TextView labelTV = new TextView(this);
	            labelTV.setPadding(15,0,0,0);
	            labelTV.setText(m.team.name);
	            if(m.team.equals(state.player.team)) labelTV.setTypeface(null,Typeface.BOLD);
	            //labelTV.setTextColor(Color.BLACK);
	            //labelTV.setLayoutParams(new LayoutParams(
	            //        LayoutParams.FILL_PARENT,
	            //        LayoutParams.WRAP_CONTENT));
	            tr.addView(labelTV);
	            //played
	            labelTV = new TextView(this);
	            labelTV.setText(String.valueOf(m.getGamesPlayed()));
	            labelTV.setGravity(Gravity.RIGHT);
	            labelTV.setPadding(0, 0, 15, 0);
	            if(m.team.equals(state.player.team)) labelTV.setTypeface(null,Typeface.BOLD);
	            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	            tr.addView(labelTV);
	            //wins
	            labelTV = new TextView(this);
	            labelTV.setText(String.valueOf(m.wins));
	            labelTV.setGravity(Gravity.RIGHT);
	            labelTV.setPadding(0, 0, 15, 0);
	            if(m.team.equals(state.player.team)) labelTV.setTypeface(null,Typeface.BOLD);
	            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	            tr.addView(labelTV);
	            //draws
	            labelTV = new TextView(this);
	            labelTV.setText(String.valueOf(m.draws));
	            labelTV.setGravity(Gravity.RIGHT);
	            labelTV.setPadding(0, 0, 15, 0);
	            if(m.team.equals(state.player.team)) labelTV.setTypeface(null,Typeface.BOLD);
	            tr.addView(labelTV);
	            //losses
	            labelTV = new TextView(this);
	            labelTV.setText(String.valueOf(m.losses));
	            labelTV.setGravity(Gravity.RIGHT);
	            labelTV.setPadding(0, 0, 15, 0);
	            if(m.team.equals(state.player.team)) labelTV.setTypeface(null,Typeface.BOLD);
	            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	            tr.addView(labelTV);
	            //points
	            labelTV = new TextView(this);
	            labelTV.setText(String.valueOf(m.points));
	            labelTV.setGravity(Gravity.RIGHT);
	            labelTV.setPadding(0, 0, 15, 0);
	            if(m.team.equals(state.player.team)) labelTV.setTypeface(null,Typeface.BOLD);
	            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	            tr.addView(labelTV);
	            

	            // Add the TableRow to the TableLayout
	            tl.addView(tr);
	            
			}
			Button goNextMatch = new Button(this);
			 //do stuff like add text and listeners.
			goNextMatch.setText("Go To Next Match");
			goNextMatch.setOnClickListener(new OnClickListener() {
		          @Override
		          public void onClick(View v) {
		        	  Intent myIntent = new Intent(LeagueTableView.this,TeamStatus.class);
		        	  LeagueTableView.this.startActivity(myIntent);
		          }
		        });
			
			tl.addView(goNextMatch);
			
	 }
	 
}


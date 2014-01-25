package uk.ac.lincoln.games.non_league;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;


/**
 * This screen shows the results of all matches including the last player fixture, up until just before the next one.
 * @author bkirman
 *
 */
public class PostMatchResults extends ListActivity{
	private GameState state;
	public static final String TAG = "uk.ac.lincoln.games.non_league.PostMatchResults";
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_postmatchresults);
	        
	        state = GameState.getGameState(this);
	        ArrayList<String> results = new ArrayList<String>();
	        //calculate results and append them to the results list.
	        Match nextFixture = state.league.findTeamsNextFixture(state.player.team);
	        while(!state.league.nextFixture().isTeam(state.player.team)) {//until the next game of the player
	        	MatchResult result = state.league.nextFixture().run();
	        	String r_desc = result.team_1.name + " " + String.valueOf(result.result_1)+" - "+ String.valueOf(result.result_2) + " " + result.team_2.name;
	        	results.add(r_desc);
	        }
	        if(results.size()==0)
	        	results.add("No Other Fixtures");
	        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, results));
	 }

	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 super.onListItemClick(l, v, position, id);
		 Intent myIntent = new Intent(PostMatchResults.this,LeagueTableView.class);
		 PostMatchResults.this.startActivity(myIntent);
	 }
}


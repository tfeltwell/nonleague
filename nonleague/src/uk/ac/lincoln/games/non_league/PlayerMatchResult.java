package uk.ac.lincoln.games.non_league;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
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
	        Match thisMatch = state.league.nextFixture();
	        MatchResult result = thisMatch.run();
	        ((TextView)this.findViewById(R.id.resultTeam1)).setText(result.team_1.name);
	        ((TextView)this.findViewById(R.id.resultTeam2)).setText(result.team_2.name);
	        ((TextView)this.findViewById(R.id.resultTeam1Score)).setText(String.valueOf(result.result_1));
	        ((TextView)this.findViewById(R.id.resultTeam2Score)).setText(String.valueOf(result.result_2));
	        //+" "+String.valueOf(result.result_1)+" - "+String.valueOf(result.result_2)+" "+result.team_2.name);
	        // Match summary statements
	        String resultAsString = result.resultForPlayerTeam(state.player);
	        Log.v("FUCK",resultAsString);
	        Log.v("KEYSET",String.valueOf(state.news_items.keySet()));
	        ArrayList<String> all_items = state.news_items.get(resultAsString);
	        String news_item = all_items.get(new Random().nextInt(all_items.size()));
	        // Tokens: yourteam, opposition, goalkeeper, defender, midfielder, attacker, stadium
	        news_item = news_item.replace("{yourteam}",String.valueOf(state.player.team.name));
	        news_item = news_item.replace("{opposition}",String.valueOf(result.findOpposition(state.player.team).name));
	        news_item = news_item.replace("{goalkeeper}",String.valueOf(state.player.team.getFootballerAtPosition("goalkeeper")));
	        news_item = news_item.replace("{defender}",String.valueOf(state.player.team.getFootballerAtPosition("defender")));
	        news_item = news_item.replace("{attacker}",String.valueOf(state.player.team.getFootballerAtPosition("striker")));
	        news_item = news_item.replace("{midfielder}",String.valueOf(state.player.team.getFootballerAtPosition("midfield")));
	        news_item = news_item.replace("{stadium}",String.valueOf(thisMatch.team_1.stadium_name));
	        ((TextView)this.findViewById(R.id.resultNewsItem)).setText(news_item);
	        
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

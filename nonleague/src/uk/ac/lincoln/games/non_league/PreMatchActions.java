package uk.ac.lincoln.games.non_league;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;
import uk.ac.lincoln.games.non_league.team.*;
import uk.ac.lincoln.games.non_league.util.MultiSpinner;

/**
 * This screen shows the current status of the player's team, and info about the next match.
 * @author bkirman
 *
 */

public class PreMatchActions extends Activity{
	private GameState state;
	private Match match;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_prematchactions);
	        state = GameState.getGameState(this);
	        
	        if(!state.league.nextFixture().isTeam(state.player.team)) {
	        	//first time we need to skip some matches.
	        	while (!state.league.nextFixture().isTeam(state.player.team)) {
	        		state.league.nextFixture().run();
	        	}
	        }
	        match = state.league.nextFixture();
	        
	        String[] possible_weather = {"Mild","Wet","Overcast","Sleet","Stormy","Snow","Drizzle","Showers","Pouring","Cats and Dogs","Misty"};
	        String weather = possible_weather[(new Random().nextInt(possible_weather.length))];
	        ((TextView)this.findViewById(R.id.firstTeamPreMatch)).setText(match.team_1.name);
	        ((TextView)this.findViewById(R.id.secondTeamPreMatch)).setText(match.team_2.name);
	        ((TextView)this.findViewById(R.id.preMatchStadium)).setText("at "+match.team_1.stadium_name + " ("+weather+")");
	        
	        List<String> action_list = new ArrayList<String>();
	        Spinner spinner_clothes = (Spinner) findViewById(R.id.spinner_clothes);
	        spinner_clothes.setPrompt("Special Clothes?");
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	                this, R.array.clothes, android.R.layout.simple_spinner_item);
	        
	        Spinner spinner_food = (Spinner) findViewById(R.id.spinner_food);
	        spinner_clothes.setPrompt("Special Prematch Food?");
	        adapter = ArrayAdapter.createFromResource(
	                this, R.array.food, android.R.layout.simple_spinner_item);
	        
	        Spinner spinner_drink = (Spinner) findViewById(R.id.spinner_drink);
	        spinner_clothes.setPrompt("Special Prematch Drink?");
	        adapter = ArrayAdapter.createFromResource(
	                this, R.array.drink, android.R.layout.simple_spinner_item);
	        
	        //TODO Go to match
	        
	        Button go = (Button)this.findViewById(R.id.goKickOff);
	        go.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
	        	  Intent myIntent = new Intent(PreMatchActions.this,PlayerMatchResult.class);
	        	  PreMatchActions.this.startActivity(myIntent);
	          }
	        });
	 }
	 public void onItemsSelected(boolean[] selected){/*shhh ;)*/}
	 
}

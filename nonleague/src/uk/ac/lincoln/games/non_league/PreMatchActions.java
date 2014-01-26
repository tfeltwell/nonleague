package uk.ac.lincoln.games.non_league;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import uk.ac.lincoln.games.non_league.match.Match;

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
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner_clothes.setAdapter(adapter);
	        
	        Spinner spinner_food = (Spinner) findViewById(R.id.spinner_food);
	        spinner_food.setPrompt("Special Prematch Food?");
	        adapter = ArrayAdapter.createFromResource(
	                this, R.array.food, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner_food.setAdapter(adapter);
	        
	        Spinner spinner_drink = (Spinner) findViewById(R.id.spinner_drink);
	        spinner_drink.setPrompt("Special Prematch Drink?");
	        adapter = ArrayAdapter.createFromResource(
	                this, R.array.drink, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner_drink.setAdapter(adapter);
	        
	        Spinner spinner_misc = (Spinner) findViewById(R.id.spinner_misc);
	        spinner_misc.setPrompt("Special Prematch Activity?");
	        adapter = ArrayAdapter.createFromResource(
	                this, R.array.misc, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner_misc.setAdapter(adapter);
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

	 
}

package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.MatchResult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * Show summary of match results. Score, newspaper article, etc.
 * @author bkirman
 *
 */
public class PostMatch extends BaseScreen {
	private MatchResult result;
	private Label home_label,away_label,stadium_label,description_label;
	
	public PostMatch (final NonLeague game) {
		super(game);
		home_label = new Label("[HOME TEAM]: 0",GameState.assets.skin);
		away_label = new Label("[AWAY TEAM]: 0",GameState.assets.skin);
		stadium_label = new Label("at [STADIUM]",GameState.assets.skin);
		description_label = new Label("[DESCRIPTION]",GameState.assets.skin);
		description_label.setWrap(true);
		//description_label.setWidth(200);
		
		table.add(home_label).expandX();
		table.row();
		table.add(away_label).expandX();
		table.row();
		table.add(stadium_label).expandX().right();
		table.row();
		table.add(description_label).expand().left().width(200);
		table.row();
		
		TextButton button = new TextButton("Leave Match", GameState.assets.skin);	

		table.add(button).width(200).height(40);
		table.row();
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				for(MatchResult x : GameState.league.weekly_results) System.out.println(x.toString());//TODO Debug
				game.teamstatus_screen.update();
				game.changeScreen(game.teamstatus_screen);
				
		}
		});
	}
	
	public void setResult(MatchResult mr) {
		result = mr;
		//TODO fill screen content
		home_label.setText(result.match.home.name + ": "+String.valueOf(result.home_goals.size()));
		away_label.setText(result.match.away.name + ": "+String.valueOf(result.away_goals.size()));
		stadium_label.setText("at "+result.match.home.stadium);
		description_label.setText(result.getDescription(GameState.player_team));
		
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

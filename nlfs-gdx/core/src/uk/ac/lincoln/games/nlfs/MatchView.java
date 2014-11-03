package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.Match;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * Display the ongoing match and calculate the results
 * @author bkirman
 *
 */
public class MatchView extends BaseScreen{
	private Match match;
	
	private Label home_label,away_label,stadium_label;
	
	public MatchView (final NonLeague game) {
		super(game);
		home_label = new Label("[HOME TEAM]",game.skin);
		away_label = new Label("[AWAY TEAM]",game.skin);
		stadium_label = new Label("at [STADIUM]",game.skin);
		
		
		table.add(home_label).expandX();
		table.row();
		table.add(away_label).expandX();
		table.row();
		table.add(stadium_label).expandX().right();
		table.row();
		
		//TODO generate the result
		//TODO add timed reveal of scores etc
		
		TextButton button = new TextButton("Leave Match", game.skin);	

		table.add(button).width(200).height(40);
		table.row();
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.postmatch_screen.setResult(match.result);
				game.changeScreen(game.postmatch_screen);
		}
		});
	}
	
	public void setMatch(Match m) {
		match = m;
		match.run();
		//TODO fill screen content
		home_label.setText(match.home.name);
		away_label.setText(match.away.name);
		stadium_label.setText("at "+match.home.stadium);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

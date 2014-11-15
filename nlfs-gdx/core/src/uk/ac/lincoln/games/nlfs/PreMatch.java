package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.Match;
import uk.ac.lincoln.games.nlfs.ui.RitualSelector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen before match where players select the rituals they will use
 * @author bkirman
 *
 */
public class PreMatch extends BaseScreen{
	private Match match;
	
	private Label home_label,away_label,stadium_label;
	
	public PreMatch (final NonLeague game) {
		super(game);
		home_label = new Label("[HOME TEAM]",GameState.assets.skin);
		away_label = new Label("[AWAY TEAM]",GameState.assets.skin);
		stadium_label = new Label("at [STADIUM]",GameState.assets.skin);
		
		
		table.add(home_label).expandX();
		table.row();
		table.add(away_label).expandX();
		table.row();
		table.add(stadium_label).expandX().right();
		table.row().padBottom(5);
		
		RitualSelector clothes_ritual = new RitualSelector("Wearing");
		RitualSelector food_ritual = new RitualSelector("Eating");
		RitualSelector drink_ritual = new RitualSelector("Drinking");
		//clothes_ritual.validate();
		table.add(clothes_ritual.getActor()).expandX().center();
		table.row().padBottom(5);
		table.add(food_ritual.getActor()).expandX().center();
		table.row().padBottom(5);
		table.add(drink_ritual.getActor()).expandX().center();
		table.row();
		
		//TODO rituals here
		TextButton button = new TextButton("Go to Match", GameState.assets.skin);	

		table.add(button).width(200).height(40);
		table.row();
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.matchview_screen.setMatch(match);
				game.changeScreen(game.matchview_screen);
		}
		});
	}
	
	public void setMatch(Match match) {
		this.match = match;
		//TODO set screen content
		home_label.setText(match.home.name);
		away_label.setText(match.away.name);
		stadium_label.setText("at "+match.home.stadium);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.Match;
import uk.ac.lincoln.games.nlfs.ui.RitualSelector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
		//NB remember none of this stuff is in memory yet
		home_label = new Label("[HOME TEAM]",GameState.assets.skin,"teamname");
		away_label = new Label("[AWAY TEAM]",GameState.assets.skin,"teamname");
		stadium_label = new Label("at [STADIUM]",GameState.assets.skin);
		
		
		table.add(home_label).expandX().fillX().left();
		table.row();
		table.add("vs.").center();
		table.row();
		table.add(away_label).expandX().fillX().left();
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
		//Set team names/colours
		home_label.setColor(GameState.assets.skin.getColor(match.home.colour_primary));
		away_label.setColor(GameState.assets.skin.getColor(match.away.colour_primary));
		
		home_label.setText(" "+match.home.name);
		home_label.setStyle(new LabelStyle(GameState.assets.skin.get("teamname", LabelStyle.class)));
		home_label.getStyle().background = GameState.assets.skin.newDrawable("base",GameState.assets.skin.getColor(match.home.colour_base));
		away_label.setText(" "+match.away.name);
		away_label.setStyle(new LabelStyle(GameState.assets.skin.get("teamname", LabelStyle.class)));
		away_label.getStyle().background = GameState.assets.skin.newDrawable("base",GameState.assets.skin.getColor(match.away.colour_base));
		

		
		
		stadium_label.setText("at "+match.home.stadium);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		
	}
}

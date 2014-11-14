package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.logic.Match;

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
		home_label = new Label("[HOME TEAM]",game.skin);
		away_label = new Label("[AWAY TEAM]",game.skin);
		stadium_label = new Label("at [STADIUM]",game.skin);
		
		
		table.add(home_label).expandX();
		table.row();
		table.add(away_label).expandX();
		table.row();
		table.add(stadium_label).expandX().right();
		table.row();
		
		HorizontalGroup hg = new HorizontalGroup();
		hg.addActor(new Label("TEST1",game.skin));
		hg.addActor(new Label("TEST2",game.skin));
		hg.addActor(new Label("TEST3",game.skin));
		hg.addActor(new Label("TEST4",game.skin));
		hg.addActor(new Label("TEST1",game.skin));
		hg.addActor(new Label("TEST2",game.skin));
		hg.addActor(new Label("TEST3",game.skin));
		hg.addActor(new Label("TEST4",game.skin));
		ScrollPane sp = new ScrollPane(hg);
		table.add(sp);
		table.row();
		
		//TODO rituals here
		TextButton button = new TextButton("Go to Match", game.skin);	

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

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
	
	public MatchView (final NonLeague game, Match match) {
		super(game);
		this.match = match;
		//match.run();
		table.add("hello").expandX().height(100f);
		table.row();
		table.add(new Label(match.home.name,game.skin,"stretch")).expandX().height(100.0f);//width(game.viewport.getScreenWidth());
		table.row();
		table.add(new Label(match.away.name,game.skin));
		table.row();
		
		TextButton button = new TextButton("Go to Game", game.skin);	
		table.add(button).width(200).height(40);
		table.row();
		
		//button.setText(match.result.getDescription(match.home));
		
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				
		}
		});
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

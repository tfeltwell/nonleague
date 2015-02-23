package uk.ac.lincoln.games.nlfs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * show season results. Calculate new league in case of promotion/relegation
 * @author bkirman
 *
 */
public class EndOfSeason extends BaseScreen {
	public EndOfSeason(final NonLeague game) {
		super(game);
		table.add("hello").expandX().height(100f);
		table.row();
		
		
		TextButton button = new TextButton("Go to Game", Assets.skin);	
		table.add(button).width(200).height(40);
		table.row();
		
		//button.setText(match.result.getDescription(match.home));
		
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				
		}
		});
	}
	public void update(){}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

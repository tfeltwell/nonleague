package uk.ac.lincoln.games.nlfs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen before match where players select the rituals they will use
 * @author bkirman
 *
 */
public class PreMatch extends BaseScreen{
	public PreMatch (NonLeague game) {
		super(game);
		
		TextButton button = new TextButton("Go to Game", game.skin);
		table.add(button).width(200).height(40);
		table.row();
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

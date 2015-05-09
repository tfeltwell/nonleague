package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;
import uk.ac.lincoln.games.nlfs.ui.LeaguePositionGraph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * show season results. final league positions. Calculate new league in case of promotion/relegation
 * @author bkirman
 *
 */
public class SplashScreen extends BaseScreen {
	
	private class Title extends Actor {
		Texture title_screen = new Texture(Gdx.files.internal("title_screen.png"));
		@Override
        public void draw(Batch batch, float alpha){
            batch.draw(title_screen,0,0);
        }
		
		public Title() {
			addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                	game.changeScreen(game.teamstatus_screen);
                    return true;
                }
            });
		}
	}
	
	public SplashScreen(final NonLeague game) {
		super(game);
		
		stage.clear();
		
		Title title = new Title();
		title.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		title.setTouchable(Touchable.enabled);
		stage.addActor(title);
		
		Gdx.input.setInputProcessor(stage);

	}
	public void update(){
		
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
}

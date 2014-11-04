package uk.ac.lincoln.games.nlfs;

import uk.ac.lincoln.games.nlfs.logic.GameState;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class NonLeague extends Game {
	Texture img;
		
	public Skin skin;
	public FitViewport viewport;
	public GameState state;
	
	
	public TeamStatus teamstatus_screen;//messy architecture but saves on garbage collection
	public PreMatch prematch_screen;
	public MatchView matchview_screen;
	public PostMatch postmatch_screen;
	public LeagueTable leaguetable_screen;
	public EndOfSeason endofseason_screen;
	/**
	 * Actor for background texture.
	 * @author bkirman
	 *
	 */
	public class Background extends Actor {
		Texture bg = new Texture("bg_fill.png");
		
		public Background() {
			super();
			bg.setWrap(TextureWrap.Repeat,TextureWrap.Repeat);
		}
		@Override
		public void draw(Batch batch, float alpha) {
			batch.draw(bg, 0, 0,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());//fill
		}
	}
	
	public void changeScreen(BaseScreen s) {
		this.setScreen(s);
		Gdx.input.setInputProcessor(s.stage);
	}
	
	public void create () {
		viewport = new FitViewport(360,640);
		state = GameState.getGameState();
		skin = new Skin();
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		Pixmap pm2 = new Pixmap(1,1,Format.RGBA8888);
		pm2.setColor(1f, 1f, 1f, 0.3f);
		pm2.fill();
		skin.add("transparent",new Texture(pm2));
				
		Texture texture = new Texture(Gdx.files.internal("titlefont.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		BitmapFont font = new BitmapFont(Gdx.files.internal("titlefont.fnt"), new TextureRegion(texture), false);
		skin.add("titlefont", new LabelStyle(font,Color.WHITE));
		
		skin.add("white", new Texture(pixmap));
		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());
		skin.add("button_up",new Texture("button_up.png"));
		skin.add("button_down",new Texture("button_down.png"));
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle button_style = new TextButtonStyle();
		button_style.font = skin.getFont("default");
		button_style.up = skin.newDrawable("button_up");
		button_style.down = skin.newDrawable("button_down");
				
		skin.add("default", button_style);
		skin.add("default", new LabelStyle(new BitmapFont(),Color.WHITE));
		skin.add("stretch", new LabelStyle(new BitmapFont(),Color.RED));
		
		teamstatus_screen = new TeamStatus(this);
		prematch_screen = new PreMatch(this);
		matchview_screen = new MatchView(this);
		postmatch_screen = new PostMatch(this);
		leaguetable_screen = new LeagueTable(this);
		endofseason_screen = new EndOfSeason(this);
		//start app flow
		teamstatus_screen.update();
		this.changeScreen(teamstatus_screen);
	}

	public void pause () {
		GameState.getGameState().saveGame();
	}

	public void resume () {
	}
	
	public void dispose () {
		skin.dispose();
	}
}

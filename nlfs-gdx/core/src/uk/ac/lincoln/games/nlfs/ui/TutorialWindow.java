package uk.ac.lincoln.games.nlfs.ui;

import uk.ac.lincoln.games.nlfs.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TutorialWindow extends Actor {
	 private Table table;
	 private static int width = 500;
	 private static int height = 800;
	 private Label text;
	 public boolean dismissed = false;

	    public TutorialWindow (String title_text, String body_text, String button_text) {
	        text = new Label(body_text,Assets.skin);
	        text.setWrap(true);
	        table = new Table();
	        
			table.setBackground(Assets.skin.getDrawable("tutorial"));
	        
			table.add(new Label(title_text,Assets.skin,"pagetitle")).center().expandX();
			table.row();
	        table.add(text).fill().expand().top().padLeft(10f);
	        table.row();
	        
	        TextButton button = new TextButton(button_text, Assets.skin);
	        table.add(button).width(380).height(85).center();
			table.row();
			
			
	        table.setWidth(width);
	        table.setHeight(height);
	        
	        
	        table.setPosition((Gdx.graphics.getWidth()/2) - width/2 , (Gdx.graphics.getHeight()/2) - table.getHeight()/2 );
	        setTouchable(Touchable.enabled);
	        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	        addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                	setVisible(false);
                	dismissed = true;
                	return true;
                }
            });
	        
	    }

	    @Override
	    public void draw (Batch batch, float parentAlpha) {
	    	if(!dismissed)
	    		table.draw(batch, parentAlpha);
	    }
	    	
}

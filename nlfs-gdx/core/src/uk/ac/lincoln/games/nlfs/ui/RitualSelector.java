package uk.ac.lincoln.games.nlfs.ui;

import java.util.ArrayList;

import uk.ac.lincoln.games.nlfs.logic.GameState;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * UI Widget that allows user to select a ritual from a given set
 * @author bkirman
 *
 */
public class RitualSelector {
	private WidgetGroup wg;
	private Label selected_label;
	private Table table;
	
	private ButtonGroup button_group;
	private ArrayList<TextButton> buttons;
	private ScrollPane sp;
	
	public RitualSelector(String description) {
		selected_label = new Label("Nothing Special",GameState.assets.skin);
		buttons = new ArrayList<TextButton>();
		for(int i=1;i<8;i++) {//TODO real ones
			buttons.add(new TextButton("TEST"+String.valueOf(i),GameState.assets.skin));
		}
		ButtonGroup bg = new ButtonGroup();
		HorizontalGroup hg = new HorizontalGroup();
		for(TextButton b:buttons) {
			hg.addActor(b);
			b.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					selected_label.setText(((TextButton)actor).getText());
			}
			});
			bg.add(b);
		}
		bg.setMaxCheckCount(1);
		bg.setUncheckLast(true);
		sp = new ScrollPane(hg);
		table = new Table();
		table.setBackground(GameState.assets.skin.getDrawable("darken"));
		table.add(sp).colspan(2);
		table.row();
		table.add(new Label(description+":",GameState.assets.skin)).left().padLeft(10);
		table.add(selected_label).right().expandX().padRight(10);
		//ScrollPane sp = new ScrollPane(hg);
		//this.addActor(sp);
		//this.addActor(selected_label);
	}
	
	public Table getActor() {return table;}
}
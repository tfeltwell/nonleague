package uk.ac.lincoln.games.nlfs.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import uk.ac.lincoln.games.nlfs.Assets;
import uk.ac.lincoln.games.nlfs.logic.Team;

public class TeamLabel extends Label{
	
	public TeamLabel(Team t) {//TODO this should have an option for large or small
		super(t.name,Assets.skin,"teamname");
		
		setStyle(new LabelStyle(Assets.skin.get("teamname", LabelStyle.class)));
		getStyle().background = Assets.skin.newDrawable("base",Assets.skin.getColor(t.colour_base));
		getStyle().fontColor = Assets.skin.getColor(t.colour_primary);			
	}

}

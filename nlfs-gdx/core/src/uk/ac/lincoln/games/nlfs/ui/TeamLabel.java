package uk.ac.lincoln.games.nlfs.ui;

import uk.ac.lincoln.games.nlfs.Assets;
import uk.ac.lincoln.games.nlfs.logic.Team;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TeamLabel extends Label{
	
	public TeamLabel(Team t, String font) {
		super("[PLACEHOLDER]",Assets.skin,font);
		setStyle(new LabelStyle(Assets.skin.get(font, LabelStyle.class)));
		
		if(t!=null) update(t);
		}
	
	/**
	 * Update label element with new team data.
	 * @param t
	 */
	public void update(Team t) {
		this.setText(" "+t.name+" ");
		
		getStyle().background = Assets.skin.newDrawable("base",Assets.skin.getColor(t.colour_base));
		getStyle().fontColor = Assets.skin.getColor(t.colour_primary);			
	}
	
	

}

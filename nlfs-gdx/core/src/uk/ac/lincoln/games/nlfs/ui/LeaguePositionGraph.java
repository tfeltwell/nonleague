package uk.ac.lincoln.games.nlfs.ui;

import java.util.ArrayList;

import uk.ac.lincoln.games.nlfs.logic.League;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * This is a UI texture that shows the league position history for a given team
 * @author ben
 *
 */
public abstract class LeaguePositionGraph {
	
	private static int WIDTH = 240;
	private static int HEIGHT = 40;
	
	public static Texture generateLeaguePositionGraph(ArrayList<Integer> position_history) {
		Pixmap pixmap = new Pixmap(WIDTH,HEIGHT,Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		
		int unit_h = HEIGHT/ League.LEAGUE_SIZE; 
		int unit_w = WIDTH / (2*(League.LEAGUE_SIZE-1));//number of weeks
		
		int cursor_h = 0;
		for(int cursor_w = 0;cursor_w < position_history.size();cursor_w++) {
			pixmap.drawLine(cursor_w*unit_w, cursor_h*unit_h,
							(cursor_w+1)*unit_w, position_history.get(cursor_w)*unit_h);
			cursor_h = position_history.get(cursor_w);
		}
		Texture tex = new Texture(pixmap);
		tex.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		
		return tex;
	}
}

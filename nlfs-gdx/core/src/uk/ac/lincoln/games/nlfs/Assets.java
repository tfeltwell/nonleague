package uk.ac.lincoln.games.nlfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static Skin skin;
	public ArrayList<String> team_names;
	public ArrayList<String> town_names;
	public ArrayList<String> first_names;
	public ArrayList<String> surnames;
	public ArrayList<String> stadium_names;
	public ArrayList<String> road_names;
	public ArrayList<String> league_prefices,league_suffices;
	public ArrayList<ArrayList<String>> team_colours;
	public static HashMap<String,ArrayList<String>> news_summaries;
	
	public static AssetManager manager;
	
	private boolean gen_loaded;
	
	public Assets() {
		gen_loaded = false;
		manager = new AssetManager();
		loadSkin();
		loadRunData();
		
	}
	
	public boolean isGenLoaded() { return gen_loaded;}
	
	/**
	 * Fill data for gen (new leagues, teams, players)
	 */
	public void loadGenData() {
		//get the potential team names and suffixes, first names, last names and stadium names
		try{
			team_names = loadFile("teamnames.txt");
			town_names = loadFile("townnames.txt");
			first_names = loadFile("firstnames.txt");
			surnames = loadFile("surnames.txt");
			road_names = loadFile("roadnames.txt");
			stadium_names = loadFile("stadiumnames.txt");
			league_prefices = loadFile("league_prefix.txt");
			league_suffices = loadFile("league_suffix.txt");
			
			//Team colours
			ArrayList<String> colour_data = loadFile("team_colours.txt");
			
			team_colours = new ArrayList<ArrayList<String>>();
			for(String line: colour_data) {
				ArrayList<String> colour_line = new ArrayList<String>();
				
				colour_line.add(line.split(" on ")[0]);
				colour_line.add(line.split(" on ")[1]);
				
				team_colours.add(colour_line);
				//Gdx.app.log("TEST", colour_line.toString());
			}
		} catch (IOException e) {
            //CRASH TODO: fail gracefully
		}
		
	}
	/**
	 * Fill data based on day-to-day activities
	 */
	public void loadRunData() {
		ArrayList<String> news_data;
		news_summaries = new HashMap<String, ArrayList<String>>();
		try{
			news_data = loadFile("newssummaries.txt");
		} catch (IOException e) {
            //CRASH TODO: fail gracefully
			return;
		}
		String score = "0-0";
		for (String line : news_data) {//fill hashmap with news items associated with particular scores
			if(line.charAt(1)=='-') {//new score
				score = line;
				news_summaries.put(line, new ArrayList<String>());
			}
			else{
				news_summaries.get(score).add(line);//add comment to that line
			}
		}
	}
	
	/**
	 * Load the skin data into memory (must be done before anything is displayed)
	 */
	private void loadSkin() {
		
		manager.load("skin.json",Skin.class);
		//manager.load("darken.png",Texture.class);
		//manager.load("base.png",Texture.class);
		//manager.load("transparent.png",Texture.class);
		
		manager.finishLoading();
		//TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));
		
		skin = manager.get("skin.json");//new Skin(Gdx.files.internal("skin.json"),atlas);
		
		
		Pixmap pm = new Pixmap(1,1,Format.RGBA8888);
		pm.setColor(0f, 0.3f, 0f, 0.95f);
		pm.fill();
		skin.add("tutorial",new Texture(pm));
		
		
		//skin.
		
		//skin.add("darken", manager.get("darken.png"),Texture.class);
		//skin.add("base", manager.get("base.png"),Texture.class);
		//skin.add("transparent", manager.get("transparent.png"),Texture.class);
		
		// Generate a 1x1 white texture andtore it in the skin named "white".
		/*
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		Pixmap pm2 = new Pixmap(1,1,Format.RGBA8888);
		pm2.setColor(1f, 1f, 1f, 0.3f);
		pm2.fill();
		skin.add("transparent",new Texture(pm2));
		pm2 = new Pixmap(1,1,Format.RGBA8888);
		pm2.setColor(1f, 1f, 1f, 0.7f);
		pm2.fill();
		skin.add("base",new Texture(pm2));
		Pixmap pm3 = new Pixmap(1,1,Format.RGBA8888);
		pm3.setColor(0f, 0f, 0f, 0.3f);
		pm3.fill();
		skin.add("darken",new Texture(pm3));
		*/		
		//Texture texture = new Texture(Gdx.files.internal("titlefont.png"));
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//skin.add("default", new BitmapFont());
		
		//skin.add("default", new LabelStyle(new BitmapFont(),Color.WHITE));
		//skin.add("stretch", new LabelStyle(new BitmapFont(),Color.RED));
	}
	
	/**
	 * Load filename into array of strings
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> loadFile(String filename) throws IOException {
		ArrayList<String> out = new ArrayList<String>();
		BufferedReader buffreader = new BufferedReader(Gdx.files.internal(filename).reader());
		String line = buffreader.readLine();
		while(line!=null) {
			out.add(line);
			line = buffreader.readLine();
		}
		buffreader.close();
		return out;
	}

}

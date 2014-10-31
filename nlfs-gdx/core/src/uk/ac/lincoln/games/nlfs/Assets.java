package uk.ac.lincoln.games.nlfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class Assets {
	
	public ArrayList<String> team_names;
	public ArrayList<String> town_names;
	public ArrayList<String> first_names;
	public ArrayList<String> surnames;
	public ArrayList<String> stadium_names;
	public ArrayList<String> road_names;
	public HashMap<String,ArrayList> news_summaries;
	
	private boolean gen_loaded;
	
	public Assets() {
		gen_loaded = false;
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
		} catch (IOException e) {
            //CRASH TODO: fail gracefully
		}
	}
	/**
	 * Fill data based on day-to-day activities
	 */
	public void loadRunData() {
		ArrayList<String> news_data;
		news_summaries = new HashMap<String, ArrayList>();
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

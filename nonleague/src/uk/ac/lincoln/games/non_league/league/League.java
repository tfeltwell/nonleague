package uk.ac.lincoln.games.non_league.league;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import android.content.res.AssetManager;
import android.util.Log;

import uk.ac.lincoln.games.non_league.MainActivity;
import uk.ac.lincoln.games.non_league.match.Match;
import uk.ac.lincoln.games.non_league.match.MatchResult;
import uk.ac.lincoln.games.non_league.team.Team;

/**
 * A league has many teams.
 * 
 * A league has an ordered series of fixtures - matches between these teams
 * 
 * Based on the results of past fixtures, a league table can be built.
 * 
 * @author bkirman
 *
 */
public class League {
	public ArrayList<Match> fixtures;
	public ArrayList<Team> teams;
	public int CursorPos;
	
	public League(ArrayList<String> town_names, ArrayList<String> team_names, ArrayList<String> first_names, ArrayList<String> last_names, ArrayList<String> road_names, ArrayList<String> stadium_names, int league_size){//TODO probably pass the player team in here.
		//build league from X number of teams. Build fixture list.
					
		teams = new ArrayList<Team>();
		fixtures = new ArrayList<Match>();
		String team_name, stadium_name;
		
		for(int i=0;i<league_size;i++) {//note the value here is the size of the league
			team_name = town_names.get((new Random()).nextInt(town_names.size()));//random town name
			if (Math.random()<0.3)
				team_name = team_name +" "+ team_names.get((new Random()).nextInt(team_names.size()));
			stadium_name = stadium_names.get(new Random().nextInt(stadium_names.size())) +" "+ road_names.get(new Random().nextInt(road_names.size()));
			teams.add(new Team(team_name,stadium_name));
		}
		// Generate squads
		for(int i=0;i<teams.size();i++){
			teams.get(i).generateSquad(first_names,last_names);
		}
		
		//build a list of fixtures
		ArrayList<Match> fixtures_a = new ArrayList<Match>();
		ArrayList<Match> fixtures_b = new ArrayList<Match>();
		for(int i=0;i<teams.size();i++){
			Team team_a = teams.get(i);
			for(int j=0;j<teams.size();j++) {
				Team team_b = teams.get(j);
				if (team_a.equals(team_b)) break;
				boolean was_found = false;
				for (int k=0;k<fixtures_a.size();k++) {
					//check fixture not already existing
					if (fixtures_a.get(k).team_1.equals(team_a)&&fixtures_a.get(k).team_2.equals(team_b)){was_found = true; break;}
					if (fixtures_a.get(k).team_1.equals(team_b)&&fixtures_a.get(k).team_2.equals(team_a)){was_found = true; break;}//bk - yeah, i know. it was midnight ok
				}
				if(!was_found) {//oh man this feels wrong
					fixtures_a.add(new Match(team_a,team_b));//home at a
					fixtures_b.add(new Match(team_b,team_a));//home at b
				}
			}
		}
		//ok so now we have a list of all possible fixtures between these teams
		//randomise these fixtures, add them twice in order.
		Collections.shuffle(fixtures_a);
		Collections.shuffle(fixtures_b);
		fixtures.addAll(fixtures_a);
		fixtures.addAll(fixtures_b);
	}
	
	public Match nextFixture(){
		for(int i=0;i<fixtures.size();i++) {
			if(!fixtures.get(i).has_run) return fixtures.get(i); //return first non-run match
		}
		//there are no un-run matches in this league
		return null;
	}
	
	/**
	 * Get current league table (list of items)
	 * @return
	 */
	public ArrayList<LeagueTableItem> getLeagueTable() {
		ArrayList<LeagueTableItem> table = new ArrayList<LeagueTableItem>();
		
		for(int i=0;i<teams.size();i++) {
			table.add(new LeagueTableItem(teams.get(i)));
		}
		for(int i=0;i<fixtures.size();i++) { 
			if(!fixtures.get(i).has_run||fixtures.get(i).result==null)//only run matches go into the table
				break;
			MatchResult result = fixtures.get(i).result;
			//Log.d("bk","Storing result of "+result.team_1.name+" vs "+result.team_2.name);
			for(int j=0;j<table.size();j++) {
				if(table.get(j).team.equals(result.team_1)||table.get(j).team.equals(result.team_2)){
					//Log.d("bk","Adding result to "+table.get(j).team.name);
					table.get(j).calc(result); 
				}
			}
		}
		//order table
		Collections.sort(table);
		return table;
	}
	
	/**
	 * Return 5 match form for this team (WWDDL)
	 * @param t
	 * @return
	 */
	public String getForm(Team t) {
		String form = "-----";
		Iterator<Match> i = fixtures.iterator();
		Match m;
		while(i.hasNext()) {
			m = i.next();
			if (!m.has_run)
				break;//no more past results to check
			if (!m.isTeam(t))
				continue;//not playing
			else {
				form.concat(m.result.resultForTeam(t));
			}
		}
		if(form.length()>5) {
			form = form.substring(form.length()-5);
		}
		return form;
	}
	
	public Team findTeamsNextFixture(Team currentTeam){
		
		for(int i=0;i<fixtures.size();i++){
			if(fixtures.get(i).has_run == false){
				if(fixtures.get(i).team_1 == currentTeam || fixtures.get(i).team_2 == currentTeam){
					if(fixtures.get(i).team_1 == currentTeam){ return fixtures.get(i).team_2; }
					else{ return fixtures.get(i).team_1; }
				}
			}
		}
		return null;
		
	}
}

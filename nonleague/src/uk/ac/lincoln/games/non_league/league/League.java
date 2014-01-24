package uk.ac.lincoln.games.non_league.league;

import java.util.ArrayList;
import java.util.Collections;

import uk.ac.lincoln.games.non_league.match.Match;
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
	private ArrayList<Match> fixtures;
	private ArrayList<Team> teams;
	
	public League(){//TODO probably pass the player team in here.
		//build league from X number of teams. Build fixture list.
		for(int i=0;i<20;i++) {
			teams.add(new Team());
		}
		//build a list of fixtures
		ArrayList<Match> fixtures_a = new ArrayList();
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
				if(!was_found)//oh man this feels wrong
					fixtures_a.add(new Match(team_a,team_b));
			}
		}
		//ok so now we have a list of all possible fixtures between these teams
		//randomise these fixtures, add them twice in order.
		Collections.shuffle(fixtures_a);
		fixtures.addAll(fixtures_a);
		Collections.shuffle(fixtures_a);
		fixtures.addAll(fixtures_a);
	}
	
	public Match nextFixture(){
		for(int i=0;i<fixtures.size();i++) {
			if(!fixtures.get(i).has_run) return fixtures.get(i); //return first non-run match
		}
		//there are no un-run matches
		return null;
	}
}

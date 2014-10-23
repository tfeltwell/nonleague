package uk.ac.lincoln.games.nlfs.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;

import uk.ac.lincoln.games.nlfs.Assets;


/**
 * A league has many teams.
 * 
 * A league has an ordered series of fixtures - matches between these teams
 * 
 * Based on the results of past fixtures, a league table can be built.
 * 
 * Fundamentally, the League (or should it be called Non-League? ;) ) object stores the entire current game state.
 * 
 * @author bkirman
 *
 */
public class League {
	public ArrayList<Match> fixtures;
	public ArrayList<Team> teams;
	public ArrayList<LeagueTableItem> table;
	
	public int CursorPos;
	
	/**
	 * The constructor is used to randomly generate an entire league. This is fairly intense so might need a loading screen
	 * TODO: perhaps take a callback function that is notified of progress.
	 */
	public League(Assets assets){
		if(!assets.isGenLoaded()) assets.loadGenData();//load data files into memory
				
		teams = new ArrayList<Team>();
		fixtures = new ArrayList<Match>();
		
		//Generate teams
		for(int i=0;i<GameState.LEAGUE_SIZE;i++) {
			teams.add(new Team(assets,this));
		}
		
		/*Gdx.app.log("", "League teams:");
		for(Team t:teams)Gdx.app.log("", t.name);*/
		
		//Generate fixtures
		fixtures = generateFixtures(teams);
		updateLeagueTable();
	}
	
	/**
	 * This will generate a new league around the given team. It will take into account promotion and relegation appropriately.
	 * relegation and promotion tell the algorithms how many teams to move from the league at both ends. OBVIOUSLY relegation + promotion must be < LEAGUE_SIZE
	 * @param t
	 */
	public void newSeason(Team team_following,int relegation,int promotion) {
		
		//if the given team is in relegation/promotion zone, them and any other teams move to a new league.
		//otherwise, the teams in the relegation/promotion zone are replaced by new teams
		//then, regenerate fixtures etc.
		
		ArrayList<Team> promoted_teams = new ArrayList<Team>();
		ArrayList<Team> relegated_teams = new ArrayList<Team>();
		ArrayList<Team> league_teams = new ArrayList<Team>();
		
		for(int i=0;i<table.size();i++) {
			if(i<promotion) {
				promoted_teams.add(table.get(i).team);
			} else if (i>=(table.size()-relegation)) {
				relegated_teams.add(table.get(i).team);
			}
			else {
				league_teams.add(table.get(i).team);
			}
		}
		
		teams.clear();
		fixtures.clear();
		
		if(team_following.getLeaguePosition()<promotion) {//promotion
			teams.addAll(promoted_teams);
		} else if(team_following.getLeaguePosition()>=(table.size()-relegation)) {//relegation
			teams.addAll(relegated_teams);
		}
		else {//not a new league
			teams.addAll(league_teams);
		}
		if(!GameState.assets.isGenLoaded()) GameState.assets.loadGenData();//load data files into memory for team generation
		//fill rest of league with teams
		for(int i=(teams.size()-1);i<GameState.LEAGUE_SIZE;i++) {
			teams.add(new Team(GameState.assets,this));//presuming the gamestate object is not currently being constructed (i.e. application starting up) (this would crash the game).
		}
		
		//Generate fixtures
		fixtures = generateFixtures(teams);
		
		updateLeagueTable();
		/*Gdx.app.log("", "League teams:");
		for(Team t:teams)Gdx.app.log("", t.name);*/
	}
	
	/**
	 * Generates and returns an ordered list of fixtures generated for the given teams using a double round robin algorithm.
	 * NB: the number of teams MUST be even and > 2. It will crash otherwise.
	 * @param tournament_teams
	 * @return
	 */
	private ArrayList<Match> generateFixtures(ArrayList<Team> tournament_teams) {
		//Each team plays each other team twice.
		ArrayList<Match> autumn = new ArrayList<Match>();
		ArrayList<Match> spring = new ArrayList<Match>();
		
		//round robin generation
		Team pivot = tournament_teams.get(0);
		Team temp;
		boolean flip = true;
		
		ArrayList<Team> robin = new ArrayList<Team>();
		for(int i=1;i<tournament_teams.size();i++) robin.add(tournament_teams.get(i));
		
		for(int i=0;i<(GameState.LEAGUE_SIZE-1);i++){//N-1 weeks
			if(flip)
				autumn.add(new Match(pivot,robin.get(robin.size()-1)));//pivot plays last team in cycle
			else
				autumn.add(new Match(robin.get(robin.size()-1),pivot));
			for(int j=0;j<((int)GameState.LEAGUE_SIZE/2)-1;j++) {//for each match (N/2 matches per week), -1 we already did
				if(flip)
					autumn.add(new Match(robin.get(j),robin.get(robin.size()-2-j)));//pair match in cyclic fashion (dark maths here, careful)
				else
					autumn.add(new Match(robin.get(robin.size()-2-j),robin.get(j)));
			}
			//rotate cycle
			temp = robin.remove(0);
			robin.add(temp);
			flip = !flip;//ensure some mix of home vs away
		}
		
		//now duplicate but flip home/away for spring season
		for(Match m: autumn) {
			spring.add(new Match(m.away,m.home));
		}
		autumn.addAll(spring);//collate fixtures
		
		return autumn;
	}
	
	/**
	 * return next unplayed fixture
	 * @return
	 */
	public Match nextFixture(){
		for (Match m: fixtures) 
			if(!m.has_run)return m;
		//there are no unplayed matches in this league
		return null;
	}
	
	/**
	 * Update current league table (list of items)
	 * @return
	 */
	public void updateLeagueTable() {
		ArrayList<LeagueTableItem> new_table = new ArrayList<LeagueTableItem>();
		
		for(int i=0;i<teams.size();i++) {
			new_table.add(new LeagueTableItem(teams.get(i)));
		}
		for(int i=0;i<fixtures.size();i++) { 
			if(!fixtures.get(i).has_run||fixtures.get(i).result==null)//only run matches go into the table
				break;
			MatchResult result = fixtures.get(i).result;
			//Log.d("bk","Storing result of "+result.team_1.name+" vs "+result.team_2.name);
			for(int j=0;j<new_table.size();j++) {
				if(new_table.get(j).team.equals(result.team_1)||new_table.get(j).team.equals(result.team_2)){
					//Log.d("bk","Adding result to "+table.get(j).team.name);
					new_table.get(j).calc(result); 
				}
			}
		}
		//order table
		Collections.sort(new_table);
		this.table = new_table;
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
				form = form.concat(m.result.resultForTeam(t));
				//Log.d("DEBUG",form);
			}
		}
		if(form.length()>5) {
			form = form.substring(form.length()-5);
		}
		return form;
	}
	
	public Match findTeamsNextFixture(Team currentTeam){
		
		for(int i=0;i<fixtures.size();i++){
			if(fixtures.get(i).has_run == false){
				if(fixtures.get(i).isTeam(currentTeam)){
					return fixtures.get(i);
				}
			}
		}
		return null;

	}
}

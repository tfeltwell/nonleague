package uk.ac.lincoln.games.nlfs.logic;

/**
 * Class that covers non-goal events that might happen in a match
 * @author bkirman
 *
 */
public class MatchEvent implements Comparable<MatchEvent>{
	public transient Footballer player;
	public transient Match match;
	public MatchEventType type;
	public int minute;
	private String player_id;
	public static enum MatchEventType {REDCARD,YELLOWCARD,KNOCK,WARNING};
	
	public MatchEvent() {}
	
	public MatchEvent(Footballer player, MatchEventType type, Match match, int minute) {
		this.type = type;
		this.player = player;
		this.match = match;
		this.minute = minute;
	}
	
	public String getDescription() {
		if(type==MatchEventType.REDCARD) {
			return(player.getName() + " is sent off! "+player.team.name+" are a man down");
		}
		if(type==MatchEventType.YELLOWCARD) {
			return("Yellow card for "+player.getSurname()+" ("+player.team.name+")");
		}
		if(type==MatchEventType.KNOCK) {
			return(player.getSurname()+" ("+player.team.name+") picks up a knock");
		}
		else {
			return("Ref warns "+player.getSurname()+" ("+player.team.name+")");
		}
	}
	
	/**
	 * Reinit pointers after deserialisation
	 * @param m
	 */
	public void reinit(Match m) {
		this.match = m;
		for(Footballer f : m.home.footballers) {
			if(f.getName().equals(player_id)) {
				player = f;
				return;
			}
		}
		for(Footballer f : m.away.footballers) {
			if(f.getName().equals(player_id)) {
				player = f;
				return;
			}
		}
	}
	//allows them to be sorted by time of event
	public int compareTo(MatchEvent another) {
		return  minute - another.minute;
	}

}

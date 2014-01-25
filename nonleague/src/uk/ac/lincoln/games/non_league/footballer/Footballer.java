package uk.ac.lincoln.games.non_league.footballer;

public class Footballer {
	private String first;
	private String last;
	private int age;
	private String position;
	
	public Footballer(String firstN, String lastN, int newAge, String newPos){
		first = firstN;
		last = lastN;
		age = newAge;
		position = newPos;
	}
	
	public void setPostion(String newPos){ position = newPos;}
	
	public int getAge(){ return age; }
	
	public String getPosition(){ return position; }
	
	public String getFirst(){ return first; }
	
	public String getLast(){ return last; }
	
	public String getFull() { return first + last; }

}

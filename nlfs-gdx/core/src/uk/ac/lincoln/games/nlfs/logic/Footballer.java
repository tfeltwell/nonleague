package uk.ac.lincoln.games.nlfs.logic;

public class Footballer {
	private String first;
	private String last;
	private int age;
	private Position position;
	
	public static enum Position {GK,DF,MF,ST}
		
	public Footballer(String firstN, String lastN, int newAge, Position newPos){
		first = firstN;
		last = lastN;
		age = newAge;
		position = newPos;
	}
	
	public void setPostion(Position newPos){ position = newPos;}
	
	public int getAge(){ return age; }
	
	public Position getPosition(){ return position; }
	
	public String getFirst(){ return first; }
	
	public String getLast(){ return last; }
	
	public String getFull() { return first+" "+last; }

}

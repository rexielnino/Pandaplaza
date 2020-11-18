package pandaplaza.model;

import java.util.*;

public class Game {

	/**
	 * Used in test cases to identify the game.
	 */
	private String name;
	
	/**
	 * Number of pandas currently in the game.
	 */
	private int pandaNum = 0;
	
	/**
	 * The game's timer, which controls the pandas movement.
	 */
	private Timer timer;
	
	
	/**
	 * Players of the game.
	 */
	private List<Player> players = new ArrayList<>();
	public Player getPlayer(int idx) {
		if (players.size() <= idx) {return null;}
		return players.get(idx);
	}
	
	
	public Game() {}
	
	public Timer getTimer() {return timer;}
	public void setTimer(Timer t) {timer = t;}
	public void setPandaNum(int num) {pandaNum = num;}
	public void setName(String n) {name = n;}
	public String getName() {return name;}
	
	private boolean started = false;
	
	public void start(int numofplayers) {
		players.clear();
		for (int i = 0; i<numofplayers; i++) {
			Orangutan or = new Orangutan();
			or.setGame(this);
			players.add(new Player(or));
		}
		started = true;
		timer.Start(1000);
		
	}

	public boolean isGameOver() {
		if (!started || players.size() == 0) {return false;}
		
		if (pandaNum <= 0) {started = false; return true;}
		
		int db = 0;
		for (int i = 0; i<players.size(); i++) {
			if (players.get(i).getOrangutan().getGame() == null) {++db;}
		}
		if (db == players.size()) {started = false; return true;}
		else {return false;}
		
		
	}
	
	public void endGame() {
		// TODO - implement Game.endGame
	}

	/**
	 * Decrease the number of pandas by the given value.
	 * @param i - This value is be substracted by the number of pandas.
	 */
	public void decreasePandaNum(int i) {
		
		pandaNum -= i;
		
		// Later
		/*
		if (pandaNum <= 0) {
			 endGame();
		}
		*/
		
		
	}

	public void increaseTime() {
		// TODO - implement Game.increaseTime
	}

	/**
	 * Adds point to orangutan's player.
	 * @param p - point
	 * @param o - orangutan, whose player will get the point
	 */
	public void addPoints(int p, Orangutan o) {
		for (Player player : players) {
			if (player.getOrangutan() == o) {
				player.addPoints(p);
				break;
			}
		}
	}

	/**
	 * Add player to the player list.
	 * @param p - The new player.
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Game:");
		sb.append(newLine); sb.append(name);
		sb.append(newLine); sb.append("pn: "); sb.append(pandaNum);
		sb.append(newLine); sb.append("t: "); sb.append(timer.getName());
		sb.append(newLine); sb.append("tfs: "); //sb.append(timeFromStart);
		sb.append(newLine); sb.append("ps:");
		for(Player p : players) { sb.append(" "); sb.append(p.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
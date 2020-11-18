package pandaplaza.model;

public class Player {

	/**
	 * Points of the player
	 */
	private int points = 0;
	
	/**
	 * The player controls this orangutan.
	 */
	private Orangutan orangutan = null;
	
	/**
	 * Player's name, used in test cases to identify the player.
	 */
	private String name;
	
	
	public int getPoints() {
		return points;
	}

	public void addPoints(int add) {
		this.points += add;
	}

	public Orangutan getOrangutan() {
		return orangutan;
	}

	public void setOrangutan(Orangutan orangutan) {
		this.orangutan = orangutan;
	}
	
	public String getName() {return name;}
	public void setName(String n) {name = n;}

	public Player(Orangutan or) {orangutan = or;}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Player:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("o: "); sb.append(orangutan.getName());
		sb.append(newLine); sb.append("pts: "); sb.append(points);
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
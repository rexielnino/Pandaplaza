/*
 * Kovács Ákos
 * 2019.03.07 
 */

package pandaplaza.model;

import java.util.List;
import java.util.Random;

public class GameMachine extends Field implements ITickable {


	/* member fields */
	/**
	 *  True, if the GameMachine is ringing, false if not.
	 *  The ringing has effect on the pandas.
	 */
	private boolean ringing = false;
	
	/**
	 * Used to measure the lenght of the ringing state.
	 */
	private int tickCounter = 0;
	private Random random = new Random();  // we use this in tick() method, but one instance is enough
	
	/* getter - setter */
	public boolean isRinging() {
		return ringing; 
	}
	public void setRinging(boolean ringing) { 
		this.ringing = ringing; 
	}

	public int getTickCounter() { 
		return tickCounter; 
	}
	
	public void setTickCounter(int tickCounter) { 
		this.tickCounter = tickCounter; 
	}

	
	public GameMachine() {
		ringing = false;
		tickCounter = 0;
		
	}

	/**
	 *  If the machine is not ringing, then start ringing by a chance (60 %)
	 *  If ringing, call gotScared() method on every neigbour animal.
	 */
	@Override
	public void tick() {
		int randomNum = random.nextInt(100); // [0-99]
		
		if (!ringing && randomNum < 10) {  // 10% chance
			ringing = true;
		}
		
		else if (ringing) {
			List<Field> nbs = getNeighbours();
			for (Field field : nbs) {
				Animal a = field.getAnimal();
				if (a != null) { a.gotScared(); }
			}
			++tickCounter;
			
			if (tickCounter >= 3) {
				ringing = false;
				tickCounter = 0;
			}
		}
		
	}
	
	@Override
	public void accept(Animal a) {
		a.steppedOnGameMachine(this);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();//the result string
		String newline = System.lineSeparator();//new line string
		//building string
		sb.append("GameMachine:");
		sb.append(newline);
		sb.append(this.getName());
		sb.append(newline);
		sb.append("a: ");
		if(this.getAnimal() == null) {sb.append("null");}
		else {sb.append(this.getAnimal().getName());}
		sb.append(newline);
		sb.append("r: ");
		if(ringing)
			sb.append("true");
		else
			sb.append("false");
		sb.append(newline);
		sb.append("tc: ");
		sb.append(tickCounter);
		sb.append(newline);
		sb.append("n:");
		for(Field f : this.getNeighbours()) { sb.append(" "); sb.append(f.getName()); }
		sb.append(newline); sb.append(newline);
		return sb.toString();
	}

}
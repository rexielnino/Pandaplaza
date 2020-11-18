/*
 * Kovács Ákos
 * 2019.03.11
 */

package pandaplaza.model;

import java.util.List;
import java.util.Random;



public class ChocolateMachine extends Field implements ITickable {
	
	/* member fields */
	/**
	 *  True, if the chocolatemachine is whistling, false if not.
	 *  The whistling has effect on the pandas.
	 */
	private boolean whistling = false;
	
	/**
	 * Used to measure the lenght of the whistling state.
	 */
	private int tickCounter  = 0;
	private Random random = new Random();
	
	/* getter-setter */
	public boolean isWhistling() {
		return whistling; 
	}
	public void setWhistling(boolean whistling) {
		this.whistling = whistling; 
	}

	public int getTickCounter() {
		return tickCounter; 
	}
	public void setTickCounter(int tickCounter) {
		this.tickCounter = tickCounter; 
	}

	
	public ChocolateMachine() {
		whistling = false;
		tickCounter = 0;
	}
	
	/**
	 *  If the machine is not whistling, then start whistling by a chance (60 %)
	 *  If whistling, call jump method on every neigbour animal.
	 */
	public void tick() {
		int randomNum = random.nextInt(100); // interval between [0 - 99]
		
		if (!whistling && randomNum < 10) {  // 10% chance
			whistling = true;
		}
		
		else if (whistling) {
			List<Field> nbs = getNeighbours();
			for (Field field : nbs) {
				Animal a = field.getAnimal();
				if (a != null) { a.jump(); }
			}
			++tickCounter;
			
			if (tickCounter >= 3) {
				whistling = false;
				tickCounter = 0;
			}
		}
	}
	
	@Override
	public void accept(Animal a) {
		a.steppedOnChocolateMachine(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("ChocolateMachine:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("a: ");
		if(getAnimal() == null) sb.append("null");
		else sb.append(getAnimal().getName());
		sb.append(newLine); sb.append("w: ");
		if(whistling == true) sb.append("true");
		else sb.append("false");
		sb.append(newLine); sb.append("tc: "); sb.append(tickCounter);
		sb.append(newLine); sb.append("n:");
		for(Field f: getNeighbours()) {sb.append(" "); sb.append(f.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}

}
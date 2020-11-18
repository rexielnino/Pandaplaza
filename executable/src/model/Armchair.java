/*
 * Kovács Ákos
 * 2019.03.08.
 * 
 */
package pandaplaza.model;

import java.util.List;
import java.util.Random;

public class Armchair extends Field implements ITickable {

	private Random rand = new Random();
	
	
	public Armchair() {}
	
	
	/**
	 *  If an animal is sitting in the chair, steps it from the chair to a random neigbour field,
	 */
	public void standUp() {
		Animal a = getAnimal();
		if (a != null) {
			Field f = a.getField();
			List<Field> nbs = f.getNeighbours();
			a.step( nbs.get( rand.nextInt(nbs.size()) )  );
		}
		else {
			return;
		}
	}
	
	/**
	 *  Calls the standUp method with a random chance.
	 */
	public void tick() {
		if (rand.nextInt(100) < 10) {  // 10%
			standUp();
		}
	}
	
	/**
	 *  Sits down the LazyPanda to the chair
	 *  @param lp - LazyPanda
	 */
	@Override
	public void wantToSitDown(LazyPanda lp) {
		if (getAnimal() != null) {
			return;
		}
		lp.getField().setAnimal(null); //before setting field of animal: set old field's animal to null
		lp.setField(this);
		setAnimal(lp);
		lp.setSitting(true);
		lp.setEnergy(2);
		Orangutan leader = lp.getLeader();
		if (leader != null) {
			leader.removePandaFromLine(lp);
		}
	}

	@Override
	public void accept(Animal a) {
		a.steppedOnArmchair(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Armchair:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("a: ");
		if(getAnimal() == null) sb.append("null");
		else sb.append(getAnimal().getName());
		sb.append(newLine); sb.append("n:");
		for(Field f : getNeighbours()) {sb.append(" "); sb.append(f.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
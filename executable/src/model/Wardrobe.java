/*
 * Kovács Ákos
 * 2019.03.12.
 */
package pandaplaza.model;

public class Wardrobe extends Field {

	/**
	 * If an animal enters the wardrobe, it will come out on the pair.
	 */
	private Wardrobe pair;

	public void setPair(Wardrobe wpair) {pair = wpair;}
	public Wardrobe() {}
	
	/**
	 * Teleports an orangutan to the wardrobe pair
	 * @param o - orangutan to be teleported
	 */
	public void teleportOrangutan(Orangutan o) {
		if (!isFree()) { return; }
		else {
			o.setPrevField(o.getField());
			
			o.getField().setAnimal(null);
			o.setField(pair);
			pair.setAnimal(o);
			o.pull(o.getPrevField());
		}
	}
	
	/**
	 * Teleports a panda to the wardrobe pair
	 * @param p - panda to be teleported
	 */
	public void teleportPanda(Panda p) {
		if (!isFree()) {return;}
		else if(p.getLeader() != null) {
			p.getField().setAnimal(null);
			p.setField(this);
			this.setAnimal(p);
		}
		else {
			p.getField().setAnimal(null);
			p.setField(pair);
			pair.setAnimal(p);
		}
	}
	
	@Override
	public void accept(Animal a) {
		a.steppedOnWardrobe(this);
	}
	
	private boolean isFree() {
		if (getAnimal() == null && pair.getAnimal() == null) {return true;}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Wardrobe:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("wp: "); sb.append(pair.getName());
		sb.append(newLine); sb.append("a: ");
		if(getAnimal() == null) sb.append("null");
		else sb.append(getAnimal().getName());
		sb.append(newLine); sb.append("n:");
		for(Field f : getNeighbours()) {sb.append(" "); sb.append(f.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}

}
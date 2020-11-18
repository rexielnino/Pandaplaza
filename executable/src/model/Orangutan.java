/*
 * Kovács Ákos
 * 2019.03.12.
 */
package pandaplaza.model;

import java.util.ArrayList;

public class Orangutan extends Animal {

	/**
	 * List of the pandas in the line.
	 */
	private ArrayList<Panda> pandas = new ArrayList<>();
	
	/**
	 * The orangutan's previously stepped field.
	 */
	private Field prevField = null;
	
	/**
	 * If the cooldown is not 0, then the orangutan can't catch any panda, or can't steal from other orangutan.
	 * The cooldown is reduced by 1 after every step.
	 * If the cooldown is 0, then the orangutan can catch and steal pandas.
	 */
	private int cooldown = 0;
	
	
	public ArrayList<Panda> getPandas() { return pandas; }

	public Field getPrevField() { 
		return prevField; 
	}
	public void setPrevField(Field prevField) { 
		this.prevField = prevField; 
	}
	
	public int getCooldown() {return cooldown;}
	public void setCooldown(int c) {cooldown = c;}
	public void decreaseCooldown() {--cooldown; if (cooldown < 0) {cooldown = 0;}}
	


	public Orangutan() {}

	/**
	 * Removes the panda from the line, and breaks the line after that panda.
	 * @param rpanda - The panda to remove.
	 */
	public void removePandaFromLine(Panda rpanda) {
		
		Timer t = getGame().getTimer();
		int index = pandas.indexOf(rpanda);  // we need the index of the panda. The line will break from that index
		for (int i = index; i<pandas.size(); ++i) {
			pandas.get(i).setLeader(null);         
			t.addTickable(pandas.get(i)); 
		}
		pandas.removeIf(curr -> (curr.getLeader() == null));  // remove the pandas with null leader, they are free
	}

	/**
	 *  Kill's the orangutan, and releases the pandas.
	 */
	@Override
	public void die() {
		pull(prevField);
		if (pandas.size() != 0) {
			removePandaFromLine(pandas.get(0));
		}
		getField().setAnimal(null);
		setField(null);
		setGame(null);
	}

	@Override
	public void steppedOnField(Field f) {
		Animal a = f.getAnimal();
		if (a != null) {
			a.HitByOrangutan(this);
		}
		else {
			prevField = getField();
			getField().setAnimal(null);
			f.setAnimal(this);
			setField(f);
			pull(prevField);
			decreaseCooldown();
			
		}
		
	}

	@Override
	public void steppedOnWeakTile(WeakTile wt) {
		
		Animal a = wt.getAnimal();
		if (a != null) {
			a.HitByOrangutan(this);
		}
		else {
			prevField = getField();
			getField().setAnimal(null);
			wt.setAnimal(this);
			setField(wt);
			decreaseCooldown();
			
			if (wt.getLife() <= 0) {
				die();
			}
			else {
				pull(prevField);
			}
		}
	}

	@Override
	public void steppedOnExit(Exit ex) {
		ex.orangutanExited(this);
		decreaseCooldown();
	}

	@Override
	public void steppedOnWardrobe(Wardrobe w) {
		w.teleportOrangutan(this);
		decreaseCooldown();
	}

	public void addPanda(Panda p) {
		pandas.add(0, p);
		p.setLeader(this);
	}

	/**
	 * The orangutan leads the pandas. 
	 * Pull the whole panda line to the orangutan's previous field.
	 * @param f - The previous field of the orangutan
	 */
	public void pull(Field f) {
		
		// if no panda in line, do nothing
		if (pandas.size() == 0) {
			return;
		} 
		
		// We make a copy of the line
		ArrayList<Panda> tmp = new ArrayList<>();
		tmp.addAll(pandas);
		boolean pandaDied = false;
		int deathIndex = -1;
		Field next = pandas.get(0).getField();
		for (int i = 0; i<pandas.size()-1; ++i) {
			Panda curr = pandas.get(i);
			
			// The panda can die at the step function, breaking the for loop.
			// When the panda dies, the pandas in the line after him are become free.
			// And they are removed from the leader's list too.
			// So we can't pull them after that.
			// But we can detect if a pandas dies, because their game is null-ed if they die.
			// We made a copy of the line above. So if a panda dies, we break out from this loop, and go down to the [else] section.
			curr.step(f);
			if (curr.getGame() == null) {pandaDied = true; deathIndex = i; break;}
			f = next;
			next = pandas.get(i+1).getField();
		}
	
		if (!pandaDied) {
			pandas.get(pandas.size()-1).step(f);
		}
		
		// We remember the pandas, so we can step them one field forward.
		else {
			f = next;
			for (int i = deathIndex+1; i<tmp.size()-1; i++) {
				tmp.get(i).step(f);
				f = next;
				next = tmp.get(i+1).getField();
			}
			tmp.get(tmp.size()-1).step(f);
		}
	}
	
	/**
	 *  The player can release all pandas from the line.
	 */
	public void releasePandas() {
		if (pandas.size() == 0) { return; }
		removePandaFromLine(pandas.get(0));
	}

	/**
	 * Collides to orangutan.
	 */
	@Override
	public void HitByOrangutan(Orangutan o1) {
		if (this.getPandas().size() == 0) {return;}
		if (o1.getCooldown() == 0 && o1.getPandas().size() == 0) {
			Field tmp = o1.getField();
			o1.setField(this.getField());
			o1.setPrevField(tmp); 
			this.setField(tmp);
			this.setPrevField(o1.getField());
			o1.getField().setAnimal(o1);
			this.getField().setAnimal(this);
			
			
			for (Panda panda : pandas) {
				o1.addPanda(panda);
			}
			this.pandas.clear();
			cooldown = 3;

		}
	}

	@Override
	public void HitByPanda(Panda p) {
		
		return;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Orangutan:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("g: "); 
		if (getGame() == null) {sb.append("null");}
		else {
		sb.append(getGame().getName());
		}
		
		sb.append(newLine); sb.append("f: ");
		if(getField() == null) sb.append("null");
		else sb.append(getField().getName());
		sb.append(newLine); sb.append("pf: ");
		if(prevField == null) sb.append("null");
		else sb.append(prevField.getName());
		sb.append(newLine); sb.append("cd: "); sb.append(cooldown);
		sb.append(newLine); sb.append("p:");
		for(Panda p : pandas) { sb.append(" "); sb.append(p.getName()); }
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
	

}
package pandaplaza.model;

import java.util.List;
import java.util.Random;

public class Panda extends Animal implements ITickable {

	/**
	 * If the panda is in a line, the leader orangutan is stored here.
	 */
	private Orangutan leader = null;
	private Random rand = new Random(); // used in tick(), don't want to create new instance every time we call tick, one is enough
	
	public Panda() {}
	
	public Orangutan getLeader() {return leader;}
	public void setLeader(Orangutan leader) { this.leader = leader;}
	
	@Override
	public void die() {
		getGame().decreasePandaNum(1);
		
		if(leader != null)
			leader.removePandaFromLine(this);
		
		getGame().getTimer().removeTickable(this);
		getField().setAnimal(null);
		setField(null);
		setGame(null);
	}
	
	@Override
	public void HitByPanda(Panda p) {
		return;
	}
	
	/**
	 *  If a panda is hit by an orangutan, then the panda added to the orangutan's line.
	 */
	@Override
	public void HitByOrangutan(Orangutan o) {
		if (o.getCooldown() > 0) {return;}
		if(leader != null) {
			if (o == leader) {return;}
			leader.removePandaFromLine(this);
		}
			
		getGame().getTimer().removeTickable(this);
		o.addPanda(this);
		Field oField = o.getField();   //orangutan's old field
		o.setPrevField(oField);        //orangutan's previous field
		o.setField(getField());        //orangutan's new field is panda's field
		o.getField().setAnimal(o);     //set animal on it
		setField(oField);              //panda's field is orangutan's old field
		getField().setAnimal(this);    //set animal on it
	}
	
	@Override
	public void steppedOnField(Field f) {
		
		Animal a = f.getAnimal();
		if(a != null)
			a.HitByPanda(this);
		else
		{
			f.setAnimal(this);           //new field's animal = this
			getField().setAnimal(null);  //no animal on old field
			setField(f);                 //set animal's field to new field
			
		}
	}
	
	@Override
	public void steppedOnWardrobe(Wardrobe w)  {
		w.teleportPanda(this);
	}
	
	@Override
	public void steppedOnWeakTile(WeakTile wt)  {
		Animal a = wt.getAnimal();
		if(a != null) {
			a.HitByPanda(this);
		}
		else {
			wt.setAnimal(this);
			getField().setAnimal(null);
			setField(wt);
			
			if (wt.getLife() <= 0) {
				die();
			}
		}
	}
	
	public void tick()  {
		List<Field> nbs = getField().getNeighbours();
		int n = rand.nextInt(nbs.size()); //n in [0; size of nbs[
		step(nbs.get(n));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Panda:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("g: "); 
		if (getGame() == null) {sb.append("null");}
		else {
			sb.append(getGame().getName());
		}
		
		sb.append(newLine); sb.append("l: ");
		if(leader == null) sb.append("null");
		else sb.append(leader.getName());
		sb.append(newLine); sb.append("f: ");
		if(getField() == null) sb.append("null");
		else sb.append(getField().getName());
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
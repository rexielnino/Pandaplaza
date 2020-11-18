package pandaplaza.model;

import java.util.List;

public class LazyPanda extends Panda {

	/**
	 * The LazyPanda can sit down when it's energy is 0. 
	 * The energy is reduced by 1, after every step.
	 * The energy is set to 2 after the panda stands up from an armchair.
	 */
	private int energy = 2;
	private boolean sitting = false;
	
	public int getEnergy() {
		return energy; 
	}
	
	public void setEnergy(int energy) {
		this.energy = energy; 
	}

	public boolean isSitting() {
		return sitting; 
	}
	
	public void setSitting(boolean sitting) {
		this.sitting = sitting;
	}
	
	public LazyPanda() {}
	
	@Override
	public void steppedOnField(Field f) {
		Animal a = f.getAnimal();
		if(a != null)
			a.HitByPanda(this);
		else
		{
			f.setAnimal(this);
			getField().setAnimal(null);
			setField(f);
			if(energy > 0)
				energy--;
			if (energy <= 0)
			{
				List<Field> nbs = getField().getNeighbours();
				for(Field neighbor : nbs)
				{
					if(!sitting)
						neighbor.wantToSitDown(this);
				}
			}
		}
		
	}
	
	@Override
	public void steppedOnWardrobe(Wardrobe w)  {
		w.teleportPanda(this);
		if(energy > 0)
			energy--;
		if (energy <= 0)
		{
			List<Field> nbs = getField().getNeighbours();
			for(Field neighbor : nbs)//foreach
			{
				if(!sitting)
					neighbor.wantToSitDown(this);
			}
		}
	}
	
	@Override
	public void steppedOnWeakTile(WeakTile wt)  {
		Animal a = wt.getAnimal();
		if(a != null)
			a.HitByPanda(this);
		else
		{
			wt.setAnimal(this);
			getField().setAnimal(null);
			setField(wt);
			if (wt.getLife() <= 0) {die();}
			else {
				if(energy > 0)
					energy--;
				if (energy <= 0)
				{
					List<Field> nbs = getField().getNeighbours();
					for(Field neighbor : nbs)//foreach
					{
						if(!sitting)
							neighbor.wantToSitDown(this);
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("LazyPanda:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("g: "); sb.append(getGame().getName());
		sb.append(newLine); sb.append("l: ");
		if(getLeader() == null) sb.append("null");
		else sb.append(getLeader().getName());
		sb.append(newLine); sb.append("f: ");
		if(getField() == null) sb.append("null");
		else sb.append(getField().getName());
		sb.append(newLine); sb.append("e: "); sb.append(energy);
		sb.append(newLine); sb.append("s: ");
		if(sitting == true) sb.append("true");
		else sb.append("false");
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
	
}
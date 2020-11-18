package pandaplaza.model;

public class WeakTile extends Field {

	/**
	 * If the life is 0, the weaktile is broken, and kills the animal is it steps on it.
	 * The life is reduced by 1 after an animal steppend on the weaktile.
	 */
	private int life = 20;
	
	public int getLife() {return life;}
	public void setLife(int l) {  life = l; }
	public WeakTile() {}
	
	@Override
	public void accept(Animal a) {
		a.steppedOnWeakTile(this);
	}

	@Override
	public void animalJumped(Animal a) {
		--life;
		if(life <= 0) {
			a.die();
		}
	}
	
	@Override
	public void setAnimal(Animal a) {
		super.setAnimal(a);
		--life;
		// makes testing easier, because we know the minimum value, it's 0. Otherwise we would not know the exact value, only that it is <= 0
		// But then what to expect at the test output?
		if (life <= 0) {life = 0;} 
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(); //the result string
		String newline = System.lineSeparator(); //new line string
		//building string
		sb.append("WeakTile:");
		sb.append(newline);
		sb.append(this.getName());
		sb.append(newline);
		sb.append("a: ");
		if(this.getAnimal() == null) {sb.append("null");}
		else {sb.append(this.getAnimal().getName());}
		sb.append(newline);
		sb.append("l: ");
		sb.append(life);
		sb.append(newline);
		sb.append("n:");
		for(Field f : this.getNeighbours()) { sb.append(" "); sb.append(f.getName()); }
		sb.append(newline); sb.append(newline);
		return sb.toString();
	}
}
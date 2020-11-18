package pandaplaza.model;

public class Entrance extends Field {

	public Entrance() {}
	
	public void enterToField(Orangutan o) {
		o.getField().setAnimal(null);
		o.setField(this);
		setAnimal(o);
	}
	@Override
	public void accept(Animal a) {
		a.steppedOnEntrance(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Entrance:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("a: ");
		if(getAnimal() == null) sb.append("null");
		else sb.append(getAnimal().getName());
		
		sb.append(newLine);sb.append("n:");
		for(Field f : getNeighbours()) {sb.append(" "); sb.append(f.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}

}
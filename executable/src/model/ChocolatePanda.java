package pandaplaza.model;

public class ChocolatePanda extends Panda {
	
	public ChocolatePanda() {}
	
	@Override
	public void jump() {
		getField().animalJumped(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("ChocolatePanda:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("g: "); 
		
		if (getGame() == null) {
			sb.append("null");
		}
		else {
			sb.append(getGame().getName());
		}
		
		sb.append(newLine); sb.append("l: ");
		if(getLeader() == null) sb.append("null");
		else sb.append(getLeader().getName());
		sb.append(newLine); sb.append("f: ");
		if(getField() == null) sb.append("null");
		else sb.append(getField().getName());
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
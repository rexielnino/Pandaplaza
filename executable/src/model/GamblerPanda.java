package pandaplaza.model;

public class GamblerPanda extends Panda {
	
	public GamblerPanda() {}
	
	/**
	 *  If a GamblerPanda is scared, he breaks the line.
	 */
	@Override
	public void gotScared() {
		if(getLeader() != null) {
			getLeader().removePandaFromLine(this);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("GamblerPanda:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("g: "); sb.append(getGame().getName());
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
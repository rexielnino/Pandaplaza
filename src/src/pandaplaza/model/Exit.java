/*
 * Kovács Ákos
 * 2019.03.11
 */

package pandaplaza.model;

import java.util.ArrayList;

public class Exit extends Field {

	/**
	 * The Exit has an Entrance pair. If the orangutan exits the field, it will come back on the entrance.
	 */
	private Entrance entrancePair = null;
	
	/**
	 *  We need to know the game, to add points to the players.
	 */
	private Game game = null;

	public Entrance getEntrance() { 
		return entrancePair; 
	}

	public void setEntrance(Entrance entrancePair) {
		this.entrancePair = entrancePair;
	}

	public Game getGame() { return game; 	}
	public void setGame(Game game) { this.game = game; }
	public Exit() {}
	
	/**
	 * Remove the pandas from the game, and add points to player accordingly.
	 * @param o - The orangutan who is exited.
	 */
	public void orangutanExited(Orangutan o) {
						
		// o.getPanda() is not working here, because get a reference to the orangutan's list
		// but the orangutan's list is modified in removePandaFromLine, (we remove the pandas from the list)
		// Because of the reference, this method's list would be empty too.
		// But we need the pandas to kill them :)
		// We create a new List, then append the pandas
		ArrayList<Panda> pandas = new ArrayList<>();
		pandas.addAll(o.getPandas());
		
		// game.decreasePandaNum(pandas.size());       We will do that in panda.die(), which we call below.
		
		int pont = (int) (pandas.size() * 10 + 50*Math.pow(1.08, -(double)game.getTimer().getTimeFromStartSec())); 
		game.addPoints(pont, o);
		
		if (pandas.size() != 0) {  
			o.removePandaFromLine(pandas.get(0));  
		}   	                                   
		for (Panda panda : pandas) {
			panda.die();         
		}
		
		entrancePair.enterToField(o);
		
	}
	
	@Override
	public void accept(Animal a) {
		a.steppedOnExit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Exit:");
		sb.append(newLine); sb.append(getName());
		sb.append(newLine); sb.append("g: "); sb.append(getGame().getName());
		
		sb.append(newLine); sb.append("a: ");
		if (getAnimal() == null) sb.append("null");
		else {sb.append(getAnimal().getName());}
		
		sb.append(newLine); sb.append("ep: ");
		if(entrancePair == null) sb.append("null");
		else sb.append(entrancePair.getName());
		sb.append(newLine); sb.append("n:");
		for(Field f : getNeighbours()) {sb.append(" "); sb.append(f.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
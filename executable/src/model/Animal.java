package pandaplaza.model;

public abstract class Animal {

	/* member field */
	
	/**
	 *  The animal stands on this field.
	 */
	private Field field;
	
	/**
	 * The rotation of the animal measured in degrees from 0 to 360. 
	 * 0 is when the face is pointin up.
	 */
	private double angle;

	/**
	 * Retruns the animal's rotation.
	 * @return Rotation in degrees from 0 to 360.
	 */
	public double getAngle() {
		double ret = angle%360;
		if (ret < 0) {ret = 360+ret;}
		return ret; 
	}
	public void setAngle(double angle) { this.angle = angle; }
	
	
	/**
	 *  The animal is part of this game.
	 */
	private Game game;
	
	/**
	 *  The animal's name, used by test cases to identify the animal.
	 */
	private String name;
	
    /* getter - setter */
	public Field getField()           {return field;  }
	public void setField(Field field) { this.field = field;   }
	
	public Game getGame()          { return game; }
	public void setGame(Game game) {  this.game = game; }
	
	public String getName() {return name;}
	public void setName(String n) {name = n;}
	
	/* methods */
	public void die() {};
	
	/**
	 * Step the animal to desired field.
	 * @param to - Field, the animal will step there, if it is not occupied.
	 */
	public void step(Field to) {
		if (to == null || this == null) {return;}
		to.accept(this);
	};
	
	public void steppedOnField(Field f) {}
	public void steppedOnWeakTile(WeakTile wt) {}
	public void steppedOnChocolateMachine(ChocolateMachine cm) {}
	public void steppedOnArmchair(Armchair am) {}
	public void steppedOnExit(Exit ex) {}
	public void steppedOnGameMachine(GameMachine gm) {}
	public void steppedOnEntrance(Entrance ent) {}
	public void steppedOnWardrobe(Wardrobe w) {}
	public void HitByOrangutan(Orangutan o) {}
	public void HitByPanda(Panda p) {}
	public void gotScared() {}
	public void jump() {}

}
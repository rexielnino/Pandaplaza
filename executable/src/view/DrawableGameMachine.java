package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.GameMachine;

/**
 * Represents a drawable game machine.
 * @author Akos
 *
 */
public class DrawableGameMachine extends DrawableField {

	/**
	 * GameMachine to be drawn.
	 */
	private GameMachine gameMachine;
	/**
	 * Image for the gam machine in non-ringing and ringing state.
	 */
	private Image image, imageActive;
	
	public DrawableGameMachine(Color c, GameMachine a) {
		super(c);
		gameMachine = a; 
		image = new Image(this.getClass().getResourceAsStream("/gamemachine/gamemachine.png"));
		imageActive = new Image(this.getClass().getResourceAsStream("/gamemachine/gamemachineActive.png"));
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, gameMachine.getBorderWidth());
		
		if (gameMachine.isRinging()) {
			DrawUtils.DrawImage(gc, imageActive, 0.25, gameMachine.getCenter());
		}
		else {
			DrawUtils.DrawImage(gc, image, 0.25, gameMachine.getCenter());
		}
	}
}

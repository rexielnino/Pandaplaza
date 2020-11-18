package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.ChocolateMachine;

/**
 * Represents a drawable chocolate machine.
 * @author Akos
 *
 */
public class DrawableChocolateMachine extends DrawableField {
	/**
	 * ChocolateMachine to be drawn.
	 */
	private ChocolateMachine chocolateMachine;
	/**
	 * Images for the non whistling and whistling machine.
	 */
	private Image image, imageActive;
	
	public DrawableChocolateMachine(Color c, ChocolateMachine a) {
		super(c);
		chocolateMachine = a; 
		image = new Image(this.getClass().getResourceAsStream("/chocolatemachine/chocolatemachine.png"));
		imageActive = new Image(this.getClass().getResourceAsStream("/chocolatemachine/chocolatemachineActive.png"));
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, chocolateMachine.getBorderWidth());
		if (chocolateMachine.isWhistling()) {
			DrawUtils.DrawImage(gc, imageActive, 0.25, chocolateMachine.getCenter());
		}
		else {
			DrawUtils.DrawImage(gc, image, 0.25, chocolateMachine.getCenter());
		}
	}

}

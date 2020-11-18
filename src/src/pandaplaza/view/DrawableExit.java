package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.Exit;

/**
 * Represents a drawable exit.
 * @author Akos
 *
 */
public class DrawableExit extends DrawableField {

	/**
	 * Exit to be drawn
	 */
	private Exit exit;
	/**
	 * Image of the exit.
	 */
	private Image image;
	
	public DrawableExit(Color c, Exit a) {
		super(c);
		exit = a; 
		image = new Image(this.getClass().getResourceAsStream("/exit/exit.png"));
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, exit.getBorderWidth());
		DrawUtils.DrawImage(gc, image, 0.25, exit.getCenter());
	}
}

package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.Entrance;

/**
 * Represents a drawable entrance
 * @author Akos
 *
 */
public class DrawableEntrance extends DrawableField {

	/**
	 * Entrance to be drawn.
	 */
	private Entrance entrance;
	/**
	 * Image of the entrance
	 */
	private Image image;
	
	public DrawableEntrance(Color c, Entrance a) {
		super(c);
		entrance = a; 
		image = new Image(this.getClass().getResourceAsStream("/entrance/entrance.png"));
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, entrance.getBorderWidth());
		DrawUtils.DrawImage(gc, image, 0.25, entrance.getCenter());
	}
}

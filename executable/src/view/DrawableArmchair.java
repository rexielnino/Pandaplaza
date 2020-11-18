package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.Armchair;

/**
 * Represents a drawable armchair.
 * @author Akos
 *
 */
public class DrawableArmchair extends DrawableField {

	/**
	 * Armchair to be drawn.
	 */
	private Armchair armchair;
	/**
	 * Image of the armchair.
	 */
	private Image image;
	
	public DrawableArmchair(Color c, Armchair a) {
		super(c);
		armchair = a; 
		image = new Image(this.getClass().getResourceAsStream("/armchair/armchair.png"));
	}
	
	@Override
    public void draw(GraphicsContext gc) {
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, armchair.getBorderWidth());
		DrawUtils.DrawImage(gc, image, 0.25, armchair.getCenter());
    }
	
}

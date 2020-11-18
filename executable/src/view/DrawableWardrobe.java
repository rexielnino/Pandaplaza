package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.Wardrobe;

/**
 * Represents a drawable wardrobe
 * @author Akos
 *
 */
public class DrawableWardrobe extends DrawableField {

	/**
	 * Wardrobe to be drawn
	 */
	private Wardrobe wardrobe;
	/**
	 * Image of the  wardrobe.
	 */
	private Image image;
	
	public DrawableWardrobe(Color c, Wardrobe a) {
		super(c);
		wardrobe = a; 
		image = new Image(this.getClass().getResourceAsStream("/wardrobe/wardrobe.png"));
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, wardrobe.getBorderWidth());
		DrawUtils.DrawImage(gc, image, 0.25, wardrobe.getCenter());
	}
}

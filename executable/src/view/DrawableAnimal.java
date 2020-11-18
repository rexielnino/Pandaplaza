package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a drawable animal.
 * @author Akos
 *
 */
public abstract class DrawableAnimal implements IDrawable {

	/**
	 * The image of the animal
	 */
	protected Image image;
	
	public DrawableAnimal() {}
	
	@Override
	public abstract void draw(GraphicsContext gc);

	/**
	 * Returns the Zindex of the animal. Anything larger than the fields are acceptable.
	 */
	@Override
	public int getZindex() {
		return 10;
	}
	
	

}

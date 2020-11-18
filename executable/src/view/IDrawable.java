package pandaplaza.view;

import javafx.scene.canvas.GraphicsContext;

/**
 * Provides interface for everything that is drawable by a draw() function.
 * The getZindex method is used to determine an order for the elements, because the animals are
 * on top of everything (field, machines ...).
 * @author Akos
 *
 */
public interface IDrawable {
	public void draw(GraphicsContext gc);
	public int getZindex();
}

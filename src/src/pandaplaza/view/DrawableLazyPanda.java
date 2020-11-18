package pandaplaza.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pandaplaza.model.LazyPanda;


/**
 * Represents a drawble lazy panda
 * @author Akos
 *
 */
public class DrawableLazyPanda extends DrawablePanda {

	/**
	 * LazyPanda to be drawn.
	 */
	private LazyPanda lazyPanda;
	public DrawableLazyPanda(LazyPanda p) {
		image = new Image(this.getClass().getResourceAsStream("/lazypanda/lazypanda.png"));
		lazyPanda = p;
	}


	@Override
	public void draw(GraphicsContext gc) {
		if (lazyPanda.getGame() == null) {return;}
		
		if (lazyPanda.getLeader() != null) {
			int idx = lazyPanda.getLeader().getPandas().indexOf(lazyPanda);
			if (idx != 0) {
				Point2D from = lazyPanda.getField().getCenter();
				Point2D to = lazyPanda.getLeader().getPandas().get(idx-1).getField().getCenter();
		
				DrawUtils.DrawLine(gc, from, to);
			}
		}
		DrawUtils.DrawImage(gc, image, 0.1, lazyPanda.getField().getCenter());
	}

}

package pandaplaza.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pandaplaza.model.Panda;

/**
 * Represents a drawable panda.
 * @author Akos
 *
 */
public class DrawablePanda extends DrawableAnimal {

	/**
	 * Panda to be drawn.
	 */
	private Panda panda;
	
	public DrawablePanda() {}
	
	public DrawablePanda(Panda p) {
		image = new Image(this.getClass().getResourceAsStream("/panda/panda.png"));
		panda = p;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (panda.getGame() == null) {return;}
		
		if (panda.getLeader() != null) {
			int idx = panda.getLeader().getPandas().indexOf(panda);
			if (idx != 0) {
				Point2D from = panda.getField().getCenter();
				Point2D to = panda.getLeader().getPandas().get(idx-1).getField().getCenter();
				DrawUtils.DrawLine(gc, from, to);
			}
		}
		
		DrawUtils.DrawImage(gc, image, 0.1, panda.getField().getCenter());
	}

	
}

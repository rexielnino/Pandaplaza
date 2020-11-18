package pandaplaza.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pandaplaza.model.GamblerPanda;

/**
 * Represents a drawable gambler panda
 * @author Akos
 *
 */
public class DrawableGamblerPanda extends DrawablePanda {

	/**
	 * GamblerPanda to be drawn.
	 */
	private GamblerPanda gamblerPanda;
	public DrawableGamblerPanda(GamblerPanda p) {
		image = new Image(this.getClass().getResourceAsStream("/gamblerpanda/gamblerpanda.png"));
		gamblerPanda = p;
	}

	

	@Override
	public void draw(GraphicsContext gc) {
		if (gamblerPanda.getGame() == null) {return;}
		
		if (gamblerPanda.getLeader() != null) {
			int idx = gamblerPanda.getLeader().getPandas().indexOf(gamblerPanda);
			if (idx != 0) {
				Point2D from = gamblerPanda.getField().getCenter();
				Point2D to = gamblerPanda.getLeader().getPandas().get(idx-1).getField().getCenter();
		
				DrawUtils.DrawLine(gc, from, to);
			}
		}
		DrawUtils.DrawImage(gc, image, 0.1, gamblerPanda.getField().getCenter());
	}
}

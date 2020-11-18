package pandaplaza.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pandaplaza.model.ChocolatePanda;

/**
 * Represents a drawable chocolate panda
 * @author Akos
 *
 */
public class DrawableChocolatePanda extends DrawablePanda {

	/**
	 * ChocolatePanda to be drawn.
	 */
	private ChocolatePanda chocolatePanda;

	public DrawableChocolatePanda(ChocolatePanda p) {
		image = new Image(this.getClass().getResourceAsStream("/chocolatepanda/chocolatepanda.png"));
		chocolatePanda = p;
	}

	@Override
	public void draw(GraphicsContext gc) {
		
		// If the panda is dead, do nothing
		if (chocolatePanda.getGame() == null) {return;}
		
		// If the panda is in a line, draw a line segment between him and the previuous panda.
		if (chocolatePanda.getLeader() != null) {
			int idx = chocolatePanda.getLeader().getPandas().indexOf(chocolatePanda);
			if (idx != 0) {
				Point2D from = chocolatePanda.getField().getCenter();
				Point2D to = chocolatePanda.getLeader().getPandas().get(idx-1).getField().getCenter();
		
				DrawUtils.DrawLine(gc, from, to);
			}
		}
		DrawUtils.DrawImage(gc, image, 0.1, chocolatePanda.getField().getCenter());
	}
	
}

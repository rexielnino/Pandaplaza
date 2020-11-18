package pandaplaza.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pandaplaza.model.Orangutan;

/**
 * Represents a drawable orangutan.
 * @author Akos
 *
 */
public class DrawableOrangutan extends DrawableAnimal {

	/**
	 * Static field used to read the diferrent panda for different players.
	 */
	public static int nr = 1;
	/**
	 * Orangutan to be drawn
	 */
	private Orangutan orangutan;
	public DrawableOrangutan(Orangutan o) {
		image = new Image(this.getClass().getResourceAsStream("/orangutan/orangutan"+nr+".png"));
		orangutan = o;
		nr++;
		if (nr == 3) {nr = 1;} // prevent overflow, we have two images only
	}


	@Override
	public void draw(GraphicsContext gc) {
		if (orangutan.getGame() == null) {return;}
		
		if (orangutan.getPandas().size() != 0) { 
			Point2D lineStart = orangutan.getField().getCenter();
			Point2D lineEnd = orangutan.getPandas().get(0).getField().getCenter();
			DrawUtils.DrawLine(gc, lineStart, lineEnd);
		}
		DrawUtils.DrawImageRotated(gc, image, 0.1, orangutan.getField().getCenter(), orangutan.getAngle());
		
	}


	
}

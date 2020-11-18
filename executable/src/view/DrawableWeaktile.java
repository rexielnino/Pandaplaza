package pandaplaza.view;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pandaplaza.model.WeakTile;

/**
 * Represents a drawable weaktile
 * @author Akos
 *
 */
public class DrawableWeaktile extends DrawableField {
	/**
	 * Weaktile to be drawn.
	 */
	private WeakTile weakTile;
	/**
	 * Images to display the break effect
	 */
	private ArrayList<Image> images = new ArrayList<>();

	public DrawableWeaktile(Color c, WeakTile a) {
		super(c);
		weakTile = a;
		String path = "/weaktile/crack_level";
		for (int i = 1; i <= 4; i++) {
			images.add(new Image(this.getClass().getResourceAsStream(path+i+".png")));
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		Image currentImg = null;
		Color currentColor = color;
		int life = weakTile.getLife();
		// Decide what image to draw based on the life
		if (life <= 0) {
			currentImg = images.get(3);
			currentColor = Color.BLACK;
		}
		else if (life <=5 && life > 0) {
			currentImg = images.get(2);
		}
		else if (life <= 15 && life > 5) {
			currentImg = images.get(1);
		}
		else if (life > 15) {
			currentImg = images.get(0);
		}
		
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), currentColor, Color.BLACK, weakTile.getBorderWidth());
		DrawUtils.DrawImage(gc, currentImg, 0.08, weakTile.getCenter());
		
	}
}

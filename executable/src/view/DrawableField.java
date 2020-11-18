package pandaplaza.view;

import java.util.ArrayList;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pandaplaza.model.Field;

/**
 * Represents a drawable field
 * @author Akos
 *
 */
public class DrawableField implements IDrawable {

	/**
	 * Color of the field
	 */
	protected Color color;
	/**
	 * Field to be drawn
	 */
	private Field field;
	
	protected ArrayList<Point2D> vertices = new ArrayList<>();
	public DrawableField(Color c) { color = c; }
	public DrawableField(Color c, Field f) { color = c; field = f; }

	
	public void addVertex(Point2D p) {
		vertices.add(p);
	}
	public ArrayList<Point2D> getVertices() {return vertices;}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		DrawUtils.DrawPolygon(gc, getXcoords(), getYcoords(), color, Color.BLACK, field.getBorderWidth());
	}
	
	/**
	 * Return the X coordiante of the vertices as an array.
	 */
	protected double[] getXcoords() {
		double x[] = new double[vertices.size()];
		
		for (int i = 0; i<vertices.size(); i++) {
			x[i]= vertices.get(i).getX(); 
		}
		
		return x;
	}
	
	/**
	 * Return the Y coordiante of the vertices as an array. 
	 */
	protected double[] getYcoords() {
		
		double y[] = new double[vertices.size()];
		for (int i = 0; i<vertices.size(); i++) {
			y[i] = vertices.get(i).getY(); 
		}
		
		return y;
	}
	
	/**
	 * Returns the Z index if the field, anything is less than the animal's Z index is acceptable.
	 */
	@Override
	public int getZindex() {
		return 1;
	}
	
	
	
	

	

}

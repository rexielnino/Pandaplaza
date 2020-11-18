package pandaplaza.view;



import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 * Helper class to provide methods for easier drawing.
 * @author Akos
 *
 */
public class DrawUtils {

	/**
	 * Draws a polygon with stroke and border to the given GraphicsContext
	 * @param gc GraphicsContext to draw on
	 * @param xcoords X coordinates of the polygon
	 * @param ycoords Y coordinates of the polygon
	 * @param fill The polygon will be filled with this color
	 * @param stroke The color of the polygon stroke's
	 * @param borderWidth Width of the border
	 */
	public static void DrawPolygon(GraphicsContext gc, double[] xcoords, double[] ycoords, Color fill, Color stroke, double borderWidth) {
		// Save the transformation and othet state of the graphicscontext
		gc.save();
		
		// Setup the colors and borderwidth.
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(borderWidth);
		
		// Draw the filled polygon and the stroke.
		gc.fillPolygon(xcoords, ycoords, xcoords.length);
		gc.strokePolygon(xcoords, ycoords, xcoords.length);
		
		// Restore the state of the graphicscontext.
		gc.restore();
	
	}
	
	/**
	 * Draws image to the graphicsContext to the given center point, and scale.
	 * @param gc GraphicsContext to draw on
	 * @param image Image to draw out
	 * @param scale Scale of the image
	 * @param fieldCenter This point will be the center of the image.
	 */
	public static void DrawImage(GraphicsContext gc, Image image, double scale, Point2D fieldCenter) {
		gc.save();
		
        Scale sc = new Scale(scale, scale);
        Translate ts = new Translate(fieldCenter.getX(), fieldCenter.getY());
        
        gc.transform(ts.getMxx(), ts.getMyx(), ts.getMxy(), ts.getMyy(), ts.getTx(), ts.getTy());
        gc.transform(sc.getMxx(), sc.getMyx(), sc.getMxy(), sc.getMyy(), sc.getTx(), sc.getTy());
        
        gc.drawImage(image, -image.getWidth()/2, -image.getHeight()/2);
        gc.restore();
	}
	/**
	 * Draws image to the graphicsContext to the given center point, and scale.
	 * @param gc GraphicsContext to draw on
	 * @param image Image to draw out
	 * @param scale Scale of the image
	 * @param fieldCenter This point will be the center of the image.
	 * @param angle Angle of image
	 */
	public static void DrawImageRotated(GraphicsContext gc, Image image, double scale, Point2D fieldCenter, double angle) {
		gc.save();
		
        Scale sc = new Scale(scale, scale);
        Translate ts = new Translate(fieldCenter.getX(), fieldCenter.getY());
        Rotate rt = new Rotate(angle, fieldCenter.getX(), fieldCenter.getY());
        
        gc.transform(rt.getMxx(), rt.getMyx(), rt.getMxy(), rt.getMyy(), rt.getTx(), rt.getTy());
        gc.transform(ts.getMxx(), ts.getMyx(), ts.getMxy(), ts.getMyy(), ts.getTx(), ts.getTy());
        gc.transform(sc.getMxx(), sc.getMyx(), sc.getMxy(), sc.getMyy(), sc.getTx(), sc.getTy());
       
        gc.drawImage(image, -image.getWidth()/2, -image.getHeight()/2);
        gc.restore();
	}
	
	/**
	 * Draw line between two points
	 * @param gc GraphicsContext to draw on
	 * @param from Start point of the line
	 * @param to Endpoint of the line
	 */
	public static void DrawLine(GraphicsContext gc, Point2D from, Point2D to) {
		gc.save();
		gc.setStroke(new Color(0.5, 0.5, 0.5, 0.8));
		gc.setLineWidth(5);
		gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
		gc.restore();
	}

}

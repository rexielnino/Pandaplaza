package pandaplaza.controller;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pandaplaza.model.Game;
import pandaplaza.model.Field;
import pandaplaza.model.Orangutan;
import pandaplaza.view.IDrawable;

/**
 * Responsible for handling key inputs, stroring and drawing objects.
 * @author Akos
 *
 */
public class GameController {

	/**
	 * The game is painted on this canvas. It will get the value automatically from the fxml file.
	 */
	@FXML
	private Canvas gameCanvas;

	/**
	 * One keypress rotates the animal with this amount (in degree).
	 */
	private final double angleToAdd = 10;
	
	
	/**
	 * flasg for smooth movement [p1]
	 */
	private boolean a_down = false;
	private boolean d_down = false;
	private boolean w_down = false;
	private boolean r_down = false;

	/**
	 * flasgs for smooth movement [p2]
	 */
	private boolean up_down = false;
	private boolean right_down = false;
	private boolean left_down = false;
	private boolean num1_down = false;

	/**
	 * flags for esc for smooth movement
	 */
	private boolean esc_down = false;
	

	/**
	 * The game, we control.
	 */
	private Game game = null;

	/**
	 * Collection of the drawable objects.
	 */
	private ArrayList<IDrawable> drawables = new ArrayList<>();
	public GameController() {}

	/**
	 * Call draw on every element of the drawable list.
	 */
	public void drawAll() {
		clearCanvas();
		drawables.forEach(e -> e.draw(gameCanvas.getGraphicsContext2D()));

	}

	/**
	 * Add new IDrawable to the list. The elements are ordered by their Zindex variable.
	 * @param d The element to be add, must implement IDrawable
	 */
	public void addDrawable(IDrawable d) {
		// The animals have to be on the top, so insert them accordingly
		if (drawables.isEmpty()) {drawables.add(d); return;}
		for (int i = 0; i<drawables.size(); i++) {
			if (drawables.get(i).getZindex() >= d.getZindex()) {
				drawables.add(i, d);
				return;
			}
		}

	}

	/**
	 * Destroys the game, and clears the drawable list.
	 */
	public void reset() {
		game = null;
		drawables.clear();
		a_down = d_down = w_down = r_down = up_down = right_down = left_down = num1_down = esc_down = false;
	}

	

	/**
	 * Handle the button releases
	 * @param ke
	 */
	@FXML
	private void onKeyReleased(KeyEvent ke){
		if(ke.getCode() == KeyCode.A)
			a_down = false;
		if(ke.getCode() == KeyCode.D)
			d_down = false;
		if(ke.getCode() == KeyCode.W)
			w_down = false;
		if(ke.getCode() == KeyCode.R)
			r_down = false;
		if(ke.getCode() == KeyCode.UP)
			up_down = false;
		if(ke.getCode() == KeyCode.LEFT)
			left_down = false;
		if(ke.getCode() == KeyCode.RIGHT)
			right_down = false;
		if(ke.getCode() == KeyCode.NUMPAD1)
			num1_down = false;
		if(ke.getCode() == KeyCode.ESCAPE)
			esc_down = false;
		processInput();
		drawAll();
	}
	/**
	 * Handle the button presses
	 * @param ke
	 */
	@FXML
	private void onKeyPressed(KeyEvent ke) {
		if(ke.getCode() == KeyCode.A)
			a_down = true;
		if(ke.getCode() == KeyCode.D)
			d_down = true;
		if(ke.getCode() == KeyCode.W)
			w_down = true;
		if(ke.getCode() == KeyCode.R)
			r_down = true;
		if(ke.getCode() == KeyCode.UP)
			up_down = true;
		if(ke.getCode() == KeyCode.LEFT)
			left_down = true;
		if(ke.getCode() == KeyCode.RIGHT)
			right_down = true;
		if(ke.getCode() == KeyCode.NUMPAD1)
			num1_down = true;
		if(ke.getCode() == KeyCode.ESCAPE)
			esc_down = true;
		processInput();
		drawAll();
	}

	/**
	 * draws the movements based on inputs
	 */
	public void processInput(){
		// ESCAPE button -> exit
		if (esc_down) { System.exit(0); }

		// Get the orangutan values
		Orangutan o1, o2; o1 = o2 = null;
		if (game.getPlayer(0) != null) { o1 = game.getPlayer(0).getOrangutan(); }
		if (game.getPlayer(1) != null) { o2 = game.getPlayer(1).getOrangutan(); }
		if (o1 == null  && o2 == null) { return; }


		// PLAYER 1
		// If the key is A and the player 1 is not dead
		if (a_down && o1.getGame() != null) {
			// Set the current field border to 1
			o1.getField().setBorderWidth(1);
			// Set the currently looked field's border to 1
			Field field = o1.getField().getNeighbourByAngle(o1.getAngle());
			if (field != null) {field.setBorderWidth(1);}

			// Modify the angle
			o1.setAngle(o1.getAngle()-angleToAdd);

			// Set the newly looked filed's border to 5.
			field = o1.getField().getNeighbourByAngle(o1.getAngle());
			if (field != null) {field.setBorderWidth(5);}
		}

		if (d_down && o1.getGame() != null) {
			o1.getField().setBorderWidth(1);
			Field field = o1.getField().getNeighbourByAngle(o1.getAngle());
			if (field != null) {field.setBorderWidth(1);}

			o1.setAngle(o1.getAngle()+angleToAdd);


			field = o1.getField().getNeighbourByAngle(o1.getAngle());
			if (field != null) {field.setBorderWidth(5);}
		}
		if (w_down && o1.getGame() != null)  {
			o1.getField().setBorderWidth(1);
			Field field = o1.getField().getNeighbourByAngle(o1.getAngle());
			if (field != null) {
				field.setBorderWidth(1);
				o1.step(field);
			}

		}
		if (r_down && o1.getGame() != null)  { o1.releasePandas(); }



		// PLAYER 2
		if (left_down   && o2 != null && o2.getGame() != null) {
			o2.getField().setBorderWidth(1);
			Field field = o2.getField().getNeighbourByAngle(o2.getAngle());
			if (field != null) {field.setBorderWidth(1);}

			o2.setAngle(o2.getAngle()-angleToAdd);


			field = o2.getField().getNeighbourByAngle(o2.getAngle());
			if (field != null) {field.setBorderWidth(5);}
		}

		if (right_down  && o2 != null && o2.getGame() != null) {
			o2.getField().setBorderWidth(1);
			Field field = o2.getField().getNeighbourByAngle(o2.getAngle());
			if (field != null) {field.setBorderWidth(1);}

			o2.setAngle(o2.getAngle()+angleToAdd);

			field = o2.getField().getNeighbourByAngle(o2.getAngle());
			if (field != null) {field.setBorderWidth(5);}
		}

		if (up_down&& o2 != null && o2.getGame() != null) {
			o2.getField().setBorderWidth(1);
			Field field = o2.getField().getNeighbourByAngle(o2.getAngle());
			if (field != null) {
				field.setBorderWidth(1);
				o2.step(field);
			}

		}
		if (num1_down && o2 != null && o2.getGame() != null) { o2.releasePandas(); }
	}

	/**
	 * Returns whether the game has ended
	 * @return true if the game is ended, false if not.
	 *
	 */
	public boolean isGameOver() {
		return game.isGameOver();
	}

	public void setGame(Game game) { this.game = game; }
	public Game getGame() {return game;}

	/**
	 * Clears the canvas.
	 */
	private void clearCanvas() {
		gameCanvas.getGraphicsContext2D().clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
	}

}
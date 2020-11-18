package pandaplaza.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pandaplaza.model.Field;
import pandaplaza.model.Main;
import pandaplaza.view.DrawableOrangutan;

public class MainMenuController {
	
	/**
	 * Main windows of the application.
	 */
	private Stage window;
	/**
	 * Main class of the application.
	 */
	private Main mainclass;
	/**
	 * The gamecontroller class of the program.
	 */
	private GameController gamecontroller;
	/**
	 * These two fields are set in the map.txt file.
	 * They holds the position of the orangutan at the start.
	 * If one player mode is selected, only the o1Field is used,
	 * but you have to write both in the map file.
	 * If two player mode is selected, then both value is used.
	 */
	private Field o1Field, o2Field;
	public void setO1Field(Field f) {o1Field = f;} 
	public void setO2Field(Field f) {o2Field = f;}
	public MainMenuController(Stage window, Main main, GameController gc) {this.window = window; this.mainclass = main; this.gamecontroller = gc;}
	
	/**
	 * Handles the 'One Player' button click event.
	 * @param event
	 */
	@FXML
	private void oneplayerbtn_click(ActionEvent event) {
		// Switch scene to the playingScene
		window.setScene(mainclass.getPlayingScene());
		window.setFullScreen(true);
		mainclass.getPlayingScene().getRoot().requestFocus();
		
		// Start the game with one player
		gamecontroller.getGame().start(1);
		
		// Put the orangutan on the desired field.
		gamecontroller.getGame().getPlayer(0).getOrangutan().setField(o1Field);
		o1Field.setAnimal(gamecontroller.getGame().getPlayer(0).getOrangutan());
		
		// Create the drawable orangutan object.
		DrawableOrangutan o1 = new DrawableOrangutan(gamecontroller.getGame().getPlayer(0).getOrangutan());
		gamecontroller.addDrawable(o1);
		gamecontroller.drawAll();
		
		// Start the animation timer in the main class.
		mainclass.atimer.start();
	}
	
	/**
	 * Handles the 'Two Players' button click event.
	 * @param event
	 */
	@FXML
	private void twoplayerbtn_click(ActionEvent event) {
		window.setScene(mainclass.getPlayingScene());
		window.setFullScreen(true);
		mainclass.getPlayingScene().getRoot().requestFocus();
		
		gamecontroller.getGame().start(2);
		
		gamecontroller.getGame().getPlayer(0).getOrangutan().setField(o1Field);
		o1Field.setAnimal(gamecontroller.getGame().getPlayer(0).getOrangutan());
		DrawableOrangutan o1 = new DrawableOrangutan(gamecontroller.getGame().getPlayer(0).getOrangutan());
		gamecontroller.addDrawable(o1);
		
		gamecontroller.getGame().getPlayer(1).getOrangutan().setField(o2Field);
		o2Field.setAnimal(gamecontroller.getGame().getPlayer(1).getOrangutan());
		DrawableOrangutan o2 = new DrawableOrangutan(gamecontroller.getGame().getPlayer(1).getOrangutan());
		gamecontroller.addDrawable(o2);
		gamecontroller.drawAll();
		
		mainclass.atimer.start();
	}
	/**
	 * Handles the 'Exit' button click event.
	 * @param event
	 */
	@FXML
	private void exitbtn_click(ActionEvent event) {
	    System.exit(0);
	}
}

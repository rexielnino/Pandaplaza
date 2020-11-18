package pandaplaza.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pandaplaza.model.Main;

public class GameOverController {

	/**
	 * The main windows of the application.
	 */
	private Stage window;
	
	/**
	 * The main class of the application.
	 */
	private Main mainclass;
	
	/**
	 * Label fot the player1 point.
	 */
	@FXML
	public Label player1PointsLabel;
	
	/**
	 * Label for the player2 point.
	 */
	@FXML
	public Label player2PointsLabel;
	
	public GameOverController(Stage window, Main m) {this.window = window; mainclass = m;}
	
	/**
	 * Handles the 'Back to the menu' button click event.
	 * @param e
	 */
	@FXML
	public void backtomenubtn_click(ActionEvent e) {
		window.setScene(mainclass.getMainMenuScene());
		window.setFullScreen(true);
	}

}

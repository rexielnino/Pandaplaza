package pandaplaza.model;


import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pandaplaza.controller.GameController;
import pandaplaza.controller.GameOverController;
import pandaplaza.controller.MainMenuController;
import pandaplaza.view.DrawableOrangutan;

public class Main extends Application {
	/**
	 * The game consists of three scenes.
	 */
	private Scene mainMenuScene, playingScene, gameoverScene;
	
	public Scene getMainMenuScene() { return mainMenuScene; }
	public Scene getPlayingScene() { return playingScene; }
	public Scene getGameoverScene() { return gameoverScene; }
	
	/**
	 * We use animationTimer to refresh the canvas periodically.
	 */
	public AnimationTimer atimer;
	
	
	
	
	@Override
	public void start(Stage window) {
		try {
			
			// Create the controllers
			GameController gameController = new GameController();
			MainMenuController mainMenuController = new MainMenuController(window, this, gameController);
			GameOverController gameOverController = new GameOverController(window, this);
			
			// Load the map from the file
			MapLoader mapLoader = new MapLoader(gameController, mainMenuController);
			mapLoader.ReadMap(this.getClass().getResource("/maps/map1.txt").getPath());
			
			// Load Main Menu
	        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MainMenu.fxml"));
	        loader.setController(mainMenuController);
	        AnchorPane root = (AnchorPane) loader.load();
	        
	        mainMenuScene = new Scene(root);
	        mainMenuScene.getStylesheets().add(getClass().getResource("../view/MainMenu.css").toExternalForm());
	       
	        // Load game scene
	        loader = new FXMLLoader(this.getClass().getResource("../view/PlayingScene.fxml"));
	        loader.setController(gameController);
	        AnchorPane rootGameScene = (AnchorPane) loader.load();
	        
	        playingScene = new Scene(rootGameScene);
	        playingScene.getStylesheets().add(getClass().getResource("../view/PlayingScene.css").toExternalForm());
	        
	        
	        // Load game over scene
	        loader = new FXMLLoader(this.getClass().getResource("../view/GameOverScene.fxml"));
	        loader.setController(gameOverController);
	        AnchorPane rootGameoverScene = (AnchorPane) loader.load();
	        
	        gameoverScene = new Scene(rootGameoverScene);
	        gameoverScene.getStylesheets().add(getClass().getResource("../view/GameOverScene.css").toExternalForm());
	        
	        // Setup the main windows
	        window.setFullScreen(true);
	        window.setFullScreenExitHint(""); // hide the press ESC to exit fullscreen message
	        window.setTitle("Pandapláza");
	        
	        
	        window.setScene(mainMenuScene);
	        window.show();
	       
	        // Animation timer to refresh canvas
	        atimer = new AnimationTimer() {
	            @Override 
	            public void handle(long currentNanoTime) {
	            	
	            	// If we are not in a menu
	            	if (window.getScene() == playingScene) {
	            		gameController.drawAll();
	            		
	            		if (gameController.isGameOver()) {
	            			this.stop();
	            			
	            			// Configure the labels with the points
	            			gameOverController.player1PointsLabel.setText(gameController.getGame().getPlayer(0).getPoints() +" points");
	            			gameOverController.player1PointsLabel.setAlignment(Pos.CENTER);
	            			
	            			Player p2 = gameController.getGame().getPlayer(1);
	            			if (p2 != null) {
	            				gameOverController.player2PointsLabel.setText(p2.getPoints() +" points");
	            			}
	            			else {
	            				gameOverController.player2PointsLabel.setText("-");
	            			}
	            			gameOverController.player2PointsLabel.setAlignment(Pos.CENTER);
	            			
	            			// Switch to the gameoverScene to display the points.
	            			window.setScene(gameoverScene);
	            			window.setFullScreen(true);
	            			
	            			// Reset the orangutan image counter, the gamecontroller, and read the map again
	            			// Therefore the player can start the game again.
	            			try {
	            				DrawableOrangutan.nr = 1;
	            				gameController.reset();
								mapLoader.ReadMap(this.getClass().getResource("/maps/map1.txt").getPath());
								
							} catch (IOException e) { e.printStackTrace(); }
	            		}
	            	}
	            }
	        };
			
	
	        
	        
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

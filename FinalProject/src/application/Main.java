package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * 
 * @author Ola Elmahdi, Ethan Mauk
 * 
 * @version 22.11.29
 * 
 * Banking Application
 * 
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
			Scene scene = new Scene(root,400,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 
			
			javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();	//creates coordinate space	
			primaryStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() -500);		//places window on that space
			primaryStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 800);
			
			primaryStage.getIcons().add(new Image("chase.jpg"));	//adds icon
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Banking Applicaiton");
			primaryStage.show();	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
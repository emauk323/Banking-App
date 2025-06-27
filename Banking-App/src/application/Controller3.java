package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller3 implements Initializable{
	@FXML private Label firstName;
	@FXML private Label lastName;
	@FXML private Label accountName2;
	@FXML private Label dateName;
	@FXML private Label usernameLabel;
	@FXML private Label passwordLabel;
	private Stage stage;
	private Scene scene;
	private static String accountName;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		firstName.setText(FileUtility.getProperty(accountName, "FirstName"));
		lastName.setText(FileUtility.getProperty(accountName, "LastName"));
		accountName2.setText(FileUtility.getProperty(accountName, "accountID"));
		dateName.setText(FileUtility.getProperty(accountName, "Date:").replace(",", " "));
		usernameLabel.setText(accountName);
		passwordLabel.setText(FileUtility.getPassword(accountName));
	}
	
	public void back() throws IOException {
		Controller.handoff("AccountInfo");
		stage = (Stage)dateName.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("AccountInfo.fxml")); 
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
	}
	
	public static void updateUsername(String username) {
		accountName = username;
	}
}

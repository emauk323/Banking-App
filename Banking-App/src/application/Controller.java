package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Controller implements Initializable {
	private Stage stage;
	private Scene scene;
	private static String requestScene;
	private static String accountName;
	private static String username="";
	private static String buttonType;
	@FXML private Button myButton;
	@FXML private CustomTextField usernameField;
	@FXML private CustomPasswordField passwordField;
	@FXML private Button labelAccent;
	@FXML private CustomSceneButton button;
	@FXML private Label myLabel;
	@FXML private CustomPasswordField passwordFieldCreate;
	@FXML private CustomTextField usernameFieldCreate;
	@FXML private CustomTextField firstFieldCreate;
	@FXML private CustomTextField lastFieldCreate;
	@FXML private CheckBox myCheckbox;
	@FXML private CheckBox myCheckbox2;
	@FXML private CustomDynamicLabel myTitle;
	@FXML private CustomDynamicLabel myTitle2;
	@FXML private CustomDynamicLabel myTitle3;
	@FXML private CustomDynamicLabel labelAccounts;
	@FXML private Button viewInfoButton;
	@FXML private CustomDynamicRectangles rectangle2;
	@FXML private CustomDynamicRectangles rectangle3;
	@FXML private CustomDynamicRectangles rectangle4;
	@FXML private CustomDynamicRectangles rectangle5;
	@FXML private CustomDynamicLabel labelBalance;
	@FXML private CustomDynamicLabel labelBalance2;
	@FXML private CustomDynamicLabel labelAccountBalance;
	@FXML private CustomDynamicLabel labelAccount2Balance;
	@FXML private CustomDynamicLabel accountLabel;
	@FXML private CustomDynamicLabel accountLabel2;
	@FXML private Label firstName;
	@FXML private Label lastName;
	@FXML private Label accountName2;
	@FXML private Label dateName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (requestScene == "AccountInfo" ) {
			myTitle2.setText(accountName + "!");
			myTitle3.fadeIn();myTitle2.fadeIn();myTitle.fadeIn();
			String checkingBalance = "$" + FileUtility.updateBalance(username, "checking");
			String savingsBalance = "$" + FileUtility.updateBalance(username, "savings");
			
			if (FileUtility.getProperty(username, "checking:").equals("true") && FileUtility.getProperty(username, "savings:").equals("true")) {
				rectangle4.fadeIn();rectangle3.fadeIn();labelBalance.fadeIn();labelAccountBalance.fadeIn();accountLabel.fadeIn();
				accountLabel.setText("Checkings Account");
				accountLabel2.setText("Savings Account");
				labelAccountBalance.setText(checkingBalance);
				labelAccount2Balance.setText(savingsBalance);
				labelBalance2.fadeIn();labelAccount2Balance.fadeIn();rectangle5.fadeIn();rectangle2.fadeIn();accountLabel2.fadeIn(); //show savings
			}
			else {
				if (FileUtility.getProperty(username, "checking:").equals("true")) {
					rectangle4.fadeIn();rectangle3.fadeIn();labelBalance.fadeIn();labelAccountBalance.fadeIn();accountLabel.fadeIn(); //showing checking
					accountLabel.setText("Checking Account");
					labelAccountBalance.setText(checkingBalance);
					Controller2.updateType("checking");
					buttonType = "checking";
				}
				if (FileUtility.getProperty(username, "savings:").equals("true")) {
					rectangle4.fadeIn();rectangle3.fadeIn();labelBalance.fadeIn();labelAccountBalance.fadeIn();accountLabel.fadeIn(); //showing checking
					accountLabel.setText("Savings Account");
					labelAccountBalance.setText(savingsBalance);
					Controller2.updateType("savings");
					buttonType = "savings";
				}
			}
		}		
	}
	
	public void switchScene(ActionEvent event) throws IOException{
		if (requestScene.equals("BankAccountPage")) {
			if (event.getTarget().toString().contains("id=buttonTransparent1")){
				Controller2.updateType("savings");
			}
			else {
				if (buttonType == "savings") {
					Controller2.updateType("savings");
				}
				else {
					Controller2.updateType("checking");
				}
			}
		}
		Controller3.updateUsername(username);
		Controller2.updateUsername(username);	
		stage = (Stage)myButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(requestScene + ".fxml")); 
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
	}
	
	
	public static void handoff(String event) {
		requestScene = event;
	}
	
	public void signIn() throws IOException, InterruptedException {
		requestScene = "AccountInfo";
		if (!myButton.isDisabled()) {
			if (FileUtility.isValid(usernameField.getText(), passwordField.getPasswordText())){
				username = usernameField.getText();
				accountName = (FileUtility.getProperty(usernameField.getText(), "FirstName"));
				switchScene(null);
			}
			else {
				myLabel.setVisible(true);
			}
		}
	}
	
	public void checkEmpty() {
		if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
			myButton.setDisable(true);
		}
		else {
			myButton.setDisable(false);
		}
	}
	
	public void checkEmptyCreate() {
		if (usernameFieldCreate.getText().isEmpty() 
				|| passwordFieldCreate.getText().isEmpty() 
				|| firstFieldCreate.getText().isEmpty() 
				|| lastFieldCreate.getText().isEmpty()
				|| !myCheckbox.isSelected() && !myCheckbox2.isSelected() ) 
		{
			myButton.setDisable(true);
		}
		else {
			myButton.setDisable(false);
		}
	}
	
	public void createAccount() {
	    Boolean checking = (myCheckbox.isSelected())? true: false;
	    Boolean savings = (myCheckbox2.isSelected())? true: false;
	    
		try {
			FileUtility.createAccount(
					usernameFieldCreate.getText(),
					passwordFieldCreate.getPasswordText(),
					firstFieldCreate.getText(),
					lastFieldCreate.getText(),
					checking, savings
					);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		accountName = usernameFieldCreate.getText();
		alert();
	}
	
	
	public void alert() {
		Image image = new Image(System.getProperty("user.dir") +"//src/chase.jpg");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Account Created");
		alert.setHeaderText("To access your account you will have to sign in");
		alert.setContentText("Would you like to go to the sign in page?");
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();			
		stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() -475);
		stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 600);
		stage.getIcons().add(image);
		if(alert.showAndWait().get()== ButtonType.OK) {	
			try {
				requestScene = "SignIn";
				switchScene(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void alertExit() {
		Image image = new Image(System.getProperty("user.dir") +"//src/chase.jpg");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("You will have to sign in again to access your account");
		alert.setContentText("Are you sure you would like to Sign Out");
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();			
		stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() -500);
		stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 600);
		stage.getIcons().add(image);
		if(alert.showAndWait().get()== ButtonType.OK) {	
			try {
				requestScene = "SignIn";
				buttonType = "checking";
				switchScene(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void exit() {
		Image image = new Image(System.getProperty("user.dir") +"//src/chase.jpg");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You are Exiting the Application!");
		alert.setContentText("Youll make the Developer Sad :(");
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();			
		stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() -500);
		stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 600);
		stage.getIcons().add(image);
		if(alert.showAndWait().get() == ButtonType.OK) {
			System.exit(0);
		}
	}
	
}

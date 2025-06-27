package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller2 implements Initializable{
	private Boolean stopper;
	private Boolean stopper2;
	private Stage stage;
	private Scene scene;
	@FXML private Label balanceAccount;
	static String username = "";
	static String accountType = "checking";
	@FXML private Label desc1;
	@FXML private Label value1;
	@FXML private Label date1;
	@FXML private Label desc2;
	@FXML private Label value2;
	@FXML private Label date2;
	@FXML private Label accountNumber;
	@FXML private Label routingNumber;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String a = FileUtility.updateBalance(username, accountType);
		balanceAccount.setText("$"+a);
		updateHis();
		updateHis2();
		accountNumber.setText(FileUtility.getProperty(username, "accountID"));
		routingNumber.setText(FileUtility.getProperty(username, accountType + "ID"));
	}
	public void back() throws IOException {
		Controller.handoff("AccountInfo");
		stage = (Stage)balanceAccount.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("AccountInfo.fxml")); 
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
	}
	
	public static void updateType(String Type) {
		accountType = Type;
	}
	
	public static void updateUsername(String givenUsername) {
		username = givenUsername;
	}
	@FXML
	public void deposit() throws IOException {
		stopper = false;
		TextInputDialog textInput = new TextInputDialog();
		TextInputDialog textInput2 = new TextInputDialog();
		textInput.setTitle("Deposit Information");
		textInput2.setTitle("Deposit Information");
		textInput2.setHeaderText("Description for Transaction");
		textInput.setHeaderText("Enter The Deposit Amount");
		TextField input2 = textInput2.getEditor();
		Button ok = (Button) textInput2.getDialogPane().lookupButton(ButtonType.OK);
		ok.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (input2.getText().isEmpty()) {
					event.consume();
				}
				else {
					stopper = true;
				}
			}
		});
		EventHandler<KeyEvent> filter = new EventHandler<KeyEvent>(){ 

			@Override
			public void handle(KeyEvent event) {
				String a = event.getCharacter();
				if (Character.isDigit(a.charAt(0)) || input2.getText().length()>=40) {
					event.consume();
				}
			}
			
		};
		input2.addEventFilter(KeyEvent.KEY_TYPED, filter);
		textInput2.showAndWait();		
		TextField input = textInput.getEditor();
		EventHandler<KeyEvent> filter2 = new EventHandler<KeyEvent>(){ 

			@Override
			public void handle(KeyEvent event) {
				String a = event.getCharacter();
				if (Character.isDigit(a.charAt(0))&&input.getText().length()<=8 || (event.getCharacter().equals(".") && !input.getText().contains(a))) {
				}
				else {
					event.consume();
				}
			}
			
		};
		Button ok2 = (Button) textInput.getDialogPane().lookupButton(ButtonType.OK);
		ok2.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {//must have input

			@Override
			public void handle(ActionEvent event) {
				if (input.getText().isEmpty()) {
					event.consume();
					stopper2 = false;
				}
				else {
					stopper2 = true;
				}
			}
		});
		input.addEventFilter(KeyEvent.KEY_TYPED, filter2);
		if (stopper) {
			textInput.showAndWait();	
			if (stopper2) {
				FileUtility.storeTransaction(username, accountType, input2.getText().replace(" ", ",") + " ", String.format("%.2f", Double.valueOf(input.getText())));	
				FileUtility.updateBalance(username, accountType);
				initialize(null, null);
			}
		}
		
	}
	public void withdraw() throws IOException {
		stopper = false;
		TextInputDialog textInput = new TextInputDialog();
		TextInputDialog textInput2 = new TextInputDialog();
		textInput.setTitle("Withdraw Information");
		textInput2.setTitle("Withdraw Information");
		textInput2.setContentText("");
		textInput.setContentText("");
		TextField input2 = textInput2.getEditor();
		textInput2.setHeaderText("Description for Withdrawal");
		Button ok = (Button) textInput2.getDialogPane().lookupButton(ButtonType.OK);
		ok.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (input2.getText().isEmpty()) {
					event.consume();
				}
				else {
					stopper = true;
				}
			}
		});
		EventHandler<KeyEvent> filter = new EventHandler<KeyEvent>(){ 

			@Override
			public void handle(KeyEvent event) {
				String a = event.getCharacter();
				if (Character.isDigit(a.charAt(0)) || input2.getText().length()>=40) {
					event.consume();
				}
			}
			
		};
		input2.addEventFilter(KeyEvent.KEY_TYPED, filter);
		textInput2.showAndWait();		
		textInput.setHeaderText("Enter The Withdrawal Amount");
		TextField input = textInput.getEditor();
		EventHandler<KeyEvent> filter2 = new EventHandler<KeyEvent>(){ 

			@Override
			public void handle(KeyEvent event) {
				String a = event.getCharacter();
				if (Character.isDigit(a.charAt(0))&&input.getText().length()<=8 || (event.getCharacter().equals(".") && !input.getText().contains(a))) {
				}
				else {
					event.consume();
				}
			}
			
		};
		Button ok2 = (Button) textInput.getDialogPane().lookupButton(ButtonType.OK);
		ok2.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (input.getText().isEmpty()) {
					event.consume();
					stopper2 = false;
				}
				else {
					stopper2 = true;
				}
			}
		});
		input.addEventFilter(KeyEvent.KEY_TYPED, filter2);
		if (stopper) {
			textInput.showAndWait();	
			if (stopper2) {
				FileUtility.storeTransaction(username, accountType, input2.getText().replace(" ", ",") + " ", String.format("%.2f", Double.valueOf("-" + input.getText())));	
				FileUtility.updateBalance(username, accountType);
				initialize(null, null);
			}
		}
	}
		
	public void interest() {
		Image image = new Image(System.getProperty("user.dir") +"//src/chase.jpg");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Interest");
		alert.setHeaderText("With yearly interest of 1.25 percent, \nIn a year your balance will be: $" + String.format("%.2f",1.0125*Double.valueOf(FileUtility.updateBalance(username, accountType)))
		+ "\nWhile in 10 years your Balance will be: $" + String.format("%.2f",Math.pow(1.0125, 10)*Double.valueOf(FileUtility.updateBalance(username, accountType))));
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(image);
		alert.showAndWait();
	}
	
	public void updateHis() {
		String a = FileUtility.getHistory(accountType);
		@SuppressWarnings("resource")
		Scanner scnr = new Scanner(a);
		scnr.next();
		scnr.next();
		date1.setText(scnr.next().replace(",", " "));
		desc1.setText(scnr.next().replace(",", " "));
		value1.setText("$" + scnr.next());
	}
	
	public void updateHis2() {
		String a = FileUtility.getHistory2(accountType);
		@SuppressWarnings("resource")
		Scanner scnr = new Scanner(a);
		scnr.next();
		scnr.next();
		date2.setText(scnr.next().replace(",", " "));
		desc2.setText(scnr.next().replace(",", " "));
		value2.setText("$" + scnr.next());
	}
}

package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CustomPasswordField extends TextField{
	TextField textHolder = new TextField(); //'real' textField
	
	public CustomPasswordField() {
		
		this.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				if (event.getCharacter().equals(" ") || getText().length() >= 24) {
					event.consume();	//cancels keytype 
				}
				else {
					int a = getCaretPosition();
					textHolder.setText(textHolder.getText() + event.getCharacter()); //adds typed character
					setText(getText().replaceAll(".", "●"));  //hides text
					positionCaret(a); 	//fixes cursor position after deletion 
				}
	        };
		});	
		this.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {//keyPressed != keyTyped

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.BACK_SPACE) {	//detects backspace
					int a = getCaretPosition(); 	//detects where backspace was
					if (a != 0) {
						String b = textHolder.getText();
						textHolder.setText(b.substring(0,a-1) + b.substring(a, textHolder.getLength())); //adjusts actual textfield according to the hidden
					}
				}
				if (getText().isEmpty()) {
					textHolder.setText(""); //in case of CTRL+SHIFT+BackSapce
				}
			}
		});
	}
	
	public String getPasswordText() {
		return textHolder.getText(); //custom read from
	}
}

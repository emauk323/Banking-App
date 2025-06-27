package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CustomTextField extends TextField{
	
	public CustomTextField() { //custom constructor
		
		this.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCharacter().equals(" ") || getText().length() >= 24) { //limits text input
					event.consume(); //cancels keypress
				}
	        };
		});
	}
}

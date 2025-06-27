package application;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class CustomSceneButton extends Button{
	
	public CustomSceneButton(@NamedArg("sceneName") String scene) { //creates custom identifier
		this.getStyleClass().add("labelAccent");	//loads custom button styling
		
		this.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.handoff(scene);	//gives identifier when pressed
			}
		});
	}
}

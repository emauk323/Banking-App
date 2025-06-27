package application;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CustomDynamicLabel extends Label{

	public void fadeIn() {
		this.getStyleClass().removeAll();
		this.getStyleClass().add("test");
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(500));
		fade.setNode(this);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
	}
	public void fadeOut() {
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(500));
		fade.setNode(this);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.play();
	}
}

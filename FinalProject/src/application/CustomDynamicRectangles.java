package application;

import javafx.animation.FadeTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CustomDynamicRectangles extends Rectangle{
	public void fadeIn() {
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

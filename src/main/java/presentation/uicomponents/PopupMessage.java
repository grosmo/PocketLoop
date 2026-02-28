package presentation.uicomponents;

import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.util.Duration;

public class PopupMessage {        
    public PopupMessage(String text, Node ownerNode, double offsetX, double offsetY, int millis) {
        popupMessageIntern(text, ownerNode, offsetX, offsetY, millis, false);
    }

    public PopupMessage(String text, Node ownerNode, double offsetX, double offsetY, int millis, boolean overlay) {
        popupMessageIntern(text, ownerNode, offsetX, offsetY, millis, overlay);
    }

    private void popupMessageIntern(String text , Node ownerNode, double offsetX, double offsetY, int millis, boolean overlay) {
    	Label msg = new Label(text);
    	msg.setOpacity(0.95);
		msg.setId("popup-message");

    	Popup popup = new Popup();
    	popup.getContent().add(msg);
    	popup.setAutoHide(true);

    	Point2D point = ownerNode.localToScreen(0, ownerNode.getBoundsInLocal().getHeight());
    	if (point != null && !overlay) {
        	popup.show(ownerNode.getScene().getWindow(), point.getX() + offsetX, point.getY() + offsetY);
    	} else {
        	double windowX = ownerNode.getScene().getWindow().getX();
        	double windowY = ownerNode.getScene().getWindow().getY();
        	double windowWidth = ownerNode.getScene().getWindow().getWidth();
        	double windowHeight = ownerNode.getScene().getWindow().getHeight();
        	
        	msg.setMinSize(windowWidth, windowHeight);
        	msg.setPrefSize(windowWidth, windowHeight);
        	msg.setAlignment(Pos.CENTER);
			msg.setFont(new Font(40));
			msg.setWrapText(true);
        	
        	popup.show(ownerNode.getScene().getWindow(), windowX, windowY);
    	}

    	PauseTransition pt = new PauseTransition(Duration.millis(millis));
    	pt.setOnFinished(e -> popup.hide());
    	pt.play();
	}
}

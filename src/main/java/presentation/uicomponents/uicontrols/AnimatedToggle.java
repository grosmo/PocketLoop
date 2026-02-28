package presentation.uicomponents.uicontrols;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimatedToggle extends HBox {
    
    private static final double WIDTH = 50;
    private static final double HEIGHT = 25;
    private static final double DOT_RADIUS = 10;
    
    private final TranslateTransition transition;
    private final Rectangle background;
    private final Circle dot;
    
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    public BooleanProperty selectedProperty() {
        return selected;
    }
    public boolean isSelected() {
        return selected.get();
    }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
    
    public AnimatedToggle() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);        
        this.getStyleClass().add("animated-toggle");
        
        StackPane toggleContainer = new StackPane();
        toggleContainer.setMinSize(WIDTH, HEIGHT);
        toggleContainer.setMaxSize(WIDTH, HEIGHT);
        
        background = new Rectangle(WIDTH, HEIGHT);
        background.setArcWidth(HEIGHT);
        background.setArcHeight(HEIGHT);
        background.setStrokeWidth(1);
        
        dot = new Circle(DOT_RADIUS);
        dot.setTranslateX(-WIDTH/2 + DOT_RADIUS + 5);
        
        transition = new TranslateTransition(Duration.millis(200), dot);
        toggleContainer.getChildren().addAll(background, dot);
        this.getChildren().addAll(toggleContainer);
        toggleContainer.setOnMouseClicked(e -> toggle());        
        selected.addListener((obs, oldVal, newVal) -> updateVisuals(newVal));
        
        updateVisuals(false);
    }
    
    private void toggle() {
        setSelected(!isSelected());
    }
    
    private void updateVisuals(boolean isSelected) {
        if (isSelected) {
            transition.setToX(WIDTH/2 - DOT_RADIUS - 5);
            transition.play();
            background.setFill(Color.web("#00b4a0"));
        } else {
            transition.setToX(-WIDTH/2 + DOT_RADIUS + 5);
            transition.play();
            background.setFill(Color.gray(0.3));
        }
        
        background.setStroke(Color.gray(0.5));
        dot.setFill(Color.WHITE);
    }
}

package presentation.uicomponents.uicontrols;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import presentation.uicomponents.PopupMessage;

public class RotaryKnob extends StackPane {

    private static final double SIZE = 50;
    private static final double KNOB_RADIUS = 20;
    private static final double INDICATOR_LENGTH = 20;
    private static final double START_ANGLE = 135; 
    private static final double TOTAL_ANGLE = 270;
    
    Canvas canvas;
    double lastMouseY;
    boolean dragging = false;
    
    private final double min;
    private final double max;
    
    private final DoubleProperty value = new SimpleDoubleProperty(0);
    public DoubleProperty valueProperty() {
        return value;
    }
    public void setValue(double value) {
        this.value.set(value);
    }
    public double getValue() {
        return value.get();
    }

    public RotaryKnob(double min, double max, double initial) {
        this.min = min;
        this.max = max;
        this.value.set(initial);
        
        this.getStyleClass().add("rotary-knob");
        
        canvas = new Canvas(SIZE, SIZE);
        this.getChildren().add(canvas);
        
        canvas.setOnMousePressed((MouseEvent event) -> this.handleMousePressed(event));
        canvas.setOnMouseDragged((MouseEvent event) -> this.handleMouseDragged(event));
        canvas.setOnMouseReleased((MouseEvent event) -> this.handleMouseReleased(event));
        
        draw();
                
        value.addListener((obs, oldVal, newVal) -> draw());
    }
    
    private void handleMousePressed(MouseEvent event) {
        lastMouseY = event.getSceneY();
        dragging = true;
    }
    
    private void handleMouseDragged(MouseEvent event) {
        if (!dragging) return;
        
        double currentY = event.getSceneY();
        double delta = lastMouseY - currentY;
        double sensitivity = 0.005;
        double valueRange = max - min;
        double valueChange = (delta * sensitivity * valueRange);
        
        double newValue = value.get() + valueChange;
        newValue = Math.max(min, Math.min(max, newValue));
        
        value.set(newValue);
        lastMouseY = currentY;
    }
    
    private void handleMouseReleased(MouseEvent event) {
        dragging = false;
        new PopupMessage("neuer Wert " + String.format("%.2f", getValue()), this, -32, 0, 600);
    }
    
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double centerX = SIZE / 2;
        double centerY = SIZE / 2;
        
        gc.clearRect(0, 0, SIZE, SIZE);
        gc.setStroke(Color.gray(0.3));
        gc.setLineWidth(4);
        gc.strokeArc(
            centerX - KNOB_RADIUS, 
            centerY - KNOB_RADIUS,
            KNOB_RADIUS * 2, 
            KNOB_RADIUS * 2,
            START_ANGLE, 
            -TOTAL_ANGLE, 
            ArcType.OPEN
        );
        
        double normalizedValue = (value.get() - min) / (max - min);
        double valueAngle = TOTAL_ANGLE * normalizedValue;
        
        gc.setStroke(Color.web("#00b4a0"));
        gc.setLineWidth(4);
        gc.strokeArc(
            centerX - KNOB_RADIUS, 
            centerY - KNOB_RADIUS,
            KNOB_RADIUS * 2, 
            KNOB_RADIUS * 2,
            START_ANGLE, 
            -valueAngle, 
            javafx.scene.shape.ArcType.OPEN
        );
        
        gc.setFill(Color.gray(0.2));
        gc.fillOval(centerX - KNOB_RADIUS + 5, centerY - KNOB_RADIUS + 5, 
                   (KNOB_RADIUS - 5) * 2, (KNOB_RADIUS - 5) * 2);
        
        double angle = Math.toRadians(START_ANGLE - valueAngle);
        double indicatorX = centerX + Math.cos(angle) * INDICATOR_LENGTH;
        double indicatorY = centerY - Math.sin(angle) * INDICATOR_LENGTH;
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeLine(centerX, centerY, indicatorX, indicatorY);
    }
}

package presentation.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OscilloscopeView extends BaseView {

    Canvas oscilloscope;
    Slider scaleSlider;
    Label scaleLabel;
    Label samplesLabel;
    
    public OscilloscopeView() {

        oscilloscope = new Canvas(800, 400);
        oscilloscope.getStyleClass().add("oscilloscope-canvas");
        HBox switchViewContainer = createSwitchView();
        switchView.getStyleClass().add("btn-switch-view-base");
        switchView.getStyleClass().add("btn-switch-view-checked");
        viewLabel.setText("Oszilloskop");
        
        samplesLabel = new Label();
        labelBox.getChildren().add(samplesLabel);
        
        scaleLabel = new Label();
        scaleLabel.setFont(new Font(15));
        scaleSlider = new Slider(1.0, 35.0, 8.0);
        scaleSlider.getStyleClass().add("slider-oscilloscope-scale");
        scaleSlider.setMaxWidth(Double.MAX_VALUE);

        HBox scaleBox = new HBox(10, scaleLabel, scaleSlider);
        scaleBox.setAlignment(Pos.CENTER);
        scaleBox.setMaxWidth(Double.MAX_VALUE);
        scaleBox.setPadding(new Insets(0, 10, 0, 10));

        HBox.setHgrow(scaleSlider, Priority.ALWAYS);
        VBox.setVgrow(oscilloscope, Priority.ALWAYS);

        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.ALWAYS);
        column.setMinWidth(0);    
        column.setFillWidth(true);
        this.getColumnConstraints().add(column);

        this.add(switchViewContainer, 0, 0);
        this.add(oscilloscope, 0, 1);
        this.add(scaleBox, 0, 2);
    }
    
    public Canvas getOscilloscope() {
        return oscilloscope;
    }
    
}

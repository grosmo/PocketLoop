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
import presentation.TextHelper;

public class OscilloscopeView extends BaseView {

    Canvas oscilloscope;
    Slider scaleSlider;
    Label scaleLabel;    
    
    public OscilloscopeView() {

        oscilloscope = new Canvas(800, 400);
        oscilloscope.getStyleClass().add(TextHelper.STYLECLASS_OSCILLOSCOPE_CANVAS);
        HBox switchViewContainer = createTopBar();
        switchView.getStyleClass().add(TextHelper.STYLECLASS_BTN_SWITCH_VIEW_BASE);
        switchView.getStyleClass().add(TextHelper.STYLECLASS_BTN_SWITCH_VIEW_CHECKED);
        viewLabel.setText(TextHelper.OSZILLOSKOP);
        
        scaleLabel = new Label();
        scaleLabel.setFont(TextHelper.FONT_15);
        scaleSlider = new Slider(1.0, 35.0, 8.0);
        scaleSlider.getStyleClass().add(TextHelper.STYLECLASS_SLIDE_OSCILLOSCOPE_SCALE);
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

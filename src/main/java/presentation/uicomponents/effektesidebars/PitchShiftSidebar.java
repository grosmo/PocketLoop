package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.uicomponents.uicontrols.RotaryKnob;

public class PitchShiftSidebar extends BaseEffektSidebar {

    RotaryKnob knobPitchShift;

    public PitchShiftSidebar(){
        HBox rotatedLabelBox = createRotatedLabelBox(
            "Pitch", 
            new Insets(0, 0, 20, 20)
        );

        toggleBox = new VBox(
            10, 
            rotatedLabelBox, 
            toggleEffect
        );
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);
        
        List<Pane> pitchList = createRotaryKnobBox(
            "Halbtöne", 
            -12, 12, 0
        );
        VBox pitchBox = (VBox) pitchList.get(0);
        knobPitchShift = (RotaryKnob) pitchList.get(1);

        btnSave = new Button("Speichern");
        btnSave.getStyleClass().add("btn-save-effect");

        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            pitchBox, 
            btnSave
        );
        setBasics();
    }
}

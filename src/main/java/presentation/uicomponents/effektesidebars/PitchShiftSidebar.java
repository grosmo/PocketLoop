package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.TextHelper;
import presentation.uicomponents.uicontrols.RotaryKnob;

public class PitchShiftSidebar extends BaseEffektSidebar {

    RotaryKnob knobPitchShift;

    Label lblKnobNote;

    public PitchShiftSidebar(){

        lblKnobNote = new Label();

        HBox rotatedLabelBox = createRotatedLabelBox(
            new String(),
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
            lblKnobNote, 
            -12, 12, 0
        );
        VBox pitchBox = (VBox) pitchList.get(0);
        knobPitchShift = (RotaryKnob) pitchList.get(1);

        btnSave = new Button(new String());
        btnSave.getStyleClass().add(TextHelper.STYLECLASS_BTN_SAFE);

        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            pitchBox, 
            btnSave
        );
        setBasics();
    }
}

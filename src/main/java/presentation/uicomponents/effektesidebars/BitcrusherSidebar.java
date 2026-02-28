package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.uicomponents.uicontrols.RotaryKnob;

public class BitcrusherSidebar extends BaseEffektSidebar {

    RotaryKnob knobBitDepth;
    RotaryKnob knobSampleRate;

    public BitcrusherSidebar(){
        HBox rotatedLabelBox = createRotatedLabelBox(
            "Bitcrush", 
            new Insets(0, 0, 53, -13)
        );

        toggleBox = new VBox(10, rotatedLabelBox, toggleEffect);
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);

        List<Pane> bitDepthList = createRotaryKnobBox(
            "Bittiefe", 
            1, 16, 8
        );
        VBox bitDepthBox = (VBox) bitDepthList.get(0);
        knobBitDepth = (RotaryKnob) bitDepthList.get(1);

        List<Pane> sampleRateList = createRotaryKnobBox(
            "Samplerate", 
            1, 50, 1
        );
        VBox sampleRateBox = (VBox) sampleRateList.get(0);
        knobSampleRate = (RotaryKnob) sampleRateList.get(1);

        btnSave = new Button("Speichern");
        btnSave.getStyleClass().add("btn-save-effect");

        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            bitDepthBox, 
            sampleRateBox, 
            btnSave
        );
        setBasics();
    }
}

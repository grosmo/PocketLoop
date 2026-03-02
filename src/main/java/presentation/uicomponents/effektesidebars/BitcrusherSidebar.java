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

public class BitcrusherSidebar extends BaseEffektSidebar {

    RotaryKnob knobBitDepth;
    RotaryKnob knobSampleRate;

    Label lblKnobBitDepth;
    Label lblKnobSampleRate;

    public BitcrusherSidebar(){

        lblKnobBitDepth = new Label();
        lblKnobSampleRate = new Label();

        HBox rotatedLabelBox = createRotatedLabelBox(
            new String(), 
            new Insets(0, 0, 53, -13)
        );

        toggleBox = new VBox(10, rotatedLabelBox, toggleEffect);
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);

        List<Pane> bitDepthList = createRotaryKnobBox(
            lblKnobBitDepth, 
            1, 16, 8
        );
        VBox bitDepthBox = (VBox) bitDepthList.get(0);
        knobBitDepth = (RotaryKnob) bitDepthList.get(1);

        List<Pane> sampleRateList = createRotaryKnobBox(
            lblKnobSampleRate, 
            1, 50, 1
        );
        VBox sampleRateBox = (VBox) sampleRateList.get(0);
        knobSampleRate = (RotaryKnob) sampleRateList.get(1);

        btnSave = new Button(new String());
        btnSave.getStyleClass().add(TextHelper.STYLECLASS_BTN_SAFE);

        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            bitDepthBox, 
            sampleRateBox, 
            btnSave
        );
        setBasics();
    }
}

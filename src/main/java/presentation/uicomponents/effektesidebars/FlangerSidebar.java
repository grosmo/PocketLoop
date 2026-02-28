package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.uicomponents.uicontrols.RotaryKnob;

public class FlangerSidebar extends BaseEffektSidebar {

    RotaryKnob knobFlangerRate;
    RotaryKnob knobFlangerDepth;
    RotaryKnob knobFlangerDelay;

    public FlangerSidebar(){

        HBox rotatedLabelBox = createRotatedLabelBox(
            "Flanger", 
            new Insets(0,0,46,-7)
        );

        toggleBox = new VBox(10, rotatedLabelBox, toggleEffect);
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);

        List<Pane> rateList = createRotaryKnobBox(
            "Rate", 
            0, 1, 0.05
        );
        VBox rateBox = (VBox) rateList.get(0);
        knobFlangerRate = (RotaryKnob) rateList.get(1);

        List<Pane> depthList = createRotaryKnobBox(
            "Tiefe", 
            0, 50, 5
        );
        VBox depthBox = (VBox) depthList.get(0);
        knobFlangerDepth = (RotaryKnob) depthList.get(1);

        List<Pane> delayList = createRotaryKnobBox(
            "Delay", 
            0, 500, 5
        );
        VBox delayBox = (VBox) delayList.get(0);
        knobFlangerDelay = (RotaryKnob) delayList.get(1);

        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            rateBox, 
            depthBox, 
            delayBox
        );
        setBasics();
    }
}
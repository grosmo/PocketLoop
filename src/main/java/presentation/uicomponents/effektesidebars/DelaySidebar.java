package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.uicomponents.uicontrols.RotaryKnob;

public class DelaySidebar extends BaseEffektSidebar {

    RotaryKnob knobDelayTime;
    RotaryKnob knobFeedback;

    Label lblKnobDelayTime;
    Label lblKnobFeedback;

    public DelaySidebar(){

        HBox rotatedLabelBox = createRotatedLabelBox(
            new String(), 
            new Insets(0, 0, 25, 13)
        );

        lblKnobDelayTime = new Label();
        lblKnobFeedback = new Label();

        toggleBox = new VBox(10, rotatedLabelBox, toggleEffect);
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);

        List<Pane> delayList = createRotaryKnobBox(
            lblKnobDelayTime, 
            50, 2000, 300
        );
        VBox delayBox = (VBox) delayList.get(0);
        knobDelayTime = (RotaryKnob) delayList.get(1);

        List<Pane> feedbackList = createRotaryKnobBox(
            lblKnobFeedback, 
            0, 0.95, 0.5
        );
        VBox feedbackBox = (VBox) feedbackList.get(0);
        knobFeedback = (RotaryKnob) feedbackList.get(1);

        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            delayBox, 
            feedbackBox
        );
        setBasics();
    }
}

package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import presentation.TextHelper;
import presentation.uicomponents.uicontrols.RotaryKnob;

public abstract class BaseEffektSidebar extends BaseSidebar{

    Label titleLabel;

    public VBox createContentBox(){
        VBox contentBox = new VBox();
        contentBox.setMaxHeight(Double.MAX_VALUE);
        contentBox.setPrefHeight(Double.MAX_VALUE);
        VBox.setVgrow(contentBox, Priority.ALWAYS);
        contentBox.setAlignment(Pos.BOTTOM_CENTER);
        contentBox.setVisible(false);
        contentBox.setManaged(false);
        contentBox.setSpacing(20);
        return contentBox;
    }

    public HBox createRotatedLabelBox(String text, Insets padding){
        titleLabel = new Label(text);
        titleLabel.setFont(TextHelper.FONT_50);
        titleLabel.setRotate(270);
        titleLabel.setPadding(new Insets(0, -50, 0, -50));
        titleLabel.setAlignment(Pos.TOP_LEFT);
        HBox hBox = new HBox(titleLabel);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(padding);
        hBox.setMinWidth(75);
        hBox.setMaxWidth(75);
        hBox.setPrefWidth(75);
        return hBox;
    }

    public List<Pane> createRotaryKnobBox(Label labelText, double min, double max, double initial){
        labelText.setFont(TextHelper.FONT_12);
        RotaryKnob knob = new RotaryKnob(min, max, initial);
        VBox box = new VBox(5, labelText, knob);
        box.setAlignment(Pos.BOTTOM_CENTER);
        List<Pane> list = List.of(box, knob);
        return list;
    }

    public void setBasics(){
        this.setPadding(new Insets(10));
        this.setSpacing(15);
        this.getStyleClass().add(TextHelper.STYLECLASS_EFFEKTE_SIDEBAR);
        this.getChildren().add(contentBox);
    }
}

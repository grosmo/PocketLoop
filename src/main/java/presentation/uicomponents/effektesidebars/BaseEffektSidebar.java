package presentation.uicomponents.effektesidebars;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import presentation.uicomponents.uicontrols.RotaryKnob;

public abstract class BaseEffektSidebar extends BaseSidebar{

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
        Label label = new Label(text);
        label.setFont(new Font(50));
        label.setRotate(270);
        label.setPadding(new Insets(0, -50, 0, -50));
        label.setAlignment(Pos.TOP_LEFT);
        HBox hBox = new HBox(label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(padding);
        hBox.setMinWidth(75);
        hBox.setMaxWidth(75);
        hBox.setPrefWidth(75);
        return hBox;
    }

    public List<Pane> createRotaryKnobBox(String labelText, double min, double max, double initial){
        Label label = new Label(labelText);
        label.setFont(new Font(12));
        RotaryKnob knob = new RotaryKnob(min, max, initial);
        VBox box = new VBox(5, label, knob);
        box.setAlignment(Pos.BOTTOM_CENTER);
        List<Pane> list = List.of(box, knob);
        return list;
    }

    public void setBasics(){
        this.setPadding(new Insets(10));
        this.setSpacing(15);
        this.getStyleClass().add("effekte-sidebar");
        this.getChildren().add(contentBox);
    }
}

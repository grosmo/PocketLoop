package presentation.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import presentation.TextHelper;

public abstract class BaseView extends GridPane {
    CheckBox switchView;
    Label viewLabel;
    Label sampleRateLabel;
    VBox labelBox;
    Button playAll;
    Button stopAll;
    Button selectAll;
    Button deselectAll;
    
    protected HBox createTopBar() {

        HBox containerBox = new HBox();
        containerBox.setAlignment(Pos.CENTER);
        containerBox.setMaxWidth(Double.MAX_VALUE);

        viewLabel = new Label();
        viewLabel.setFont(TextHelper.FONT_35);

        sampleRateLabel = new Label();

        labelBox = new VBox(viewLabel, sampleRateLabel);
        labelBox.setAlignment(Pos.TOP_LEFT);
        labelBox.setPadding(new Insets(5, 10, 5, 10));
        labelBox.setMinHeight(80);
        labelBox.setMinWidth(200);

        HBox buttonContainer = new HBox();
        
        stopAll = new Button();
        stopAll.getStyleClass().add(TextHelper.STYLECLASS_BTN_STOP_ALL);
        Tooltip stopAllTooltip = new Tooltip(TextHelper.TOOLTIP_STOP_ALL);
        stopAllTooltip.setFont(TextHelper.FONT_TOOLTIP);
        stopAll.setTooltip(stopAllTooltip);
        
        playAll = new Button();
        playAll.getStyleClass().add(TextHelper.STYLECLASS_BTN_PLAY_ALL);
        Tooltip playAllTooltip = new Tooltip(TextHelper.TOOLTIP_PLAY_ALL);
        playAllTooltip.setFont(TextHelper.FONT_TOOLTIP);
        playAll.setTooltip(playAllTooltip);

        selectAll = new Button();
        selectAll.getStyleClass().add(TextHelper.STYLECLASS_BTN_SELECT_ALL);
        Tooltip selectAllTooltip = new Tooltip(TextHelper.TOOLTIP_SELECT_ALL);
        selectAllTooltip.setFont(TextHelper.FONT_TOOLTIP);
        selectAll.setTooltip(selectAllTooltip);
        HBox selectAllContainer = new HBox(selectAll);
        selectAllContainer.setPadding(new Insets(28,5,0,0));

        deselectAll = new Button();
        deselectAll.getStyleClass().add(TextHelper.STYLECLASS_BTN_DESELECT_ALL);
        Tooltip deselectTooltip = new Tooltip(TextHelper.TOOLTIP_DESELECT_ALL);
        deselectTooltip.setFont(TextHelper.FONT_TOOLTIP);
        deselectAll.setTooltip(deselectTooltip);
        HBox deselectAllContainer = new HBox(deselectAll);
        deselectAllContainer.setPadding(new Insets(28,0,0,8));

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(5, 148, 5, 10));
        buttonContainer.getChildren().addAll(selectAllContainer, playAll, stopAll, deselectAllContainer);
        HBox.setHgrow(buttonContainer, Priority.ALWAYS);

        HBox toggleContainer = new HBox();
        toggleContainer.setAlignment(Pos.CENTER_RIGHT);
        toggleContainer.setPadding(new Insets(5, 10, 5, 10));

        switchView = new CheckBox();
        switchView.getStyleClass().remove(TextHelper.STYLECLASS_CHECK_BOX);
        toggleContainer.getChildren().add(switchView);

        containerBox.getChildren().addAll(labelBox, buttonContainer, toggleContainer);

        return containerBox;
    }
  
}

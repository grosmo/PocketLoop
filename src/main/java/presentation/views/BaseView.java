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
import javafx.scene.text.Font;

public abstract class BaseView extends GridPane {
    CheckBox switchView;
    Label viewLabel;
    VBox labelBox;
    Button playAll;
    Button stopAll;
    Button selectAll;
    Button deselectAll;
    
    protected HBox createSwitchView() {

        HBox containerBox = new HBox();
        containerBox.setAlignment(Pos.CENTER);
        containerBox.setMaxWidth(Double.MAX_VALUE);

        viewLabel = new Label();
        viewLabel.setFont(new Font(35));

        labelBox = new VBox(viewLabel);
        labelBox.setAlignment(Pos.TOP_LEFT);
        labelBox.setPadding(new Insets(5, 10, 5, 10));
        labelBox.setMinHeight(80);
        labelBox.setMinWidth(200);

        HBox buttonContainer = new HBox();
        
        stopAll = new Button();
        stopAll.getStyleClass().add("btn-stop-all");
        stopAll.setTooltip(new Tooltip("Alle Samples stoppen"));
        
        playAll = new Button();
        playAll.getStyleClass().add("btn-play-all");
        playAll.setTooltip(new Tooltip("Alle ausgewählten Samples abspielen"));

        selectAll = new Button();
        selectAll.getStyleClass().add("btn-select-all");
        selectAll.setTooltip(new Tooltip("Alle Samples auswählen"));
        HBox selectAllContainer = new HBox(selectAll);
        selectAllContainer.setPadding(new Insets(28,5,0,0));

        deselectAll = new Button();
        deselectAll.getStyleClass().add("btn-deselect-all");
        deselectAll.setTooltip(new Tooltip("Alle Samples abwählen"));
        HBox deselectAllContainer = new HBox(deselectAll);
        deselectAllContainer.setPadding(new Insets(28,0,0,8));

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(5, 160, 5, 10));
        buttonContainer.getChildren().addAll(selectAllContainer, playAll, stopAll, deselectAllContainer);
        HBox.setHgrow(buttonContainer, Priority.ALWAYS);

        HBox toggleContainer = new HBox();
        toggleContainer.setAlignment(Pos.CENTER_RIGHT);
        toggleContainer.setPadding(new Insets(5, 10, 5, 10));

        switchView = new CheckBox();
        switchView.getStyleClass().remove("check-box");
        toggleContainer.getChildren().add(switchView);

        containerBox.getChildren().addAll(labelBox, buttonContainer, toggleContainer);

        return containerBox;
    }
  
}

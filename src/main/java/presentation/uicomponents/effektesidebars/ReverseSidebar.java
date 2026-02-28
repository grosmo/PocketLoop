package presentation.uicomponents.effektesidebars;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ReverseSidebar extends BaseEffektSidebar {
    
    public ReverseSidebar(){

        HBox rotatedLabelBox = createRotatedLabelBox(
            "Reverse", 
            new Insets(0, 0, 50, -11)
        );

        toggleBox = new VBox(
            10, 
            rotatedLabelBox, 
            toggleEffect
        );
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);

        Label infoLabel = new Label("Spielt das Audio\nrückwärts ab.");
        infoLabel.setFont(new Font(14));
        infoLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setWrapText(true);
        
        btnSave = new Button("Speichern");
        btnSave.getStyleClass().add("btn-save-effect");
        
        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            infoLabel, 
            btnSave
        );
        setBasics();
    }
}

package presentation.uicomponents.effektesidebars;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import presentation.TextHelper;

public class ReverseSidebar extends BaseEffektSidebar {

    Label infoLabel;
    
    public ReverseSidebar(){

        HBox rotatedLabelBox = createRotatedLabelBox(
            new String(), 
            new Insets(0, 0, 50, -11)
        );

        toggleBox = new VBox(
            10, 
            rotatedLabelBox, 
            toggleEffect
        );
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().add(toggleBox);

        infoLabel = new Label(new String());
        infoLabel.setFont(TextHelper.FONT_13);
        infoLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setWrapText(true);
        
        btnSave = new Button(new String());
        btnSave.getStyleClass().add(TextHelper.STYLECLASS_BTN_SAFE);
        
        contentBox = createContentBox();
        contentBox.getChildren().addAll(
            infoLabel, 
            btnSave
        );
        setBasics();
    }
}

package presentation.uicomponents.effektesidebars;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.TextHelper;
import presentation.uicomponents.uicontrols.AnimatedToggle;

public abstract class BaseSidebar extends HBox {
    
    Button btnSave;
    
    AnimatedToggle toggleEffect = new AnimatedToggle();
    VBox toggleBox;
    VBox contentBox;
    
    protected void createDescriptionBox(String description, String descriptionText) {
        Label lblLiveEffekte = new Label(description);
        lblLiveEffekte.setFont(TextHelper.FONT_35);
        Label lblLiveEffekteDefinition = new Label(descriptionText);
        lblLiveEffekteDefinition.setWrapText(true);
        VBox liveEffekteBox = new VBox(lblLiveEffekte, lblLiveEffekteDefinition);
        liveEffekteBox.setAlignment(Pos.BOTTOM_LEFT);
        liveEffekteBox.setPadding(new Insets(10,10,10,40));
        liveEffekteBox.setMaxWidth(270);
        this.getChildren().add(liveEffekteBox);
        
    }

}

package presentation.views;

import java.util.List;

import business.AudioSamplePlayer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import presentation.TextHelper;
import presentation.uicomponents.effektesidebars.BaseSidebarController;

public class SamplesView extends BaseView {
    
    Button btnRecord;
    Button btnRename;
    Button btnDelete;

    ListView<AudioSamplePlayer> listView;
    HBox effectsContainer;
    ScrollPane effectsScrollPane;

    Label noSamplesLabel;
    StackPane listViewBox;

    public SamplesView(ObservableList<AudioSamplePlayer> observableRecordingsList, List<BaseSidebarController> sidebarControllers){
        
        HBox switchViewContainer = createTopBar();
        switchView.getStyleClass().add("btn-switch-view-base");
        switchView.getStyleClass().add("btn-switch-view");
        viewLabel.setText(TextHelper.SAMPLES);

        listView = new ListView<>(observableRecordingsList);
		listView.autosize();
        listView.setVisible(false);

        listViewBox = new StackPane();
        listViewBox.getChildren().add(listView);
        listViewBox.setAlignment(Pos.CENTER);
        listViewBox.setMinHeight(322);
        listViewBox.getStyleClass().add("list-view-box");

        noSamplesLabel = new Label(TextHelper.NO_RECORDING_TEXT);
        noSamplesLabel.getStyleClass().add("no-samples-label");
        listViewBox.getChildren().add(noSamplesLabel);

        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMaxHeight(48);
        buttonBox.setSpacing(20);
        buttonBox.getStyleClass().add("button-box");
        
        btnRecord = new Button();
        Image micIcon = new Image(getClass().getResourceAsStream("/icons/microphone.png"));
        Image micHoverIcon = new Image(getClass().getResourceAsStream("/icons/microphone_hover.png"));
        ImageView micImageView = new ImageView(micIcon);
        btnRecord.getStyleClass().remove("button");
        btnRecord.setGraphic(micImageView);        
        btnRecord.setOnMouseEntered(e -> micImageView.setImage(micHoverIcon));
        btnRecord.setOnMouseExited(e -> micImageView.setImage(micIcon));
        
        btnRename = new Button();
        Image renameIcon = new Image(getClass().getResourceAsStream("/icons/rename.png"));
        Image renameHoverIcon = new Image(getClass().getResourceAsStream("/icons/rename_hover.png"));
        ImageView renameImageView = new ImageView(renameIcon);
        btnRename.getStyleClass().remove("button");
        btnRename.setGraphic(renameImageView);        
        btnRename.setOnMouseEntered(e -> renameImageView.setImage(renameHoverIcon));
        btnRename.setOnMouseExited(e -> renameImageView.setImage(renameIcon));
        
        btnDelete = new Button();
        Image deleteIcon = new Image(getClass().getResourceAsStream("/icons/delete.png"));
        Image deleteHoverIcon = new Image(getClass().getResourceAsStream("/icons/delete_hover.png"));
        ImageView deleteImageView = new ImageView(deleteIcon);
        btnDelete.getStyleClass().remove("button");
        btnDelete.setGraphic(deleteImageView);        
        btnDelete.setOnMouseEntered(e -> deleteImageView.setImage(deleteHoverIcon));
        btnDelete.setOnMouseExited(e -> deleteImageView.setImage(deleteIcon));
        
        buttonBox.getChildren().addAll(btnRecord, btnRename, btnDelete);

        effectsContainer = new HBox();
        effectsContainer.setSpacing(0);
        effectsContainer.setMinHeight(300);

        for(BaseSidebarController controller : sidebarControllers){
            effectsContainer.getChildren().add(controller.root());
        }

        effectsScrollPane = new ScrollPane(effectsContainer);
        effectsScrollPane.setFitToHeight(true);
        effectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        effectsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        this.add(switchViewContainer, 0, 0);
        this.add(listViewBox, 0, 1);
        this.add(buttonBox, 0, 2);
        this.add(effectsScrollPane, 0, 3);
        
        GridPane.setHgrow(listView, Priority.ALWAYS);
        GridPane.setHgrow(effectsScrollPane, Priority.ALWAYS);
    }
}

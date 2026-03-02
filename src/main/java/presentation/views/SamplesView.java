package presentation.views;

import java.util.List;

import business.IAudioSamplePlayer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import presentation.GUIHelper;
import presentation.TextHelper;
import presentation.uicomponents.effektesidebars.BaseSidebarController;

public class SamplesView extends BaseView {
    
    Button btnRecord;
    Button btnRename;
    Button btnDelete;

    ListView<IAudioSamplePlayer> listView;
    HBox effectsContainer;
    ScrollPane effectsScrollPane;

    Label noSamplesLabel;
    StackPane listViewBox;

    public SamplesView(ObservableList<IAudioSamplePlayer> observableRecordingsList, List<BaseSidebarController> sidebarControllers, GUIHelper guiHelper) {
        
        HBox switchViewContainer = createTopBar();
        switchView.getStyleClass().add(TextHelper.STYLECLASS_BTN_SWITCH_VIEW_BASE);
        switchView.getStyleClass().add(TextHelper.STYLECLASS_BTN_SWITCH_VIEW);
        viewLabel.setText(TextHelper.SAMPLES);

        listView = new ListView<>(observableRecordingsList);
		listView.autosize();
        listView.setVisible(false);

        listViewBox = new StackPane();
        listViewBox.getChildren().add(listView);
        listViewBox.setAlignment(Pos.CENTER);
        listViewBox.setMinHeight(322);
        listViewBox.getStyleClass().add(TextHelper.STYLECLASS_LIST_VIEW_BOX);

        noSamplesLabel = new Label(TextHelper.NO_RECORDING_TEXT);
        noSamplesLabel.getStyleClass().add(TextHelper.STYLECLASS_NO_SAMPLES_LABEL);
        listViewBox.getChildren().add(noSamplesLabel);

        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMaxHeight(48);
        buttonBox.setSpacing(20);
        buttonBox.getStyleClass().add(TextHelper.STYLECLASS_BUTTON_BOX);
        
        btnRecord = new Button();
        ImageView micImageView = new ImageView(guiHelper.getMicIcon());
        btnRecord.getStyleClass().remove(TextHelper.STYLECLASS_BUTTON);
        btnRecord.setGraphic(micImageView);        
        btnRecord.setOnMouseEntered(e -> micImageView.setImage(guiHelper.getMicHoverIcon()));
        btnRecord.setOnMouseExited(e -> micImageView.setImage(guiHelper.getMicIcon()));
        
        btnRename = new Button();
        ImageView renameImageView = new ImageView(guiHelper.getRenameIcon());
        btnRename.getStyleClass().remove(TextHelper.STYLECLASS_BUTTON);
        btnRename.setGraphic(renameImageView);        
        btnRename.setOnMouseEntered(e -> renameImageView.setImage(guiHelper.getRenameHoverIcon()));
        btnRename.setOnMouseExited(e -> renameImageView.setImage(guiHelper.getRenameIcon()));
        
        btnDelete = new Button();
        ImageView deleteImageView = new ImageView(guiHelper.getDeleteIcon());
        btnDelete.getStyleClass().remove(TextHelper.STYLECLASS_BUTTON);
        btnDelete.setGraphic(deleteImageView);        
        btnDelete.setOnMouseEntered(e -> deleteImageView.setImage(guiHelper.getDeleteHoverIcon()));
        btnDelete.setOnMouseExited(e -> deleteImageView.setImage(guiHelper.getDeleteIcon()));
        
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

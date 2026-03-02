package presentation.views;

import java.util.ArrayList;
import business.IServiceHelper;
import business.IAudioSamplePlayer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.ScaleTransition;
import presentation.uicomponents.PopupMessage;
import presentation.uicomponents.SampleListCell;
import presentation.uicomponents.effektesidebars.BaseSidebarController;
import presentation.uicomponents.effektesidebars.BitcrusherSidebarController;
import presentation.uicomponents.effektesidebars.DelaySidebarController;
import presentation.uicomponents.effektesidebars.FlangerSidebarController;
import presentation.uicomponents.effektesidebars.PitchShiftSidebarController;
import presentation.uicomponents.effektesidebars.ReverseSidebarController;
import presentation.uicomponents.effektesidebars.DescriptionLiveEffektController;
import presentation.uicomponents.effektesidebars.DescriptionArrayEffektController;
import presentation.GUI;
import presentation.GUIHelper;
import presentation.TextHelper;

public class SamplesViewController extends BaseController<SamplesView> {
    private final int OVERLAY_DURATION_MS = 600;

    ObservableList<IAudioSamplePlayer> observableRecordings;
    ArrayList<BaseSidebarController> sidebarControllers;

    public SamplesViewController(IServiceHelper serviceHelper, GUIHelper guiHelper){
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        this.sidebarControllers = new ArrayList<>();
        sidebarControllers.add(new DescriptionLiveEffektController());
        sidebarControllers.add(new DelaySidebarController(serviceHelper, guiHelper));
        sidebarControllers.add(new FlangerSidebarController(serviceHelper, guiHelper));
        sidebarControllers.add(new DescriptionArrayEffektController());
        sidebarControllers.add(new ReverseSidebarController(serviceHelper, guiHelper));
        sidebarControllers.add(new PitchShiftSidebarController(serviceHelper, guiHelper));
        sidebarControllers.add(new BitcrusherSidebarController(serviceHelper, guiHelper));
        this.observableRecordings = FXCollections.observableArrayList(serviceHelper.getSamplePlayers());
        setRoot(new SamplesView(observableRecordings, sidebarControllers, this.guiHelper));
        initialize();
    }

    @Override
    public void initialize() {

        root().switchView.setOnAction(e -> {
            GUI.setToggleSwitchView();
            root().switchView.setSelected(GUI.getToggleSwitchView());
            switchToOscilloscopeView();
        });

        Tooltip recordTooltip = new Tooltip(TextHelper.TOOLTIP_START_RECORDING_TEXT);
        recordTooltip.setFont(TextHelper.FONT_TOOLTIP);
        root().btnRecord.setTooltip(recordTooltip);
        root().btnRecord.setOnAction(e -> onRecord());

        Tooltip renameTooltip = new Tooltip(TextHelper.TOOLTIP_RENAME_RECORDING_TEXT);
        renameTooltip.setFont(TextHelper.FONT_TOOLTIP);
        root().btnRename.setTooltip(renameTooltip);
        root().btnRename.setOnAction(e -> onRename());

        Tooltip deleteTooltip = new Tooltip(TextHelper.TOOLTIP_DELETE_RECORDING_TEXT);
        deleteTooltip.setFont(TextHelper.FONT_TOOLTIP);
        root().btnDelete.setTooltip(deleteTooltip);
        root().btnDelete.setOnAction(e -> onDelete());

        serviceHelper.recordingProperty().addListener((obs, wasRecording, isNowRecording) -> {
            Platform.runLater(() -> {
                if (!isNowRecording) {
                    observableRecordings.clear();
                    observableRecordings.setAll(serviceHelper.getSamplePlayers());
                    root().listView.getSelectionModel().selectLast();
                }
            });
        });
        
        root().listView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                serviceHelper.setAktuellesSample(newValue);
                sidebarControllers.forEach(controller -> { controller.bindToRecordingModel(); });
            }
        });

        root().listView.setCellFactory(lv -> {
            SampleListCell cell = new SampleListCell(guiHelper);
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() == 2 && (!cell.isEmpty())) {
                        IAudioSamplePlayer item = cell.getItem();
                        boolean isPlaying = item.isPlaying();
                        item.setPlaying(!isPlaying);
                    }
                }  
            });
            return cell;
        });
        
		root().listView.prefWidthProperty().bind(((Region) root().listView.getParent()).widthProperty());
		root().listView.prefHeightProperty().bind(((Region) root().listView.getParent()).heightProperty());
        
        root().listView.getItems().addListener((ListChangeListener<IAudioSamplePlayer>) change -> {
            if(root().listView.getItems().isEmpty()){
                guiHelper.setNoSamplesInList(true);
                root().noSamplesLabel.setVisible(true);
                root().listView.setVisible(false);
            }
            else{
                root().noSamplesLabel.setVisible(false);
                root().listView.setVisible(true);
                guiHelper.setNoSamplesInList(false);
            }
        });

		root().effectsScrollPane.prefWidthProperty().bind(((Region) root().listView.getParent()).widthProperty());
		root().effectsScrollPane.prefHeightProperty().bind(((Region) root().listView.getParent()).heightProperty());

        guiHelper.updateSamplesListProperty().addListener((obj) -> {
            Platform.runLater(() -> {
                observableRecordings.clear();
                observableRecordings.setAll(serviceHelper.getSamplePlayers());
                root().listView.getSelectionModel().selectFirst();
            });
        });

        Tooltip switchTooltip = new Tooltip(TextHelper.TOOLTIP_SWITCH_VIEW_TEXT);
        switchTooltip.setFont(TextHelper.FONT_TOOLTIP);
        root().switchView.setTooltip(switchTooltip);
        
        initializeControlls();
    }

    private void onRecord() {
        serviceHelper.stopAllPlays();
        Platform.runLater(() -> {
            Stage recordingStage = new Stage();
            recordingStage.initModality(Modality.APPLICATION_MODAL);
            recordingStage.setTitle(TextHelper.AUFNAHME_STAGE_TITEL);
            recordingStage.getIcons().add(guiHelper.getIcon());
            recordingStage.setResizable(false);
            
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setStyle("-fx-progress-color: " + TextHelper.COLOR_PRIMARY_GREEN + ";");
            
            
            Label lblTextTemp = new Label();
            lblTextTemp.setFont(TextHelper.FONT_AUFNAHME_STAGE);
            lblTextTemp.setPadding(new Insets(8));

            Label lblTemp = new Label(TextHelper.AUFNAHME_STAGE_COUNTDOWN_TEXT);
            lblTemp.setFont(TextHelper.FONT_AUFNAHME_STAGE);
            lblTemp.setPadding(new Insets(8));
            
            VBox vbox = new VBox(15);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            vbox.getChildren().addAll(lblTemp, lblTextTemp);
            
            Scene scene = new Scene(vbox, 300, 200);
            recordingStage.setScene(scene);
            
            // Audio-Hardware VOR dem Countdown initialisieren
            serviceHelper.prepareRecording();

            final int[] countdown = {3};
            lblTextTemp.setText(String.valueOf(countdown[0]));
            
            progressIndicator.setVisible(false);
            
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), lblTextTemp);
            scaleTransition.setFromX(4.0);
            scaleTransition.setFromY(4.0);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            
            scaleTransition.play();
            
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    countdown[0]--;
                    if (countdown[0] > 0) {
                        lblTextTemp.setText(String.valueOf(countdown[0]));
                        scaleTransition.stop();
                        scaleTransition.playFromStart();
                    } else if (countdown[0] == 0) {
                        vbox.getChildren().removeAll(lblTemp, lblTextTemp);
                        vbox.getChildren().addAll(progressIndicator, lblTextTemp);
                        lblTextTemp.setText(TextHelper.AUFNAHME_STAGE_GESTARTET_TEXT);
                        progressIndicator.setVisible(true);
                    }
                })
            );

            timeline.setCycleCount(3);
            timeline.play();
            
            ChangeListener<Boolean> recordingListener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, 
                                    Boolean wasRecording, Boolean isNowRecording) {
                    if (!isNowRecording) {
                        Platform.runLater(() -> {
                            recordingStage.close();
                            new PopupMessage(
                                TextHelper.POPUP_MEHRERE_AUFNAHMEN_ERSTELLT, 
                                root().btnRecord, 
                                0, 
                                0, 
                                OVERLAY_DURATION_MS, 
                                true
                            );
                        });
                        serviceHelper.recordingProperty().removeListener(this);
                    }
                }
            };
            
            timeline.setOnFinished(e -> {
                serviceHelper.recordingProperty().addListener(recordingListener);
                serviceHelper.startRecording();
            });

            recordingStage.show();
            guiHelper.setLoopSelection(false);
        });

    }

    private void onRename(){
        IAudioSamplePlayer selectedRecording = root().listView.getSelectionModel().getSelectedItem();
        if(selectedRecording != null){
            String newName = guiHelper.getDialogueManager().showUmbenennenDialogue(
                selectedRecording, 
                TextHelper.RENAME_DIALOGUE_TITLE, 
                TextHelper.RENAME_DIALOGUE_HEADER, 
                TextHelper.RENAME_DIALOGUE_CONTENT
            );
            if(newName != null){
                serviceHelper.setSelectedRecordingDisplayName(selectedRecording, newName);
                observableRecordings.setAll(serviceHelper.getSamplePlayers());
                root().listView.getSelectionModel().select(selectedRecording);
                new PopupMessage(
                    TextHelper.POPUP_MEHRERE_AUFNAHMEN_UMBENANNT 
                    + selectedRecording.getDisplayName(), 
                    root().btnRename,
                    0, 
                    -0, 
                    OVERLAY_DURATION_MS, 
                    true
                );
            }    
        }
    }

    private void onDelete(){

        boolean deleteAll = false;
        if(serviceHelper.getSelectedCount() > 1){
            deleteAll = guiHelper.getDialogueManager().showConfirmationDialogue(
                TextHelper.DELETE_ALL_DIALOGUE_TITLE, 
                TextHelper.DELETE_ALL_DIALOGUE_HEADER, 
                TextHelper.DELETE_ALL_DIALOGUE_MESSAGE, 
                TextHelper.DELETE_ALL_DIALOGUE_CONFIRM_BUTTON, 
                TextHelper.DELETE_ALL_DIALOGUE_DENY_BUTTON
            );

            if(deleteAll){
                guiHelper.setdisableEffects(true);
                
                var tempList = serviceHelper.getSamplePlayers().stream()
                    .filter(IAudioSamplePlayer::isLoopSelected).toList();

                tempList.forEach(sample -> serviceHelper.deleteRecording(sample));
                
                observableRecordings.clear();
                observableRecordings.setAll(serviceHelper.getSamplePlayers());
                new PopupMessage(
                    TextHelper.POPUP_MEHRERE_AUFNAHMEN_DELETED, 
                    root().btnDelete, 
                    0,
                    0, 
                    OVERLAY_DURATION_MS, 
                    true
                );
                guiHelper.setdisableEffects(false);
            }
        }

        if(!deleteAll){
            IAudioSamplePlayer selectedRecording = root().listView.getSelectionModel().getSelectedItem();
            if(selectedRecording != null){
                guiHelper.setdisableEffects(true);
                serviceHelper.deleteRecording(selectedRecording);
                
                observableRecordings.clear();
                observableRecordings.setAll(serviceHelper.getSamplePlayers());
                new PopupMessage(
                    TextHelper.POPUP_AUFNAHME_DELETED, 
                    root().btnDelete, 
                    0, 
                    0, 
                    OVERLAY_DURATION_MS, 
                    true
                );
                guiHelper.setdisableEffects(false);
            }
        }
        
        if(!serviceHelper.getSamplePlayers().isEmpty())
            root().listView.getSelectionModel().selectFirst();

        guiHelper.setLoopSelection(false);
    }

    private void switchToOscilloscopeView() {
        GUI.switchView(TextHelper.SWITCH_VIEW_NAME_OSZILLOSKOPE);
    }
}

package presentation.views;

import java.util.ArrayList;

import business.IServiceHelper;
import business.AudioSamplePlayer;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

public class SamplesViewController extends BaseController<SamplesView> {
    private final int OVERLAY_DURATION_MS = 600;
    private final String SWITCH_VIEW_NAME = "oscilloscopeView";

    IServiceHelper serviceHelper;
    GUIHelper guiHelper;
    ObservableList<AudioSamplePlayer> observableRecordings;
    ArrayList<BaseSidebarController> sidebarControllers;

    private final Font tooltipFont = new Font(13);


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
        
        setRoot(new SamplesView(observableRecordings, sidebarControllers));

        initialize();
    }

    @Override
    public void initialize() {

        root().switchView.setOnAction(e -> {
            root().switchView.getStyleClass().removeAll("btn-switch-root", "btn-switch-root-checked");
            root().switchView.getStyleClass().add("btn-switch-root");
            GUI.setToggleSwitchView();
            root().switchView.setSelected(GUI.getToggleSwitchView());
            switchToOscilloscopeView();
        });

        Tooltip recordTooltip = new Tooltip("Neue Aufnahme starten");
        recordTooltip.setFont(tooltipFont);
        root().btnRecord.setTooltip(recordTooltip);
        root().btnRecord.setOnAction(e -> onRecord());

        Tooltip renameTooltip = new Tooltip("Aufnahme umbenennen");
        renameTooltip.setFont(tooltipFont);
        root().btnRename.setTooltip(renameTooltip);
        root().btnRename.setOnAction(e -> onRename());

        Tooltip deleteTooltip = new Tooltip("Aufnahme Löschen");
        deleteTooltip.setFont(tooltipFont);
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
                        AudioSamplePlayer item = cell.getItem();
                        boolean isPlaying = item.isPlaying();
                        item.setPlaying(!isPlaying);
                    }
                }  
            });
            return cell;
        });
        
		root().listView.prefWidthProperty().bind(((Region) root().listView.getParent()).widthProperty());
		root().listView.prefHeightProperty().bind(((Region) root().listView.getParent()).heightProperty());
        
        root().listView.getItems().addListener((ListChangeListener<AudioSamplePlayer>) change -> {
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

        Tooltip switchTooltip = new Tooltip("Ansicht Wechseln");
        switchTooltip.setFont(tooltipFont);
        root().switchView.setTooltip(switchTooltip);
        root().samplesLabel.setText("Samplerate: " + serviceHelper.getMasterOutput().bufferSize());
        
        root().stopAll.setOnAction(e -> onStopAllPlays());
        root().playAll.setOnAction(e -> onPlayAll());
        root().selectAll.setOnAction(e -> onSelectAllSamples());
        root().deselectAll.setOnAction(e -> onDeselectAllSamples());
    }

    private void onDeselectAllSamples() {
        serviceHelper.getSamplePlayers().forEach(e -> e.setLoopSelected(false));
        guiHelper.setLoopSelection(false);
    }

    private void onSelectAllSamples() {
        serviceHelper.getSamplePlayers().forEach(e -> e.setLoopSelected(true));
        guiHelper.setLoopSelection(true);
    }

    private void onStopAllPlays() {
        serviceHelper.stopAllPlays();
    }

    private void onPlayAll() {
        serviceHelper.playAll();
    }

    private void onRecord() {
        serviceHelper.stopAllPlays();
        Platform.runLater(() -> {
            Stage recordingStage = new Stage();
            recordingStage.initModality(Modality.APPLICATION_MODAL);
            recordingStage.setTitle("Aufnahme");            
            Image icon = new Image(
                getClass().getResourceAsStream("/icons/app_icon.png")
            );
            recordingStage.getIcons().add(icon);
            recordingStage.setResizable(false);
            
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setStyle("-fx-progress-color: #00b4a0;");
            
            
            Label label = new Label();
            label.setStyle("-fx-font-size: 20px; -fx-padding: 8px;");

            Label labelTemp = new Label("Aufnahme startet in:");
            labelTemp.setStyle("-fx-font-size: 20px; -fx-padding: 8px;");
            
            VBox vbox = new VBox(15);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            vbox.getChildren().addAll(labelTemp, label);
            
            Scene scene = new Scene(vbox, 300, 200);
            recordingStage.setScene(scene);
            
            // Audio-Hardware VOR dem Countdown initialisieren
            serviceHelper.prepareRecording();

            final int[] countdown = {3};
            label.setText(String.valueOf(countdown[0]));
            
            progressIndicator.setVisible(false);
            
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), label);
            scaleTransition.setFromX(4.0);
            scaleTransition.setFromY(4.0);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            
            scaleTransition.play();
            
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    countdown[0]--;
                    if (countdown[0] > 0) {
                        label.setText(String.valueOf(countdown[0]));
                        scaleTransition.stop();
                        scaleTransition.playFromStart();
                    } else if (countdown[0] == 0) {
                        vbox.getChildren().removeAll(labelTemp, label);
                        vbox.getChildren().addAll(progressIndicator, label);
                        label.setText("Aufnahme gestartet");
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
                            new PopupMessage("Aufnahme erstellt.", root().btnRecord, 0, 0, OVERLAY_DURATION_MS, true);
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
        });

    }

    private void onRename(){
        AudioSamplePlayer selectedRecording = root().listView.getSelectionModel().getSelectedItem();
        if(selectedRecording != null){
            TextInputDialog dialog = new TextInputDialog(selectedRecording.getDisplayName());
            dialog.setTitle("Aufnahme umbenennen");
            dialog.setHeaderText("Neuen Namen eingeben:");
            dialog.setContentText("Name:");
            dialog.getDialogPane().setMinWidth(300);
            dialog.getDialogPane().setMinHeight(170);
            
            Image icon = new Image(
                getClass().getResourceAsStream("/icons/app_icon.png")
            );
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);

            dialog.getDialogPane().getStylesheets().add(getClass().getResource("/presentation/style.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("custom-dialog");
            dialog.getDialogPane().getStyleClass().add("custom-rename");

            dialog.showAndWait().ifPresent(newName -> {
                if(!newName.trim().isEmpty()){
                    serviceHelper.setSelectedRecordingDisplayName(selectedRecording, newName);
                    observableRecordings.setAll(serviceHelper.getSamplePlayers());
                    root().listView.getSelectionModel().select(selectedRecording);
                    new PopupMessage("Sample umbenannt zu: " + selectedRecording.getDisplayName(), root().btnRename, 0, -0, OVERLAY_DURATION_MS, true);
                }
            });
        }
    }

    private void onDelete(){
        AudioSamplePlayer selectedRecording = root().listView.getSelectionModel().getSelectedItem();
        if(selectedRecording != null){
            guiHelper.setdisableEffects(true);
            
            serviceHelper.deleteRecording(selectedRecording);

            observableRecordings.clear();
            observableRecordings.setAll(serviceHelper.getSamplePlayers());
            new PopupMessage("Sample gelöscht", root().btnDelete, 0, 0, OVERLAY_DURATION_MS, true);
            guiHelper.setdisableEffects(false);
        }
    }

    private void switchToOscilloscopeView() {
        GUI.switchView(SWITCH_VIEW_NAME);
    }
}

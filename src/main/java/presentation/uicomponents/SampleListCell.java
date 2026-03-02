package presentation.uicomponents;

import business.IAudioSamplePlayer;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import presentation.GUIHelper;
import presentation.TextHelper;
import presentation.uicomponents.uicontrols.RotaryKnob;

public class SampleListCell extends ListCell<IAudioSamplePlayer> {
    private CheckBox chckBxPlay;
    private CheckBox chckBxSelectedPlay;
    private Label label;
    private RotaryKnob volumeKnob;
    private VBox volumeBox;
    private WaveformCanvas waveformCanvas;
    private HBox waveformRow;
    private VBox content;
    private HBox topRow;

    private ChangeListener<Number> volumeListener;
    private ChangeListener<Boolean> playListener;
    private ChangeListener<Boolean> playingPropertyListener;
    private IAudioSamplePlayer currentItem;
    
    private ImageView imageView;
    private Image playImage;
    private Image stopImage;
    
    public SampleListCell(GUIHelper guiHelper) {
        super();

        chckBxPlay = new CheckBox();
        chckBxPlay.getStyleClass().remove(TextHelper.STYLECLASS_CHECK_BOX);
        
        playImage = guiHelper.getPlayImage();
        stopImage = guiHelper.getStopImage();
        
        imageView = new ImageView();
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        chckBxPlay.setGraphic(imageView);

        Tooltip tooltip = new Tooltip(TextHelper.TOOLTIP_LISTCELL_PLAY);
        tooltip.setFont(TextHelper.FONT_TOOLTIP);
        chckBxPlay.setTooltip(tooltip);

        chckBxSelectedPlay = new CheckBox();
        chckBxSelectedPlay.getStyleClass().add(TextHelper.STYLECLASS_PLAY_DOT);

        Tooltip selectedPlayTooltip = new Tooltip(TextHelper.TOOLTIP_LISTCELL_SELECT);
        selectedPlayTooltip.setFont(TextHelper.FONT_TOOLTIP);
        chckBxSelectedPlay.setTooltip(selectedPlayTooltip);

        chckBxSelectedPlay.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (currentItem != null) {
                if(isNowSelected)
                    currentItem.setLoopSelected(isNowSelected);
                else
                    currentItem.setLoopSelected(isNowSelected);
            }
        });

        guiHelper.loopSelectionProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if(currentItem != null){
                chckBxSelectedPlay.setSelected(guiHelper.isLoopSelection());
            }
        });
        
        label = new Label();
        label.setFont(TextHelper.FONT_15);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0,0,0,5));
        label.setStyle(TextHelper.STYLECLASS_TEXTFILL_BLACK);
        
        HBox.setHgrow(label, Priority.ALWAYS);
        
        volumeKnob = new RotaryKnob(-30, 10, 0);
        volumeKnob.setPrefSize(50, 50);
        
        volumeBox = new VBox();
        volumeBox.getChildren().add(volumeKnob);
        volumeBox.setAlignment(Pos.CENTER);
        volumeBox.setMinWidth(60);
        volumeBox.setPrefWidth(60);
        volumeBox.setMaxWidth(60);
        Tooltip volumeTooltip = new Tooltip(TextHelper.TOOLTIP_LISTCELL_VOLUME);
        volumeTooltip.setFont(TextHelper.FONT_13);
        Tooltip.install(volumeKnob, volumeTooltip);
        
        topRow = new HBox();
        topRow.getChildren().addAll(chckBxPlay, chckBxSelectedPlay, label);
        topRow.setMaxWidth(Double.MAX_VALUE);
        topRow.setPadding(new Insets(5));
        topRow.setSpacing(5);
        
        waveformRow = new HBox();
        waveformRow.setAlignment(Pos.CENTER_LEFT);
        waveformRow.setMaxWidth(Double.MAX_VALUE);
        
        content = new VBox();
        content.getChildren().addAll(topRow, waveformRow);
        content.setMaxWidth(Double.MAX_VALUE);
        content.prefWidthProperty().bind(widthProperty().subtract(55));
    }
    
    @Override
    protected void updateItem(IAudioSamplePlayer item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            if (volumeListener != null) {
                volumeKnob.valueProperty().removeListener(volumeListener);
                volumeListener = null;
            }
            if (playListener != null) {
                chckBxPlay.selectedProperty().removeListener(playListener);
                playListener = null;
            }
            if (playingPropertyListener != null && currentItem != null) {
                currentItem.playingProperty().removeListener(playingPropertyListener);
                playingPropertyListener = null;
            }
            currentItem = null;
            
            getStyleClass().remove(TextHelper.STYLECLASS_PLAYING);
            setText(null);
            setGraphic(null);
        } else {
            updatePlayingStyle(item.isPlaying());
            currentItem = item;            
            label.setText(item.getDisplayName());
            if (volumeListener != null) {
                volumeKnob.valueProperty().removeListener(volumeListener);
            }
            volumeKnob.setValue(item.getVolume());
            volumeListener = (obs, oldVal, newVal) -> {
                item.setVolume(newVal.floatValue());
            };
            volumeKnob.valueProperty().addListener(volumeListener);
            if (playListener != null)
                chckBxPlay.selectedProperty().removeListener(playListener);
            else{
                playListener = (obs, wasSelected, isNowSelected) -> {
                    item.setPlaying(isNowSelected);
                };
            }

            chckBxPlay.setSelected(item.isPlaying());
            chckBxPlay.selectedProperty().addListener(playListener);
            if (playingPropertyListener != null && currentItem != null) {
                currentItem.playingProperty().removeListener(playingPropertyListener);
            }
            playingPropertyListener = (obs, wasPlaying, isNowPlaying) -> {
                updatePlayingStyle(isNowPlaying);
            };
            item.playingProperty().addListener(playingPropertyListener);
            if (waveformCanvas == null || waveformCanvas.sample != item || item.isResampled()) {
                waveformCanvas = new WaveformCanvas(item);
                HBox.setHgrow(waveformCanvas, Priority.ALWAYS);
                waveformRow.getChildren().setAll(volumeBox, waveformCanvas);
            }
            setGraphic(content);
        }
    }

    private void updatePlayingStyle(boolean isNowPlaying) {
        imageView.setImage(isNowPlaying ? stopImage : playImage);
        if (isNowPlaying) {
            if (!getStyleClass().contains(TextHelper.STYLECLASS_PLAYING)) {
                getStyleClass().add(TextHelper.STYLECLASS_PLAYING);
            }
        } else {
            getStyleClass().removeAll(TextHelper.STYLECLASS_PLAYING);
            // CSS update erzwingen, damit die Style-Klasse sofort upgedated werden
            applyCss();
            requestLayout();
        }
    }
}

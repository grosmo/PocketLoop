package presentation.uicomponents.effektesidebars;

import java.util.List;

import business.effekteservices.EffectType;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Optional;
import presentation.uicomponents.PopupMessage;
import presentation.views.BaseController;

// public abstract class BaseSidebarController<T extends BaseSidebar> extends BaseController<T>
public abstract class BaseSidebarController<T extends BaseSidebar> extends BaseController<T> {

    private final String NO_RECORDING_SELECTED_TITLE = "Keine Aufnahme ausgewählt";
    private final String NO_RECORDING_SELECTED_MESSAGE = "Bitte mache erst eine Aufnahme und/oder wähle eine aus um einen Effekt benutzen zu können.";

    private final String RESTRICTED_EFFECT_TITLE = "Effekt kann nicht angehängt werden";
    private final String RESTRICTED_EFFECT_MESSAGE = "Live Effekte können nur verwendet werden, wenn keine Arrayeffekte aktiviert sind.";

    private final String EFFEKT_GESPEICHERT_MESSAGE = "Effekt wurde auf das Sample angewendet.";

    private final int OVERLAY_DURATION_MS = 600;

    ChangeListener<Object> disableReverseListener;
    ChangeListener<Object> disablePitchListener;
    ChangeListener<Object> disableBitcrushListener;
    ChangeListener<Object> disableDelayListener;
    ChangeListener<Object> disableFlangerListener;

    ChangeListener<Object> disableAllEffectsListener;

    ChangeListener<Boolean> checkConditionsListener;
    EventHandler<MouseEvent> restrictedEffectFilter;
    EventHandler<MouseEvent> noSelectionFilter;
    boolean isEnableConditionRestricted;

    public T getRoot() {
        return super.root();
    }

    public void setRoot(T pane) {
        super.setRoot(pane);
    }

    public abstract void bindToRecordingModel();

    protected void animateContentBox(VBox contentBox, boolean show) {
        if (show) {
            contentBox.setVisible(true);
            contentBox.setManaged(true);
            contentBox.setOpacity(0);
            
            FadeTransition fade = new FadeTransition(Duration.millis(400), contentBox);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
            fade.play();
        } else {
            FadeTransition fade = new FadeTransition(Duration.millis(200), contentBox);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> {
                contentBox.setVisible(false);
                contentBox.setManaged(false);
            });
            fade.play();
        }
    }

    protected void showNoConditionRestrictedInfo(String titel, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Image icon = new Image(
            getClass().getResourceAsStream("/icons/app_icon.png")
        );

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/presentation/style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-dialog");
        alert.getDialogPane().getStyleClass().add("custom-alert");
        alert.showAndWait();
    }

    protected void setBasicEvents(){

        disableAllEffectsListener = (obs, oldVal, newVal) -> {
            if((Boolean)newVal) {
                root().toggleEffect.setSelected(false);
            }
        };

        disableReverseListener = (observ, oldVal, newVal) -> {
            if((Boolean)newVal && getServiceHelper().getAktuellesSample() != null) {
                root().toggleEffect.setSelected(false);
            }
        };

        disablePitchListener = (observ, oldVal, newVal) -> {
            if((Boolean)newVal && getServiceHelper().getAktuellesSample() != null) {
                root().toggleEffect.setSelected(false);
            }
        };

        disableBitcrushListener = (observ, oldVal, newVal) -> {
            if((Boolean)newVal && getServiceHelper().getAktuellesSample() != null) {
                root().toggleEffect.setSelected(false);
            }
        };

        disableDelayListener = (observ, oldVal, newVal) -> {
            if((Boolean)newVal && getServiceHelper().getAktuellesSample() != null) {
                root().toggleEffect.setSelected(false);
            }
        };

        disableFlangerListener = (observ, oldVal, newVal) -> {
            if((Boolean)newVal && getServiceHelper().getAktuellesSample() != null) {
                root().toggleEffect.setSelected(false);
            }
        };

        getGuiHelper().disableEffectsProperty().removeListener(disableAllEffectsListener);
        getGuiHelper().disableEffectsProperty().addListener(disableAllEffectsListener);

        if (root().toggleBox != null){

            noSelectionFilter = event -> {
                showNoConditionRestrictedInfo(
                    NO_RECORDING_SELECTED_TITLE, 
                    NO_RECORDING_SELECTED_MESSAGE
                );
                event.consume();
            };
            
            // einen initialen Filter setzen
            root().toggleBox.setOnMouseClicked(noSelectionFilter);
            getGuiHelper().noSamplesInListProperty().addListener((obj, oldVal, newVal) -> {
                if(newVal){
                    root().toggleEffect.addEventFilter(MouseEvent.MOUSE_CLICKED, noSelectionFilter);
                }
                else {
                    // den initialen Filter entfernen wenn der filter auf den toggle effekt gesetzt wurde
                    root().toggleBox.setOnMouseClicked(null);
                    root().toggleEffect.removeEventFilter(MouseEvent.MOUSE_CLICKED, noSelectionFilter);
                }
            });
        }
    }

    protected void initializeConditionsListener(){

        checkConditionsListener = (obs, oldVal, newVal) -> {
            isEnableConditionRestricted = getServiceHelper().isPitchShiftEnabled() || 
            getServiceHelper().isReverseEnabled() || 
            getServiceHelper().isBitcrusherEnabled();
        };
        
        restrictedEffectFilter = event -> {
            if(isEnableConditionRestricted) {
                event.consume();
                showNoConditionRestrictedInfo(
                    RESTRICTED_EFFECT_TITLE, 
                    RESTRICTED_EFFECT_MESSAGE
                );
            }
        };
    }

    protected void setConditionsListener(){
        List<BooleanProperty> propertiesToList = List.of(
            getServiceHelper().pitchShiftEnabledProperty(),
            getServiceHelper().reverseEnabledProperty(),
            getServiceHelper().bitcrusherEnabledProperty()
        );

        if(checkConditionsListener != null) {
            root().toggleEffect.removeEventFilter(MouseEvent.MOUSE_CLICKED, restrictedEffectFilter);
            root().toggleEffect.addEventFilter(MouseEvent.MOUSE_CLICKED, restrictedEffectFilter);
            propertiesToList.forEach(e -> {
                e.removeListener(checkConditionsListener);
                e.addListener(checkConditionsListener);
            });
        }

        propertiesToList.forEach(prop -> {
            if(prop != null) {  
                prop.removeListener(disableAllEffectsListener);
                prop.addListener(disableAllEffectsListener);
            }
        });
    }

    protected void setSaveButtonEvent(EffectType effectType){
        root().btnSave.setOnAction(event -> {
            if(getServiceHelper().getAktuellesSample() != null) {
                getServiceHelper().stopAllPlays();

                Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                dialog.setTitle("Behalten");
                dialog.setHeaderText("Behalten oder Überschreiben?");
                dialog.setContentText("Willst du die ursprüngliche Aufnahme behalten oder überschreiben?");
                
                ButtonType btnBehalten = new ButtonType("Behalten");
                ButtonType btnUeberschreiben = new ButtonType("Überschreiben");
                
                dialog.getButtonTypes().setAll(btnBehalten, btnUeberschreiben);
                
                dialog.getDialogPane().setMinWidth(280);
                dialog.getDialogPane().setMinHeight(170);
                
                Image icon = new Image(
                    getClass().getResourceAsStream("/icons/app_icon.png")
                );
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(icon);
                
                dialog.getDialogPane().getStylesheets().add(getClass().getResource("/presentation/style.css").toExternalForm());
                dialog.getDialogPane().getStyleClass().add("custom-dialog");
                dialog.getDialogPane().getStyleClass().add("custom-frage");
                
                Optional<ButtonType> result = dialog.showAndWait();
                
                boolean keepOriginal = result.isPresent() && result.get() == btnBehalten;

                getServiceHelper().applyEffect(effectType, keepOriginal);
                
                boolean forceUpdate = getGuiHelper().isUpdateSamplesList();
                getGuiHelper().setUpdateSamplesList(!forceUpdate);
                getServiceHelper().setBitcrusherEnabled(false);
                getServiceHelper().setPitchShiftEnabled(false);
                getServiceHelper().setReverseEnabled(false);
                root().toggleEffect.setSelected(false);
                new PopupMessage(EFFEKT_GESPEICHERT_MESSAGE, root(), 0, 0, OVERLAY_DURATION_MS, true);
            }
        });
    }
}



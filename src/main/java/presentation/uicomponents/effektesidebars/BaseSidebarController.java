package presentation.uicomponents.effektesidebars;

import java.util.List;

import business.effekteservices.EffectType;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import presentation.TextHelper;
import presentation.uicomponents.PopupMessage;
import presentation.views.BaseController;

public abstract class BaseSidebarController<T extends BaseSidebar> extends BaseController<T> {
    private final int OVERLAY_DURATION_MS = 600;
    private final Duration FADE_IN_DURATION = Duration.millis(400);
    private final Duration FADE_OUT_DURATION = Duration.millis(200);

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
            
            FadeTransition fade = new FadeTransition(FADE_IN_DURATION, contentBox);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
            fade.play();
        } else {
            FadeTransition fade = new FadeTransition(FADE_OUT_DURATION, contentBox);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> {
                contentBox.setVisible(false);
                contentBox.setManaged(false);
            });
            fade.play();
        }
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
                getGuiHelper().getDialogueManager().showInfoDialogue(
                    TextHelper.NO_RECORDING_SELECTED_TITLE, 
                    TextHelper.NO_RECORDING_SELECTED_MESSAGE
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
                getGuiHelper().getDialogueManager().showInfoDialogue(
                    TextHelper.RESTRICTED_EFFECT_TITLE, 
                    TextHelper.RESTRICTED_EFFECT_MESSAGE
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
                boolean keepOriginal = getGuiHelper().getDialogueManager().showConfirmationDialogue(
                    TextHelper.NEW_ARRAYEFFECT_SAVE_TITLE, 
                    TextHelper.NEW_ARRAYEFFECT_SAVE_HEADER, 
                    TextHelper.NEW_ARRAYEFFECT_SAVE_MESSAGE, 
                    TextHelper.NEW_ARRAYEFFECT_SAVE_CONFIRM, 
                    TextHelper.NEW_ARRAYEFFECT_SAVE_DENY
                );

                getServiceHelper().applyEffect(effectType, keepOriginal);
                boolean forceUpdate = getGuiHelper().isUpdateSamplesList();
                getGuiHelper().setUpdateSamplesList(!forceUpdate);
                getServiceHelper().setBitcrusherEnabled(false);
                getServiceHelper().setPitchShiftEnabled(false);
                getServiceHelper().setReverseEnabled(false);
                root().toggleEffect.setSelected(false);
                new PopupMessage(TextHelper.EFFEKT_GESPEICHERT_MESSAGE, root(), 0, 0, OVERLAY_DURATION_MS, true);
            }
        });
    }
}



package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import javafx.beans.property.BooleanProperty;
import presentation.GUIHelper;

public class PitchShiftSidebarController extends BaseSidebarController<PitchShiftSidebar> {

    public PitchShiftSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        setRoot(new PitchShiftSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        setSaveButtonEvent(EffectType.PITCH_SHIFT);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null){ 
                if(newValue) {
                    serviceHelper.setDelayEnabled(false);
                    serviceHelper.setFlangerEnabled(false);
                }
                serviceHelper.setPitchShiftEnabled(newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        serviceHelper.aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                root().toggleEffect.setSelected(serviceHelper.isPitchShiftEnabled());
                root().knobPitchShift.setValue(serviceHelper.getPitchShiftSemitones());
                
                BooleanProperty reverseProp = serviceHelper.reverseEnabledProperty();
                if(reverseProp != null) {
                    reverseProp.removeListener(disableReverseListener);
                    reverseProp.addListener(disableReverseListener);
                }
                
                BooleanProperty bitcrushProp = serviceHelper.bitcrusherEnabledProperty();
                if(bitcrushProp != null) {
                    bitcrushProp.removeListener(disableBitcrushListener);
                    bitcrushProp.addListener(disableBitcrushListener);
                }

                BooleanProperty delayProp = serviceHelper.delayEnabledProperty();
                if(delayProp != null) {
                    delayProp.removeListener(disableDelayListener);
                    delayProp.addListener(disableDelayListener);
                }

                BooleanProperty flangerProp = serviceHelper.flangerEnabledProperty();;
                if(flangerProp != null) {
                    flangerProp.removeListener(disableFlangerListener);
                    flangerProp.addListener(disableFlangerListener);
                }
            }
        });

        root().knobPitchShift.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null) {
                serviceHelper.setPitchShiftSemitones(newValue.floatValue());
            }
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(serviceHelper.isPitchShiftEnabled());
        root().knobPitchShift.setValue(serviceHelper.getPitchShiftSemitones());
    }
}

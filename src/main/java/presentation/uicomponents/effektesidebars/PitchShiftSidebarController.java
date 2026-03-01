package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import javafx.beans.property.BooleanProperty;
import presentation.GUIHelper;

public class PitchShiftSidebarController extends BaseSidebarController<PitchShiftSidebar> {

    public PitchShiftSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){
        setServiceHelper(serviceHelper);
        setGuiHelper(guiHelper);
        setRoot(new PitchShiftSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        setSaveButtonEvent(EffectType.PITCH_SHIFT);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null){ 
                if(newValue) {
                    getServiceHelper().setDelayEnabled(false);
                    getServiceHelper().setFlangerEnabled(false);
                }
                getServiceHelper().setPitchShiftEnabled(newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        getServiceHelper().aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                root().toggleEffect.setSelected(getServiceHelper().isPitchShiftEnabled());
                root().knobPitchShift.setValue(getServiceHelper().getPitchShiftSemitones());
                
                BooleanProperty reverseProp = getServiceHelper().reverseEnabledProperty();
                if(reverseProp != null) {
                    reverseProp.removeListener(disableReverseListener);
                    reverseProp.addListener(disableReverseListener);
                }
                
                BooleanProperty bitcrushProp = getServiceHelper().bitcrusherEnabledProperty();
                if(bitcrushProp != null) {
                    bitcrushProp.removeListener(disableBitcrushListener);
                    bitcrushProp.addListener(disableBitcrushListener);
                }

                BooleanProperty delayProp = getServiceHelper().delayEnabledProperty();
                if(delayProp != null) {
                    delayProp.removeListener(disableDelayListener);
                    delayProp.addListener(disableDelayListener);
                }

                BooleanProperty flangerProp = getServiceHelper().flangerEnabledProperty();
                if(flangerProp != null) {
                    flangerProp.removeListener(disableFlangerListener);
                    flangerProp.addListener(disableFlangerListener);
                }
            }
        });

        root().knobPitchShift.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null) {
                getServiceHelper().setPitchShiftSemitones(newValue.floatValue());
            }
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(getServiceHelper().isPitchShiftEnabled());
        root().knobPitchShift.setValue(getServiceHelper().getPitchShiftSemitones());
    }
}

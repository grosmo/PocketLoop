package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import javafx.beans.property.BooleanProperty;
import presentation.GUIHelper;

public class BitcrusherSidebarController extends BaseSidebarController<BitcrusherSidebar> {

    public BitcrusherSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){
        setServiceHelper(serviceHelper);
        setGuiHelper(guiHelper);
        setRoot(new BitcrusherSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        setSaveButtonEvent(EffectType.BITCRUSHER);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {            
            if(getServiceHelper().getAktuellesSample() != null) {
                if(newValue) {
                    getServiceHelper().setDelayEnabled(false);
                    getServiceHelper().setFlangerEnabled(false);
                }
                getServiceHelper().setBitcrusherEnabled(newValue);
                animateContentBox(root().contentBox,newValue);
            }
        });

        getServiceHelper().aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                root().toggleEffect.setSelected(getServiceHelper().isBitcrusherEnabled());
                root().knobBitDepth.setValue(getServiceHelper().getBitcrushDepth());
                root().knobSampleRate.setValue(getServiceHelper().getBitcrushSampleRateDivider());

                BooleanProperty reverseProp = getServiceHelper().reverseEnabledProperty();
                if(reverseProp != null) {
                    reverseProp.removeListener(disableReverseListener);
                    reverseProp.addListener(disableReverseListener);
                }
                
                BooleanProperty pitchProp = getServiceHelper().pitchShiftEnabledProperty();
                if(pitchProp != null) {
                    pitchProp.removeListener(disablePitchListener);
                    pitchProp.addListener(disablePitchListener);
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

        root().knobBitDepth.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null) {
                getServiceHelper().setBitcrushDepth(newValue.intValue());
            }
        });

        root().knobSampleRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null) {
                getServiceHelper().setBitcrushSampleRateDivider(newValue.intValue());
            }
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(getServiceHelper().isBitcrusherEnabled());
        root().knobBitDepth.setValue(getServiceHelper().getBitcrushDepth());
        root().knobSampleRate.setValue(getServiceHelper().getBitcrushSampleRateDivider());
    }
}

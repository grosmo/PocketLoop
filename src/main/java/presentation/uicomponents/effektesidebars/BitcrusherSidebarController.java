package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import javafx.beans.property.BooleanProperty;
import presentation.GUIHelper;

public class BitcrusherSidebarController extends BaseSidebarController<BitcrusherSidebar> {

    public BitcrusherSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        setRoot(new BitcrusherSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        setSaveButtonEvent(EffectType.BITCRUSHER);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {            
            if(serviceHelper.getAktuellesSample() != null) {
                if(newValue) {
                    serviceHelper.setDelayEnabled(false);
                    serviceHelper.setFlangerEnabled(false);
                }
                serviceHelper.setBitcrusherEnabled(newValue);
                animateContentBox(root().contentBox,newValue);
            }
        });

        serviceHelper.aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                root().toggleEffect.setSelected(serviceHelper.isBitcrusherEnabled());
                root().knobBitDepth.setValue(serviceHelper.getBitcrushDepth());
                root().knobSampleRate.setValue(serviceHelper.getBitcrushSampleRateDivider());

                BooleanProperty reverseProp = serviceHelper.reverseEnabledProperty();
                if(reverseProp != null) {
                    reverseProp.removeListener(disableReverseListener);
                    reverseProp.addListener(disableReverseListener);
                }
                
                BooleanProperty pitchProp = serviceHelper.pitchShiftEnabledProperty();
                if(pitchProp != null) {
                    pitchProp.removeListener(disablePitchListener);
                    pitchProp.addListener(disablePitchListener);
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

        root().knobBitDepth.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null) {
                serviceHelper.setBitcrushDepth(newValue.intValue());
            }
        });

        root().knobSampleRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null) {
                serviceHelper.setBitcrushSampleRateDivider(newValue.intValue());
            }
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(serviceHelper.isBitcrusherEnabled());
        root().knobBitDepth.setValue(serviceHelper.getBitcrushDepth());
        root().knobSampleRate.setValue(serviceHelper.getBitcrushSampleRateDivider());
    }
}

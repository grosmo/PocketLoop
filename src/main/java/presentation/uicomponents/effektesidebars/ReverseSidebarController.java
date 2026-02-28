package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import javafx.beans.property.BooleanProperty;
import presentation.GUIHelper;

public class ReverseSidebarController extends BaseSidebarController<ReverseSidebar> {

    public ReverseSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){ 
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        setRoot(new ReverseSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        setSaveButtonEvent(EffectType.REVERSE);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null) {
                if(newValue) {
                    serviceHelper.setDelayEnabled(false);
                    serviceHelper.setFlangerEnabled(false);
                }
                serviceHelper.setReverseEnabled(newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        serviceHelper.aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                root().toggleEffect.setSelected(serviceHelper.isReverseEnabled());

                BooleanProperty pitchProp = serviceHelper.pitchShiftEnabledProperty();
                if(pitchProp != null) {
                    pitchProp.removeListener(disablePitchListener);
                    pitchProp.addListener(disablePitchListener);
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
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(serviceHelper.isReverseEnabled());
    }
}

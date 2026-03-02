package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import javafx.beans.property.BooleanProperty;
import presentation.GUIHelper;
import presentation.TextHelper;

public class ReverseSidebarController extends BaseSidebarController<ReverseSidebar> {

    public ReverseSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){ 
        setServiceHelper(serviceHelper);
        setGuiHelper(guiHelper);
        setRoot(new ReverseSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        setSaveButtonEvent(EffectType.REVERSE);

        root().titleLabel.setText(TextHelper.EFFEKT_REVERSE_TITLE);
        root().infoLabel.setText(TextHelper.EFFEKT_REVERSE_INFO);
        root().btnSave.setText(TextHelper.SAFE);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null) {
                if(newValue) {
                    getServiceHelper().setDelayEnabled(false);
                    getServiceHelper().setFlangerEnabled(false);
                }
                getServiceHelper().setReverseEnabled(newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        getServiceHelper().aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                root().toggleEffect.setSelected(getServiceHelper().isReverseEnabled());

                BooleanProperty pitchProp = getServiceHelper().pitchShiftEnabledProperty();
                if(pitchProp != null) {
                    pitchProp.removeListener(disablePitchListener);
                    pitchProp.addListener(disablePitchListener);
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
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(getServiceHelper().isReverseEnabled());
    }
}

package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import presentation.GUIHelper;
import presentation.TextHelper;

public class DelaySidebarController extends BaseSidebarController<DelaySidebar> {

    public DelaySidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){ 
        setServiceHelper(serviceHelper);
        setGuiHelper(guiHelper);
        setRoot(new DelaySidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        initializeConditionsListener();

        root().titleLabel.setText(TextHelper.EFFEKT_DELAY_TITLE);
        root().lblKnobDelayTime.setText(TextHelper.EFFEKT_TIME);
        root().lblKnobFeedback.setText(TextHelper.EFFEKT_FEEDBACK);

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null){
                getServiceHelper().setDelayEnabled(newValue);
                getServiceHelper().applyEffect(EffectType.DELAY, newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        getServiceHelper().aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                setConditionsListener();
                root().toggleEffect.setSelected(getServiceHelper().isDelayEnabled());
                root().knobDelayTime.setValue(getServiceHelper().getDelayTime() * 1000.0f);
                root().knobFeedback.setValue(getServiceHelper().getDelayFeedback());
            }
        });

        root().knobDelayTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            float delayTimeInSeconds = newValue.floatValue() / 1000.0f;
            if(getServiceHelper().getAktuellesSample() != null)
                getServiceHelper().setDelayTime(delayTimeInSeconds);
        });

        root().knobFeedback.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null)
                getServiceHelper().setDelayFeedback(newValue.floatValue());
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(getServiceHelper().isDelayEnabled());
        root().knobDelayTime.setValue(getServiceHelper().getDelayTime() * 1000.0f);
        root().knobFeedback.setValue(getServiceHelper().getDelayFeedback());
    }
}

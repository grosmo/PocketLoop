package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import presentation.GUIHelper;

public class DelaySidebarController extends BaseSidebarController<DelaySidebar> {

    public DelaySidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){ 
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        setRoot(new DelaySidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        initializeConditionsListener();

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null){
                serviceHelper.setDelayEnabled(newValue);
                serviceHelper.applyEffect(EffectType.DELAY, newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        serviceHelper.aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                setConditionsListener();
                root().toggleEffect.setSelected(serviceHelper.isDelayEnabled());
                root().knobDelayTime.setValue(serviceHelper.getDelayTime() * 1000.0f);
                root().knobFeedback.setValue(serviceHelper.getDelayFeedback());
            }
        });

        root().knobDelayTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            float delayTimeInSeconds = newValue.floatValue() / 1000.0f;
            if(serviceHelper.getAktuellesSample() != null)
                serviceHelper.setDelayTime(delayTimeInSeconds);
        });

        root().knobFeedback.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null)
                serviceHelper.setDelayFeedback(newValue.floatValue());
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(serviceHelper.isDelayEnabled());
        root().knobDelayTime.setValue(serviceHelper.getDelayTime() * 1000.0f);
        root().knobFeedback.setValue(serviceHelper.getDelayFeedback());
    }
}

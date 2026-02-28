package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import presentation.GUIHelper;

public class FlangerSidebarController extends BaseSidebarController<FlangerSidebar> {

    public FlangerSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){ 
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        setRoot(new FlangerSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        initializeConditionsListener();

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null){
                serviceHelper.setFlangerEnabled(newValue);
                serviceHelper.applyEffect(EffectType.FLANGER, newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        serviceHelper.aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                setConditionsListener();
                root().toggleEffect.setSelected(serviceHelper.isFlangerEnabled());
                root().knobFlangerRate.setValue(serviceHelper.getFlangerRate());
                root().knobFlangerDepth.setValue(serviceHelper.getFlangerDepth());
                root().knobFlangerDelay.setValue(serviceHelper.getFlangerDelay());                
            }
        });

        root().knobFlangerRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null)
                serviceHelper.setFlangerRate(newValue.floatValue());
        });

        root().knobFlangerDepth.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null)
                serviceHelper.setFlangerDepth(newValue.floatValue());
        });

        root().knobFlangerDelay.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(serviceHelper.getAktuellesSample() != null)
                serviceHelper.setFlangerDelay(newValue.floatValue());
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(serviceHelper.isFlangerEnabled());
        root().knobFlangerRate.setValue(serviceHelper.getFlangerRate());
        root().knobFlangerDepth.setValue(serviceHelper.getFlangerDepth());
        root().knobFlangerDelay.setValue(serviceHelper.getFlangerDelay());
    }
}

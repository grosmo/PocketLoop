package presentation.uicomponents.effektesidebars;

import business.IServiceHelper;
import business.effekteservices.EffectType;
import presentation.GUIHelper;

public class FlangerSidebarController extends BaseSidebarController<FlangerSidebar> {

    public FlangerSidebarController(IServiceHelper serviceHelper, GUIHelper guiHelper){ 
        setServiceHelper(serviceHelper);
        setGuiHelper(guiHelper);
        setRoot(new FlangerSidebar());
        root().toggleEffect.setDisable(true);
        initialize();
    }

    @Override
    public void initialize() {
        setBasicEvents();
        initializeConditionsListener();

        root().toggleEffect.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null){
                getServiceHelper().setFlangerEnabled(newValue);
                getServiceHelper().applyEffect(EffectType.FLANGER, newValue);
                animateContentBox(root().contentBox, newValue);
            }
        });

        getServiceHelper().aktuellesSampleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                root().toggleEffect.setDisable(false);
                setConditionsListener();
                root().toggleEffect.setSelected(getServiceHelper().isFlangerEnabled());
                root().knobFlangerRate.setValue(getServiceHelper().getFlangerRate());
                root().knobFlangerDepth.setValue(getServiceHelper().getFlangerDepth());
                root().knobFlangerDelay.setValue(getServiceHelper().getFlangerDelay());                
            }
        });

        root().knobFlangerRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null)
                getServiceHelper().setFlangerRate(newValue.floatValue());
        });

        root().knobFlangerDepth.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null)
                getServiceHelper().setFlangerDepth(newValue.floatValue());
        });

        root().knobFlangerDelay.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(getServiceHelper().getAktuellesSample() != null)
                getServiceHelper().setFlangerDelay(newValue.floatValue());
        });
    }

    @Override
    public void bindToRecordingModel(){
        root().toggleEffect.setSelected(getServiceHelper().isFlangerEnabled());
        root().knobFlangerRate.setValue(getServiceHelper().getFlangerRate());
        root().knobFlangerDepth.setValue(getServiceHelper().getFlangerDepth());
        root().knobFlangerDelay.setValue(getServiceHelper().getFlangerDelay());
    }
}

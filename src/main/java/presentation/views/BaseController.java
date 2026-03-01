package presentation.views;

import business.IServiceHelper;
import javafx.scene.layout.Pane;
import presentation.GUIHelper;

public abstract class BaseController <T extends Pane>{
    private T root;
    IServiceHelper serviceHelper;
    public void setServiceHelper(IServiceHelper serviceHelper){
        this.serviceHelper = serviceHelper;
    }
    public IServiceHelper getServiceHelper(){
        return serviceHelper;
    }

    GUIHelper guiHelper;
    public void setGuiHelper(GUIHelper guiHelper){
        this.guiHelper = guiHelper;
    }
    public GUIHelper getGuiHelper(){
        return guiHelper;
    }

    public abstract void initialize();

    public T root(){
        return root;
    }

    public void setRoot(T pane){
        root = pane;
    }

    public void initializeControlls(){
        BaseView baseView = (BaseView) root;
        if(baseView != null){
            baseView.stopAll.setOnAction(e -> onStopAllPlays());      
            baseView.playAll.setOnAction(e -> onPlayAll());
            baseView.selectAll.setOnAction(e -> onSelectAllSamples());
            baseView.deselectAll.setOnAction(e -> onDeselectAllSamples());
        }
        else {
            System.err.println("Root is not an instance of BaseView. Controlls not initialized.");
        }
    }

    
    private void onDeselectAllSamples() {
        serviceHelper.getSamplePlayers().forEach(e -> e.setLoopSelected(false));
        guiHelper.setLoopSelection(false);
    }

    private void onSelectAllSamples() {
        serviceHelper.getSamplePlayers().forEach(e -> e.setLoopSelected(true));
        guiHelper.setLoopSelection(true);
    }

    private void onStopAllPlays() {
        serviceHelper.stopAllPlays();
    }

    private void onPlayAll() {
        serviceHelper.playAll();
    }
}

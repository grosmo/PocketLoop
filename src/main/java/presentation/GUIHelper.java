package presentation;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import presentation.uicomponents.DialogueManager;

public class GUIHelper {

    DialogueManager dialogueManager;
    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }
    
    Image icon;
    public Image getIcon() {
        return icon;
    }

    public GUIHelper() {
        dialogueManager = new DialogueManager(this);
        icon = new Image(getClass().getResourceAsStream("/icons/app_icon.png"));
    }

    private final BooleanProperty updateSamplesList = new SimpleBooleanProperty(true);
    public BooleanProperty updateSamplesListProperty(){
        return updateSamplesList;
    }
    public boolean isUpdateSamplesList(){
        return updateSamplesList.get();
    }
    public void setUpdateSamplesList(boolean value){
        updateSamplesList.set(value);
    }

    private final BooleanProperty noSamplesInList = new SimpleBooleanProperty(true);
    public BooleanProperty noSamplesInListProperty(){
        return noSamplesInList;
    }
    public boolean isNoSamplesInList(){
        return noSamplesInList.get();
    }
    public void setNoSamplesInList(boolean value){
        noSamplesInList.set(value);
    }

    private final BooleanProperty disableEffects = new SimpleBooleanProperty(false);
    public BooleanProperty disableEffectsProperty(){
        return disableEffects;
    }
    public boolean isdisableEffects(){
        return disableEffects.get();
    }
    public void setdisableEffects(boolean value){
        disableEffects.set(value);
    }

    private final BooleanProperty loopSelection = new SimpleBooleanProperty(false);
    public BooleanProperty loopSelectionProperty(){
        return loopSelection;
    }
    public boolean isLoopSelection(){
        return loopSelection.get();
    }
    public void setLoopSelection(boolean value){
        loopSelection.set(value);
    }
}

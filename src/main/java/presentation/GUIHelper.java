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

    Image playImage;
    public Image getPlayImage() {
        return playImage;
    }

    Image stopImage;
    public Image getStopImage() {
        return stopImage;
    }

    Image micIcon;
    public Image getMicIcon() {
        return micIcon;
    }

    Image micHoverIcon;
    public Image getMicHoverIcon() {
        return micHoverIcon;
    }

    Image renameIcon;
    public Image getRenameIcon() {
        return renameIcon;
    }

    Image renameHoverIcon;
    public Image getRenameHoverIcon() {
        return renameHoverIcon;
    }

    Image deleteIcon;
    public Image getDeleteIcon() {
        return deleteIcon;
    }

    Image deleteHoverIcon;
    public Image getDeleteHoverIcon() {
        return deleteHoverIcon;
    }

    public GUIHelper() {
        dialogueManager = new DialogueManager(this);
        loadIcons();
    }

    public void loadIcons(){
        icon = new Image(getClass().getResourceAsStream("/icons/app_icon.png"));
        playImage = new Image(getClass().getResourceAsStream("/icons/play_circle.png"));
        stopImage = new Image(getClass().getResourceAsStream("/icons/stop_circle.png"));
        micIcon = new Image(getClass().getResourceAsStream("/icons/microphone.png"));
        micHoverIcon = new Image(getClass().getResourceAsStream("/icons/microphone_hover.png"));
        renameIcon = new Image(getClass().getResourceAsStream("/icons/rename.png"));
        renameHoverIcon = new Image(getClass().getResourceAsStream("/icons/rename_hover.png"));
        deleteIcon = new Image(getClass().getResourceAsStream("/icons/delete.png"));
        deleteHoverIcon = new Image(getClass().getResourceAsStream("/icons/delete_hover.png"));
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

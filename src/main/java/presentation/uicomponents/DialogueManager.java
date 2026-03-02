package presentation.uicomponents;

import java.util.Optional;

import business.IAudioSamplePlayer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import presentation.GUIHelper;
import presentation.TextHelper;

public class DialogueManager {

    GUIHelper guiHelper;
    public DialogueManager(GUIHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    public void showInfoDialogue(String titel, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(guiHelper.getIcon());
        alert.getDialogPane().getStylesheets().add(getClass().getResource(TextHelper.CSS_PFAD).toExternalForm());
        alert.getDialogPane().getStyleClass().add(TextHelper.STYLECLASS_CUSTOM_DIALOGUE);
        alert.getDialogPane().getStyleClass().add(TextHelper.STYLECLASS_CUSTOM_ALERT);
        alert.showAndWait();
    }

    public boolean showConfirmationDialogue(String titel, String header, String message, String confirmButtonText, String denyButtonText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titel);
        alert.setHeaderText(header);
        alert.setContentText(message);

        ButtonType btnConfirm = new ButtonType(confirmButtonText);
        ButtonType btnDeny = new ButtonType(denyButtonText);

        alert.getButtonTypes().setAll(btnConfirm, btnDeny);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(guiHelper.getIcon());
        alert.getDialogPane().getStylesheets().add(getClass().getResource(TextHelper.CSS_PFAD).toExternalForm());
        alert.getDialogPane().getStyleClass().add(TextHelper.STYLECLASS_CUSTOM_DIALOGUE);
        alert.getDialogPane().getStyleClass().add(TextHelper.STYLECLASS_CUSTOM_FRAGE);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnConfirm;
    }

    public String showUmbenennenDialogue(IAudioSamplePlayer selectedRecording, String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog(selectedRecording.getDisplayName());
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.getDialogPane().setMinWidth(300);
        dialog.getDialogPane().setMinHeight(170);
        
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(guiHelper.getIcon());

        dialog.getDialogPane().getStylesheets().add(getClass().getResource(TextHelper.CSS_PFAD).toExternalForm());
        dialog.getDialogPane().getStyleClass().add(TextHelper.STYLECLASS_CUSTOM_DIALOGUE);
        dialog.getDialogPane().getStyleClass().add(TextHelper.STYLECLASS_CUSTOM_RENAME);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            return result.get();
        }
        return null;
    }
}

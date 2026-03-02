package presentation.uicomponents;

import java.util.Optional;

import business.AudioSamplePlayer;
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
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/presentation/style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-dialog");
        alert.getDialogPane().getStyleClass().add("custom-alert");
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
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/presentation/style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-dialog");
        alert.getDialogPane().getStyleClass().add("custom-frage");
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnConfirm;
    }

    public String showUmbenennenDialogue(AudioSamplePlayer selectedRecording) {
        TextInputDialog dialog = new TextInputDialog(selectedRecording.getDisplayName());
        dialog.setTitle(TextHelper.RENAME_DIALOGUE_TITLE);
        dialog.setHeaderText(TextHelper.RENAME_DIALOGUE_HEADER);
        dialog.setContentText(TextHelper.RENAME_DIALOGUE_CONTENT);
        dialog.getDialogPane().setMinWidth(300);
        dialog.getDialogPane().setMinHeight(170);
        
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(guiHelper.getIcon());

        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/presentation/style.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("custom-dialog");
        dialog.getDialogPane().getStyleClass().add("custom-rename");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            return result.get();
        }
        return null;
    }
}

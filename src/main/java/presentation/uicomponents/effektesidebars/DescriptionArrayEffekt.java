package presentation.uicomponents.effektesidebars;

public class DescriptionArrayEffekt extends BaseSidebar {

    public DescriptionArrayEffekt() {
        String description = "Array Effekte";
        String descriptionText = 
                "Array Effekte sind Effekte, die auf eine Audiospur angewendet werden können, " + 
                "die dann gespeichert werden muss, um den Effekt zu hören.";
        createDescriptionBox(description, descriptionText);
    }
}
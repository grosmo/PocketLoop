package presentation.uicomponents.effektesidebars;

import presentation.TextHelper;

public class DescriptionArrayEffekt extends BaseSidebar {

    public DescriptionArrayEffekt() {
        createDescriptionBox(
            TextHelper.EFFEKT_ARRAY_TITLE, 
            TextHelper.EFFEKT_ARRAY_BESCHREIBUNG
        );
    }
}
package presentation.uicomponents.effektesidebars;

import presentation.TextHelper;
import presentation.uicomponents.uicontrols.AnimatedToggle;

public class DescriptionLiveEffekt extends BaseSidebar {
    AnimatedToggle toggleEffects;

    public DescriptionLiveEffekt() {
        createDescriptionBox(
            TextHelper.EFFEKT_LIVE_TITLE, 
            TextHelper.EFFEKT_LIVE_BESCHREIBUNG
        );
    }
}

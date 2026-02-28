package presentation.uicomponents.effektesidebars;

import presentation.uicomponents.uicontrols.AnimatedToggle;

public class DescriptionLiveEffekt extends BaseSidebar {
    AnimatedToggle toggleEffects;

    public DescriptionLiveEffekt() {
        String description = "Live Effekte";
        String descriptionText = 
            "Live Effekte können auf eine Audiospur angewendet werden und sind sofort hörbar, " + 
            "ohne dass die spur gespeichert werden muss.";
            
        createDescriptionBox(description, descriptionText);
    }
}

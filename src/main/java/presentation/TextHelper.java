package presentation;

import javafx.scene.text.Font;

public class TextHelper{

    public static final Font FONT_TOOLTIP = new Font(12);
    public static final Font FONT_AUFNAHME_STAGE = new Font(20);
    
    public static final String APP_TITLE = "Pocket Loop";

    public static final String SAMPLES = "Samples";
    public static final String OSZILLOSKOP = "Oszilloskop";

    public static final String SWITCH_VIEW_NAME_SAMPLE = "SamplesView";
    public static final String SWITCH_VIEW_NAME_OSZILLOSKOPE = "OszilloskopView";
    public static final String SAMPLE_RATE = "Samplerate: ";
    public static final String SCALE = "Scale: ";

    public static final String TOOLTIP_START_RECORDING_TEXT = "Neue Aufnahme starten";
    public static final String TOOLTIP_RENAME_RECORDING_TEXT = "Aufnahme umbenennen";
    public static final String TOOLTIP_DELETE_RECORDING_TEXT = "Aufnahme löschen";
    public static final String TOOLTIP_SWITCH_VIEW_TEXT = "Ansicht wechseln";
    public static final String TOOLTIP_STOP_ALL = "Alle Samples stoppen";
    public static final String TOOLTIP_PLAY_ALL = "Alle ausgewählten Samples abspielen";
    public static final String TOOLTIP_SELECT_ALL = "Alle Samples auswählen";
    public static final String TOOLTIP_LISTCELL_PLAY = "Play/Stop";
    public static final String TOOLTIP_LISTCELL_SELECT = "Zum Loop hinzufügen";
    public static final String TOOLTIP_LISTCELL_VOLUME = "Lautstärke";

    public static final String POPUP_MEHRERE_AUFNAHMEN_ERSTELLT = "Aufnahme erstellt";
    public static final String POPUP_MEHRERE_AUFNAHMEN_UMBENANNT = "Aufnahme umbenannt";
    public static final String POPUP_MEHRERE_AUFNAHMEN_DELETED = "Alle ausgewählten Samples gelöscht";
    public static final String POPUP_AUFNAHME_DELETED = "Sample gelöscht";
    public static final String TOOLTIP_DESELECT_ALL = "Alle Samples abwählen";

    public static final String AUFNAHME_STAGE_TITEL = "Aufnahme";
    public static final String AUFNAHME_STAGE_COUNTDOWN_TEXT = "Aufnahme startet in: ";
    public static final String AUFNAHME_STAGE_GESTARTET_TEXT = "Aufnahme gestartet";

    public static final String DELETE_ALL_DIALOGUE_TITLE = "Löschen";
    public static final String DELETE_ALL_DIALOGUE_HEADER = "Alle oder Eins?";
    public static final String DELETE_ALL_DIALOGUE_MESSAGE = "Willst du alle ausgewählten Samples oder nur das aktuell aktive Sample (lila Balken links) löschen?";
    public static final String DELETE_ALL_DIALOGUE_CONFIRM_BUTTON = "Alle";
    public static final String DELETE_ALL_DIALOGUE_DENY_BUTTON = "Aktuelles";

    public static final String RENAME_DIALOGUE_TITLE = "Aufnahme umbenennen";
    public static final String RENAME_DIALOGUE_HEADER = "Neuen Namen eingeben:";
    public static final String RENAME_DIALOGUE_CONTENT = "Name:";

    public static final String NO_RECORDING_TEXT = "Keine Samples vorhanden, bitte mache eine Aufnahme.";
    public static final String NO_RECORDING_SELECTED_TITLE = "Keine Aufnahme ausgewählt";
    public static final String NO_RECORDING_SELECTED_MESSAGE = "Bitte mache erst eine Aufnahme und/oder wähle eine aus um einen Effekt benutzen zu können.";

    public static final String RESTRICTED_EFFECT_TITLE = "Effekt kann nicht angehängt werden";
    public static final String RESTRICTED_EFFECT_MESSAGE = "Live Effekte können nur verwendet werden, wenn keine Arrayeffekte aktiviert sind.";

    public static final String NEW_ARRAYEFFECT_SAVE_TITLE = "Anwenden";
    public static final String NEW_ARRAYEFFECT_SAVE_HEADER = "Behalten oder Überschreiben?";
    public static final String NEW_ARRAYEFFECT_SAVE_MESSAGE = "Willst du die ursprüngliche Aufnahme behalten oder überschreiben?";
    public static final String NEW_ARRAYEFFECT_SAVE_CONFIRM = "Behalten";
    public static final String NEW_ARRAYEFFECT_SAVE_DENY = "Überschreiben";

    public static final String EFFEKT_GESPEICHERT_MESSAGE = "Effekt wurde auf das Sample angewendet.";

}
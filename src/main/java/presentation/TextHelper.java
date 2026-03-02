package presentation;

import javafx.scene.text.Font;

public class TextHelper{

    public static final Font FONT_TOOLTIP = new Font(12);
    public static final Font FONT_AUFNAHME_STAGE = new Font(20);

    public static final Font FONT_12 = new Font(12);
    public static final Font FONT_13 = new Font(13);
    public static final Font FONT_15 = new Font(15);
    public static final Font FONT_35 = new Font(35);
    public static final Font FONT_40 = new Font(40);
    public static final Font FONT_50 = new Font(50);

    public static final String COLOR_PRIMARY_GREEN = "#00b4a0";

    public static final String APP_TITLE = "Pocket Loop";
    public static final String SAMPLES = "Samples";
    public static final String OSZILLOSKOP = "Oszilloskop";

    public static final String STYLECLASS_CUSTOM_DIALOGUE = "custom-dialog";
    public static final String STYLECLASS_CUSTOM_ALERT = "custom-alert";
    public static final String STYLECLASS_CUSTOM_FRAGE = "custom-frage";
    public static final String STYLECLASS_CUSTOM_RENAME = "custom-rename";
    public static final String STYLECLASS_CHECK_BOX = "check-box";
    public static final String STYLECLASS_PLAY_DOT = "play-dot-checkbox";
    public static final String STYLECLASS_PLAYING = "playing";
    public static final String STYLECLASS_WAVEFORM_CANVAS = "waveform-canvas";
    public static final String STYLECLASS_EFFEKTE_SIDEBAR = "effekte-sidebar";
    public static final String STYLECLASS_BTN_SAFE = "btn-safe-effect";
    public static final String STYLECLASS_ANIMATED_TOGGLE = "animated-toggle";
    public static final String STYLECLASS_ROTARY_KNOB = "rotary-knob";
    public static final String STYLECLASS_BTN_STOP_ALL = "btn-stop-all";
    public static final String STYLECLASS_BTN_PLAY_ALL = "btn-play-all";
    public static final String STYLECLASS_BTN_SELECT_ALL = "btn-select-all";
    public static final String STYLECLASS_BTN_DESELECT_ALL = "btn-deselect-all";
    public static final String STYLECLASS_OSCILLOSCOPE_CANVAS = "oscilloscope-canvas";
    public static final String STYLECLASS_BTN_SWITCH_VIEW_BASE = "btn-switch-view-base";
    public static final String STYLECLASS_BTN_SWITCH_VIEW = "btn-switch-view";
    public static final String STYLECLASS_BTN_SWITCH_VIEW_CHECKED = "btn-switch-view-checked";
    public static final String STYLECLASS_SLIDE_OSCILLOSCOPE_SCALE = "slider-oscilloscope-scale";
    public static final String STYLECLASS_LIST_VIEW_BOX = "list-view-box";
    public static final String STYLECLASS_NO_SAMPLES_LABEL = "no-samples-label";
    public static final String STYLECLASS_BUTTON_BOX = "button-box";
    public static final String STYLECLASS_BUTTON = "button";
    public static final String STYLECLASS_TEXTFILL_BLACK = "-fx-text-fill: black;";

    public static final String POPUP_MSG_ID = "popup-message";

    public static final String FORMAT_DEZIMAL = "%.2f";
    public static final String CSS_PFAD = "/presentation/style.css";
    public static final String CSS_SHEET ="style.css"; 

    public static final String SWITCH_VIEW_NAME_SAMPLE = "SamplesView";
    public static final String SWITCH_VIEW_NAME_OSZILLOSKOPE = "OszilloskopView";
    public static final String SAMPLE_RATE = "Samplerate";
    public static final String SCALE = "Scale: ";
    public static final String SAFE = "Speichern";

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

    public static final String EFFEKT_LIVE_TITLE = "Live Effekte";
    public static final String EFFEKT_LIVE_BESCHREIBUNG = 
        "Live Effekte können auf eine Audiospur angewendet werden und sind sofort hörbar, " + 
        "ohne dass die spur gespeichert werden muss.";
    public static final String EFFEKT_ARRAY_TITLE = "Array Effekte";
    public static final String EFFEKT_ARRAY_BESCHREIBUNG =
        "Array Effekte sind Effekte, die auf eine Audiospur angewendet werden können, " + 
        "die dann gespeichert werden muss, um den Effekt zu hören.";

    public static final String EFFEKT_FLANGER_TITLE = "Flanger";
    public static final String EFFEKT_DELAY_TITLE = "Delay";
    public static final String EFFEKT_PITCH_SHIFT_TITLE = "Pitch";
    public static final String EFFEKT_BITCRUSH_TITLE = "Bitcrush";
    public static final String EFFEKT_REVERSE_TITLE = "Reverse";
    
    public static final String EFFEKT_RATE_TITLE ="Rate";
    public static final String EFFEKT_DEPTH_TITLE = "Tiefe";
    public static final String EFFEKT_NOTE = "Halbtöne";
    public static final String EFFEKT_TIME = "Zeit (ms)";
    public static final String EFFEKT_FEEDBACK = "Feedback";
    public static final String EFFEKT_DEPTH = "Tiefe";
    
    public static final String EFFEKT_REVERSE_INFO = "Spielt das Audio\nrückwärts ab.";
    public static final String EFFEKT_GESPEICHERT_MESSAGE = "Effekt wurde auf das Sample angewendet.";

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

}
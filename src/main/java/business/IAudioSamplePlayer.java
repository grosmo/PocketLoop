package business;

import javafx.beans.property.SimpleBooleanProperty;

public interface IAudioSamplePlayer {
    
    String getDisplayName();

    String getFilePath();

    boolean isResampled();

    SimpleBooleanProperty playingProperty();

    boolean isPlaying();

    void setPlaying(Boolean isPlaying);

    boolean isLoopSelected();

    void setLoopSelected(boolean loopSelected);

    float getVolume();

    void setVolume(float volume);

    double getStartMilliseconds();

    void setStartMilliseconds(double ms);

    double getEndMilliseconds();

    void setEndMilliseconds(double ms);

    double getTotalDurationMilliseconds();
}

package business;

import java.util.ArrayList;

import business.effekteservices.EffectType;
import ddf.minim.AudioOutput;
import ddf.minim.UGen;
import ddf.minim.ugens.Gain;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;

public interface IServiceHelper {

    public int getSAMPLE_RATE();
    
    SimpleMinim getMasterMinim();
    
    AudioOutput getMasterOutput();
    
    Gain getMasterGain();

    ArrayList<AudioSamplePlayer> getSamplePlayers();

    AudioSamplePlayer getAktuellesSample();

    void setAktuellesSample(AudioSamplePlayer sample);

    ObjectProperty<AudioSamplePlayer> aktuellesSampleProperty();

    boolean isRecording();

    BooleanProperty recordingProperty();

    void prepareRecording();

    void startRecording();

    void stopRecording();

    void deleteRecording(AudioSamplePlayer selectedSample);

    int getSelectedCount();

    void playAll();

    void stopAllPlays();

    void setSelectedRecordingDisplayName(AudioSamplePlayer selectedSample, String newName);

    boolean isBitcrusherEnabled();

    void setBitcrusherEnabled(boolean value);

    BooleanProperty bitcrusherEnabledProperty();

    int getBitcrushDepth();

    void setBitcrushDepth(int depth);

    int getBitcrushSampleRateDivider();

    void setBitcrushSampleRateDivider(int divider);

    boolean isDelayEnabled();

    void setDelayEnabled(boolean value);

    BooleanProperty delayEnabledProperty();

    float getDelayTime();

    void setDelayTime(float time);

    float getDelayFeedback();

    void setDelayFeedback(float feedback);

    UGen getDelayEffect();

    boolean isFlangerEnabled();

    void setFlangerEnabled(boolean value);

    BooleanProperty flangerEnabledProperty();

    float getFlangerRate();

    void setFlangerRate(float rate);

    float getFlangerDepth();

    void setFlangerDepth(float depth);

    float getFlangerDelay();

    void setFlangerDelay(float delay);

    UGen getFlangerEffect();

    boolean isPitchShiftEnabled();

    void setPitchShiftEnabled(boolean value);

    BooleanProperty pitchShiftEnabledProperty();

    float getPitchShiftSemitones();

    void setPitchShiftSemitones(float semitones);

    boolean isReverseEnabled();

    void setReverseEnabled(boolean value);

    BooleanProperty reverseEnabledProperty();

    void applyEffect(EffectType effectType, boolean enabled);

}
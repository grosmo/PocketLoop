package business;

import java.util.ArrayList;

import business.effekteservices.EffectType;
import ddf.minim.AudioOutput;
import ddf.minim.UGen;
import ddf.minim.ugens.Gain;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ServiceHelper implements IServiceHelper {

    RecorderService recorderService;
    Thread recorderServiceThread;
    protected final int SAMPLE_RATE = 44100;
    
    private SimpleMinim masterMinim;
    private AudioOutput masterOutput;
    private Gain masterGain;
    
    @Override
    public int getSAMPLE_RATE(){
        return SAMPLE_RATE;
    }

    private ArrayList<AudioSamplePlayer> samplePlayers = new ArrayList<>();
    @Override
    public ArrayList<AudioSamplePlayer> getSamplePlayers(){
        return samplePlayers;
    }

    public ServiceHelper() {
        recorderService = new RecorderService(this);
        samplePlayers = new ArrayList<>();
        
        initializeMasterOutput();
        
		recorderServiceThread = new Thread(recorderService);
		recorderServiceThread.setDaemon(true);
		recorderServiceThread.start();
    }
    
    private void initializeMasterOutput() {
        masterMinim = new SimpleMinim();
        masterOutput = masterMinim.getLineOut();
        masterGain = new Gain(0.0f);
        masterGain.patch(masterOutput);
    }
    
    @Override
    public SimpleMinim getMasterMinim() {
        return masterMinim;
    }
    
    @Override
    public AudioOutput getMasterOutput() {
        return masterOutput;
    }
    
    @Override
    public Gain getMasterGain() {
        return masterGain;
    }
    
    private final ObjectProperty<AudioSamplePlayer> aktuellesSample = new SimpleObjectProperty<>();
    @Override
    public AudioSamplePlayer getAktuellesSample(){
        return aktuellesSample.get();
    }
    @Override
    public void setAktuellesSample(AudioSamplePlayer sample){
        this.aktuellesSample.set(sample);
    }
    @Override
    public ObjectProperty<AudioSamplePlayer> aktuellesSampleProperty(){
        return aktuellesSample;
    }
    
    private final BooleanProperty recording = new SimpleBooleanProperty(false);
    @Override
    public boolean isRecording() {
        return recording.get();
    }
    protected void setRecording(boolean value) {
        recording.set(value);
    }
    @Override
    public BooleanProperty recordingProperty() {
        return recording;
    }

    @Override
    public void prepareRecording(){
        recorderService.prepareRecording();
    }

    @Override
    public void startRecording(){
        recording.set(true);
        recorderService.startRecording();
    }

    @Override
    public void stopRecording(){
        recording.set(false);
        recorderService.stopRecording();
    }
    @Override
    public void deleteRecording(AudioSamplePlayer selectedSample) {
        selectedSample.setPlaying(false);
        samplePlayers.remove(selectedSample);
    }

    @Override
    public void stopAllPlays() {
        samplePlayers.forEach(sample -> sample.setPlaying(false));
    }
    
    @Override
    public void playAll() {
        stopAllPlays();
        samplePlayers.forEach(sample -> {
            if(sample.isLoopSelected()) 
                sample.setPlaying(true);
        });
    }

    @Override
    public void setSelectedRecordingDisplayName(AudioSamplePlayer selectedSample, String newName) {
        selectedSample.getSampleModel().setDisplayName(newName);
    }

    @Override
    public boolean isBitcrusherEnabled() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.isBitcrusherEnabled();
        }
        return false;
    }
    @Override
    public void setBitcrusherEnabled(boolean value) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.setBitcrusherEnabled(value);
        }
    }


    @Override
    public BooleanProperty bitcrusherEnabledProperty() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.bitcrusherEnabledProperty();
        }
        return null;
    }

    @Override
    public boolean isDelayEnabled() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.isDelayEnabled();
        }
        return false;
    }
    @Override
    public void setDelayEnabled(boolean value) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.setDelayEnabled(value);
        }
    }
    @Override
    public BooleanProperty delayEnabledProperty() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.delayEnabledProperty();
        }
        return null;
    }

    @Override
    public boolean isFlangerEnabled() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.isFlangerEnabled();
        }
        return false;
    }
    @Override
    public void setFlangerEnabled(boolean value) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.setFlangerEnabled(value);
        }
    }
    @Override
    public BooleanProperty flangerEnabledProperty() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.flangerEnabledProperty();
        }
        return null;
    }

    @Override
    public boolean isPitchShiftEnabled() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.isPitchShiftEnabled();
        }
        return false;
    }
    @Override
    public void setPitchShiftEnabled(boolean value) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.setPitchShiftEnabled(value);
        }
    }
    @Override
    public BooleanProperty pitchShiftEnabledProperty() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.pitchShiftEnabledProperty();
        }
        return null;
    }

    @Override
    public boolean isReverseEnabled() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.isReverseEnabled();
        }
        return false;
    }
    @Override
    public void setReverseEnabled(boolean value) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.setReverseEnabled(value);
        }
    }
    @Override
    public BooleanProperty reverseEnabledProperty() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.reverseEnabledProperty();
        }
        return null;
    }

    @Override
    public int getBitcrushDepth() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getBitcrusherService().getBitDepth();
        }
        return 0;
    }

    @Override
    public void setBitcrushDepth(int depth) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getBitcrusherService().setBitDepth(depth);
        }
    }

    @Override
    public int getBitcrushSampleRateDivider() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getBitcrusherService().getBitcrushSampleRateDivider();
        }
        return 0;
    }

    @Override
    public void setBitcrushSampleRateDivider(int divider) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getBitcrusherService().setBitcrushSampleRateDivider(divider);
        }
    }

    @Override
    public float getDelayTime() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getDelayService().getDelayTime();
        }
        return 0.0f;
    }

    @Override
    public void setDelayTime(float time) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getDelayService().setDelayTime(time);
        }
    }

    @Override
    public float getDelayFeedback() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getDelayService().getFeedback();
        }
        return 0.0f;
    }

    @Override
    public void setDelayFeedback(float feedback) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getDelayService().setFeedback(feedback);
        }
    }

    @Override
    public UGen getDelayEffect() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getDelayService().getEffect();
        }
        return null;
    }

    @Override
    public float getFlangerRate() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getFlangerService().getFlangerRate();
        }
        return 0.0f;
    }

    @Override
    public void setFlangerRate(float rate) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getFlangerService().setFlangerRate(rate);
        }
    }

    @Override
    public float getFlangerDepth() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getFlangerService().getFlangerDepth();
        }
        return 0.0f;
    }

    @Override
    public void setFlangerDepth(float depth) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getFlangerService().setFlangerDepth(depth);
        }
    }

    @Override
    public float getFlangerDelay() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getFlangerService().getFlangerDelay();
        }
        return 0.0f;
    }

    @Override
    public void setFlangerDelay(float delay) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getFlangerService().setFlangerDelay(delay);
        }
    }

    @Override
    public UGen getFlangerEffect() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getFlangerService().getEffect();
        }
        return null;
    }

    @Override
    public float getPitchShiftSemitones() {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            return sample.effectManager.getPitchShiftService().getPitchShift();
        }
        return 0.0f;
    }

    @Override
    public void setPitchShiftSemitones(float semitones) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.effectManager.getPitchShiftService().setPitchShift(semitones);
        }
    }

    @Override
    public void applyEffect(EffectType effectType, boolean enabled) {
        AudioSamplePlayer sample = aktuellesSample.get();
        if(sample != null) {
            sample.applyEffect(effectType, enabled);
        }
    }

}
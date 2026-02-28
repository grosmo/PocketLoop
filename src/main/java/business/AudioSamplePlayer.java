package business;

import business.effekteservices.EffectType;
import ddf.minim.AudioOutput;
import ddf.minim.ugens.FilePlayer;
import ddf.minim.ugens.Gain;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleBooleanProperty;

public class AudioSamplePlayer {
    ServiceHelper serviceHelper;
    AudioOutput output;
    FilePlayer filePlayer;
    Thread playbackMinimThread;
    Thread loopThread;
    EffectManager effectManager;
    protected EffectManager getEffectManager() {
        return effectManager;
    }

    SampleModel sampleModel;
    protected SampleModel getSampleModel(){
        return sampleModel;
    }
    public String getDisplayName(){
        return sampleModel.getDisplayName();
    }
    public String getFilePath(){
        return sampleModel.getFilePath();
    }
    public boolean isResampled(){
        return sampleModel.isResampled();
    }
    
    private SimpleBooleanProperty playingProperty = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty playingProperty() {
        return playingProperty;
    }
    public boolean isPlaying(){
        return playingProperty.get();
    }
    public void setPlaying(Boolean isPlaying){
        playingProperty.set(isPlaying);
        if(isPlaying){
            play();
        }
        else
            stop();
    }

    public boolean isLoopSelected() {
        return sampleModel.isLoopSelected();
    }
    public void setLoopSelected(boolean loopSelected) {
        sampleModel.setLoopSelected(loopSelected);
    }

    Gain gain;
    float volume = 0.0f;
    public float getVolume() {
        return volume;
    }
    public void setVolume(float volume) {
        this.volume = volume;
        if (gain != null) {
            gain.setValue(volume);
        }
    }

    int startFrame = 0;
    // -1 ist bis zum Ende
    int endFrame = -1; 

    public double getStartMilliseconds() {
        return (startFrame / (double)serviceHelper.SAMPLE_RATE) * 1000;
    }
    public void setStartMilliseconds(double ms) {
        this.startFrame = (int)(ms / 1000.0 * serviceHelper.SAMPLE_RATE);
    }

    public void setEndMilliseconds(double ms) {
        this.endFrame = (int)(ms / 1000.0 * serviceHelper.SAMPLE_RATE);
    }
    public double getEndMilliseconds() {
        if (endFrame <= 0) 
            return getTotalDurationMilliseconds();
        return (endFrame / (double)serviceHelper.SAMPLE_RATE) * 1000;
    }

    protected AudioSamplePlayer(ServiceHelper serviceHelper, SampleModel sampleModel){
        this.effectManager = new EffectManager(serviceHelper);
        this.serviceHelper = serviceHelper;
        this.sampleModel = sampleModel;
    }

    public double getTotalDurationMilliseconds() {
        try {
            SimpleMinim tempMinim = new SimpleMinim();
            ddf.minim.AudioSample sample = tempMinim.loadSample(sampleModel.getFilePath());
            if (sample != null) {
                int totalFrames = sample.length();
                double duration = (totalFrames / (double)serviceHelper.SAMPLE_RATE) * 1000;
                sample.close();
                tempMinim.stop();
                return duration;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 4000;
    }

    protected synchronized void play(){
        // Wenn bereits etwas läuft, erst stoppen
        if (filePlayer != null || loopThread != null || playbackMinimThread != null) {
            stop();
        }
        
        playbackMinimThread = new Thread(() -> {
            output = serviceHelper.getMasterOutput();            
            gain = new Gain(volume);            
            filePlayer = new FilePlayer(serviceHelper.getMasterMinim().loadFileStream(sampleModel.getFilePath()));
            
            if (gain != null && output != null) {
                filePlayer.patch(gain);
                gain.patch(output);
            }
            
            effectManager.rebuildEffectChain(filePlayer, gain);            
            filePlayer.cue(startFrame);
            filePlayer.play();
            
            loopThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        FilePlayer fp = filePlayer;
                        if (fp == null)
                            break;
                        
                        int currentPosition = fp.position();
                        int effectiveEndFrame = (endFrame > 0 && endFrame > startFrame) ? endFrame : fp.length();
                        
                        if (currentPosition >= fp.length() - 1 || currentPosition >= effectiveEndFrame || !fp.isPlaying()) {
                            fp.pause();
                            Thread.sleep(10);
                            fp.cue(startFrame);
                            fp.play();
                        }
                        
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    // Ignoriere Fehler wenn Stream bereits geschlossen
                }
            });
            loopThread.setDaemon(true);
            loopThread.start();
        });
        playbackMinimThread.setDaemon(true);
        playbackMinimThread.start();
    }

    protected synchronized void stop(){
        // Zuerst loopThread stoppen, um Race Conditions zu vermeiden
        if(loopThread != null && loopThread.isAlive()){
            loopThread.interrupt();
            try {
                loopThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        loopThread = null;
        
        // Dann filePlayer stoppen
        if(filePlayer != null && filePlayer.isPlaying())
            filePlayer.pause();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        if(filePlayer != null && gain != null){
            effectManager.disconnectAllEffects(filePlayer, gain);
            filePlayer.unpatch(gain);
        }

        if(filePlayer != null){
            filePlayer.close();
            filePlayer = null;
        }
        
        if(gain != null && output != null)
            gain.unpatch(output);

        if(gain != null)
            gain = null;

        output = null;
        
        if(playbackMinimThread != null && playbackMinimThread.isAlive()){
            playbackMinimThread.interrupt();
            playbackMinimThread = null;
        }
    }

    protected void applyEffect(EffectType effectsToApply, boolean enable){ 
        effectManager.applyEffects(effectsToApply, serviceHelper, this, filePlayer, gain, enable);
    }
}

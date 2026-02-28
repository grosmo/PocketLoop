package business;

import business.effekteservices.BitcrusherService;
import business.effekteservices.DelayService;
import business.effekteservices.EffectType;
import business.effekteservices.FlangerService;
import business.effekteservices.PitchShiftService;
import business.effekteservices.ReverseService;
import ddf.minim.UGen;
import ddf.minim.ugens.FilePlayer;
import ddf.minim.ugens.Gain;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class EffectManager {

    ServiceHelper serviceHelper;

    // Flanger-Effekt
    FlangerService flangerService;
    public FlangerService getFlangerService(){
        return flangerService;
    }
    
    private final BooleanProperty flangerEnabled = new SimpleBooleanProperty(false);
    protected boolean isFlangerEnabled(){
        return flangerService.isEnabled();
    }
    protected void setFlangerEnabled(boolean enabled){
        flangerService.setEnabled(enabled);
        flangerEnabled.set(enabled);
    }
    protected BooleanProperty flangerEnabledProperty(){
        return flangerEnabled;
    }

    // Delay-Effekt
    DelayService delayService;
    public DelayService getDelayService(){
        return delayService;
    }

    private final BooleanProperty delayEnabled = new SimpleBooleanProperty(false);
    protected boolean isDelayEnabled(){
        return delayService.isEnabled();
    }
    protected void setDelayEnabled(boolean enabled){
        delayService.setEnabled(enabled);
        delayEnabled.set(enabled);
    }
    protected BooleanProperty delayEnabledProperty(){
        return delayEnabled;
    }

    // Reverse-Effekt
    ReverseService reverseService;
    public ReverseService getReverseService(){
        return reverseService;
    }

    private final BooleanProperty reverseEnabled = new SimpleBooleanProperty(false);    
    protected boolean isReverseEnabled(){
        return reverseService.isEnabled();
    }
    protected void setReverseEnabled(boolean enabled){
        if(enabled){
            setPitchShiftEnabled(false);
            setBitcrusherEnabled(false);
        }
        reverseService.setEnabled(enabled);
        reverseEnabled.set(enabled);
    }
    protected BooleanProperty reverseEnabledProperty(){
        return reverseEnabled;
    }

    // Pitch-Shift-Effekt
    PitchShiftService pitchShiftService;
    public PitchShiftService getPitchShiftService(){
        return pitchShiftService;
    }

    private final BooleanProperty pitchShiftEnabled = new SimpleBooleanProperty(false);
    protected boolean isPitchShiftEnabled(){
        return pitchShiftService.isEnabled();
    }
    protected void setPitchShiftEnabled(boolean enabled){
        if(enabled){
            setReverseEnabled(false);
            setBitcrusherEnabled(false);
        }
        pitchShiftService.setEnabled(enabled);
        pitchShiftEnabled.set(enabled);
    }
    protected BooleanProperty pitchShiftEnabledProperty(){
        return pitchShiftEnabled;
    }

    // Bitcrusher-Effekt
    BitcrusherService bitcrusherService;
    public BitcrusherService getBitcrusherService(){
        return bitcrusherService;
    }

    private final BooleanProperty bitcrusherEnabled = new SimpleBooleanProperty(false);
    protected boolean isBitcrusherEnabled(){
        return bitcrusherService.isEnabled();
    }
    protected void setBitcrusherEnabled(boolean enabled){
        if(enabled){
            setReverseEnabled(false);
            setPitchShiftEnabled(false);
        }
        bitcrusherService.setEnabled(enabled);
        bitcrusherEnabled.set(enabled);
    }
    protected BooleanProperty bitcrusherEnabledProperty(){
        return bitcrusherEnabled;
    }
    
    protected EffectManager(ServiceHelper serviceHelper){
        this.serviceHelper = serviceHelper;
        flangerService = new FlangerService();
        delayService = new DelayService();
        reverseService = new ReverseService();
        pitchShiftService = new PitchShiftService();
        bitcrusherService = new BitcrusherService();
    }

    protected void applyEffects(
        EffectType effectsToApply, 
        ServiceHelper serviceHelper, 
        AudioSamplePlayer samplePlayer, 
        FilePlayer filePlayer, 
        Gain gain,
        boolean enable){
        switch(effectsToApply){
            // Live-Effekte
            case FLANGER:
                addFlangerToOutput(enable, filePlayer, gain);
                break;
            case DELAY:
                addDelayToOutput(enable, filePlayer, gain);
                break;
            
            // Array-Effekte
            case REVERSE:
                SoundBuilderService.applyArrayEffect(EffectType.REVERSE, this, samplePlayer, serviceHelper, enable);
                samplePlayer.getSampleModel().setResampled(true);
                break;
            case PITCH_SHIFT:
                SoundBuilderService.applyArrayEffect(EffectType.PITCH_SHIFT, this, samplePlayer, serviceHelper, enable);
                samplePlayer.getSampleModel().setResampled(true);
                break;
            case BITCRUSHER:
                SoundBuilderService.applyArrayEffect(EffectType.BITCRUSHER, this, samplePlayer, serviceHelper, enable);
                samplePlayer.getSampleModel().setResampled(true);
                break;
            default:
                break;
        }
    }
    
    protected void addFlangerToOutput(boolean enabled, FilePlayer filePlayer, Gain gain){
        flangerService.setEnabled(enabled);
        rebuildEffectChain(filePlayer, gain);
    }

    protected void addDelayToOutput(boolean enabled, FilePlayer filePlayer, Gain gain){
        delayService.setEnabled(enabled);
        rebuildEffectChain(filePlayer, gain);
    }

    protected void rebuildEffectChain(FilePlayer filePlayer, Gain gain){
        if(filePlayer == null || gain == null)
            return;
        
        filePlayer.unpatch(flangerService.getEffect());
        filePlayer.unpatch(delayService.getEffect());
        filePlayer.unpatch(gain);
        
        if(flangerService.getEffect() != null){
            flangerService.getEffect().unpatch(delayService.getEffect());
            flangerService.getEffect().unpatch(gain);
        }
        
        if(delayService.getEffect() != null)
            delayService.getEffect().unpatch(gain);
        
        UGen currentSource = filePlayer;
        
        if(isFlangerEnabled() && getFlangerService().getEffect() != null){
            currentSource.patch(getFlangerService().getEffect());
            currentSource = getFlangerService().getEffect();
        }
        if(isDelayEnabled() && getDelayService().getEffect() != null){
            currentSource.patch(getDelayService().getEffect());
            currentSource = getDelayService().getEffect();
        }
        currentSource.patch(gain);
    }
    
    protected void disconnectAllEffects(FilePlayer filePlayer, Gain gain){
        if(filePlayer == null || gain == null)
            return;
        
        try {
            filePlayer.unpatch(flangerService.getEffect());
            filePlayer.unpatch(delayService.getEffect());
            
            if(flangerService.getEffect() != null){
                flangerService.getEffect().unpatch(delayService.getEffect());
                flangerService.getEffect().unpatch(gain);
            }
            
            if(delayService.getEffect() != null)
                delayService.getEffect().unpatch(gain);
        } catch (Exception e) {
            // Ignoriere Fehler beim Trennen
        }
    }
}
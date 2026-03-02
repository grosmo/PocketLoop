package business;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import business.effekteservices.EffectType;
import ddf.minim.AudioSample;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SoundBuilderService {

    private static final String EFFEKT_ERWEITERUNG_REVERSE = "_reverse";
    private static final String EFFEKT_ERWEITERUNG_PITCH = "_pitch";
    private static final String EFFEKT_ERWEITERUNG_BITCRUSH = "_bitcrush";
    
    private static final String ILLEGAL_ARGUMENT = "Left channel cannot be null or empty";
    private static final String FEHLER_EFFEKT_ANWENDEN = "FEHLER beim Anwenden des Effekts auf das Sample!";
    private static final String FEHLER_LADEN = "FEHLER beim Laden der Audiokanäle!";
    
    protected static void writeWav(float[] leftChannel, float[] rightChannel, float sampleRate, String filePath) throws IOException {
        if(leftChannel == null || leftChannel.length == 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
        
        boolean stereo = (rightChannel != null && rightChannel.length > 0);
        int channels = stereo ? 2 : 1;
        int sampleSizeInBits = 16;
        int frameSize = channels * (sampleSizeInBits / 8);
        int frameLength = leftChannel.length;
        
        AudioFormat audioFormat = new AudioFormat(
            sampleRate,
            sampleSizeInBits,
            channels,
            true,
            false
        );
        
        byte[] audioBytes = new byte[frameLength * frameSize];
        ByteBuffer buffer = ByteBuffer.wrap(audioBytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
        for(int i = 0; i < frameLength; i++) {
            short leftSample = floatToShort(leftChannel[i]);
            buffer.putShort(leftSample);
            
            if(stereo) {
                short rightSample = floatToShort(rightChannel[i]);
                buffer.putShort(rightSample);
            }
        }
        
        ByteArrayInputStream byteStream = new ByteArrayInputStream(audioBytes);
        AudioInputStream audioStream = new AudioInputStream(
            byteStream,
            audioFormat,
            frameLength
        );
        
        File outputFile = new File(filePath);
        AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, outputFile);
        
        audioStream.close();
    }

    protected static void applyArrayEffect(EffectType effectsToApply, EffectManager effectHelper, AudioSamplePlayer sampleModel, ServiceHelper serviceHelper, boolean enable) {

        if(effectsToApply == EffectType.REVERSE){
            effectHelper.setPitchShiftEnabled(false);
            effectHelper.setBitcrusherEnabled(false);
        } else if(effectsToApply == EffectType.PITCH_SHIFT){
            effectHelper.setReverseEnabled(false);
            effectHelper.setBitcrusherEnabled(false);
        } else if(effectsToApply == EffectType.BITCRUSHER){
            effectHelper.setReverseEnabled(false);
            effectHelper.setPitchShiftEnabled(false);
        }

        boolean success = applyArrayEffects(effectsToApply, effectHelper, sampleModel, serviceHelper, enable);

        if(!success){
            System.out.println(FEHLER_EFFEKT_ANWENDEN);
        }
    }

    private static boolean applyArrayEffects(EffectType effectsToApply, EffectManager effectHelper, AudioSamplePlayer samplePlayer, ServiceHelper serviceHelper, boolean enable) {
        try {
            SimpleMinim tempMinim = new SimpleMinim();
            AudioSample sample = tempMinim.loadSample(samplePlayer.getSampleModel().getFilePath());
            
            if(sample == null)
                return false;
            
            int channels = sample.getFormat().getChannels();
            float[] leftChannelData = sample.getChannel(AudioSample.LEFT);
            float[] rightChannelData = channels > 1 ? sample.getChannel(AudioSample.RIGHT) : null;
            
            if(leftChannelData == null) {
                System.out.println(FEHLER_LADEN);
                return false;
            }

            int length = leftChannelData.length;
            float[] leftChannel = new float[length];
            float[] rightChannel = rightChannelData != null ? new float[length] : null;
            
            for(int i = 0; i < length; i++) {
                leftChannel[i] = leftChannelData[i];
                if(rightChannel != null && rightChannelData != null) {
                    rightChannel[i] = rightChannelData[i];
                }
            }

            String originalPath = samplePlayer.getSampleModel().getFilePath();
            int lastDot = originalPath.lastIndexOf('.');
            String neuerPath = originalPath.substring(0, lastDot);

            if(effectsToApply == EffectType.REVERSE) {
                leftChannel = effectHelper.getReverseService().reverseAudio(leftChannel);
                if(rightChannel != null)
                    rightChannel = effectHelper.getReverseService().reverseAudio(rightChannel);
                neuerPath += EFFEKT_ERWEITERUNG_REVERSE;
            }
            
            if(effectsToApply == EffectType.PITCH_SHIFT) {
                leftChannel = effectHelper.getPitchShiftService().applyPitchShift(leftChannel);
                if(rightChannel != null)
                    rightChannel = effectHelper.getPitchShiftService().applyPitchShift(rightChannel);
                neuerPath += EFFEKT_ERWEITERUNG_PITCH;
            }
            
            if(effectsToApply == EffectType.BITCRUSHER) {
                leftChannel = effectHelper.getBitcrusherService().applyBitcrusher(leftChannel);
                if(rightChannel != null)
                    rightChannel = effectHelper.getBitcrusherService().applyBitcrusher(rightChannel);
                neuerPath += EFFEKT_ERWEITERUNG_BITCRUSH;
            }

            String newPath = neuerPath + originalPath.substring(lastDot);
            
            writeWav(leftChannel, rightChannel, serviceHelper.SAMPLE_RATE, newPath);
            sample.close();
            tempMinim.stop();

            if(!enable)
                samplePlayer.getSampleModel().setFilePath(newPath);
            else{
                AudioSamplePlayer newSample = new AudioSamplePlayer(serviceHelper, new SampleModel(newPath));
                int index = serviceHelper.getSamplePlayers().indexOf(samplePlayer);
                serviceHelper.getSamplePlayers().add(index + 1, newSample);
            }

            effectHelper.setReverseEnabled(false);
            effectHelper.setPitchShiftEnabled(false);
            effectHelper.setBitcrusherEnabled(false);
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static short floatToShort(float sample) {
        if(sample > 1.0f) sample = 1.0f;
        if(sample < -1.0f) sample = -1.0f;
        
        return (short)(sample * Short.MAX_VALUE);
    }
}

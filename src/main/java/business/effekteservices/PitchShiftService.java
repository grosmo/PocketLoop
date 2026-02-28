package business.effekteservices;

import ddf.minim.UGen;

public class PitchShiftService extends EffectServiceBase {

    private float pitchShift = 0.0f;
    public float getPitchShift() {
        return pitchShift;
    }
    public void setPitchShift(float semitones) {
        this.pitchShift = Math.max(-12.0f, Math.min(semitones, 12.0f));
    }

    @Override
    public UGen getEffect(){
        return null; 
    }

    public float[] applyPitchShift(float[] input) {
        if (pitchShift == 0.0f || input == null) {
            return input;
        }
        
        double pitchFactor = Math.pow(2.0, pitchShift / 12.0);        
        int newLength = (int)(input.length / pitchFactor);
        float[] output = new float[input.length];
        
        for (int i = 0; i < output.length; i++) {
            double sourcePos = i * pitchFactor;
            int index = (int)sourcePos;
            
            if (index >= newLength - 1) {
                output[i] = 0.0f;
            } else if (index >= input.length - 1) {
                output[i] = input[input.length - 1];
            } else {
                float fraction = (float)(sourcePos - index);
                output[i] = input[index] * (1 - fraction) + input[index + 1] * fraction;
            }
        }
        
        return output;
    }
}

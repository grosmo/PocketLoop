package business.effekteservices;

import ddf.minim.UGen;

public class ReverseService extends EffectServiceBase {
    @Override
    public UGen getEffect(){
        return null; 
    }
    
    public float[] reverseAudio(float[] input) {
        if (!isEnabled() || input == null) {
            return input;
        }
        
        float[] output = new float[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[input.length - 1 - i];
        }
        return output;
    }
}

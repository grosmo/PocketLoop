package business.effekteservices;

import ddf.minim.UGen;

public class BitcrusherService extends EffectServiceBase {
    private int bitDepth = 8;
    public int getBitDepth() {
        return bitDepth;
    }
    public void setBitDepth(int depth) {
        this.bitDepth = Math.max(1, Math.min(depth, 16));
    }
    
    private int sampleRateDivider = 1;
    public int getBitcrushSampleRateDivider() {
        return sampleRateDivider;
    }
    public void setBitcrushSampleRateDivider(int divider) {
        this.sampleRateDivider = Math.max(1, Math.min(divider, 50));
    }
    
    @Override
    public UGen getEffect(){
        return null; 
    }
    
    public float[] applyBitcrusher(float[] input) {
        if (input == null) {
            return input;
        }
        
        float[] output = new float[input.length];
        float lastSample = 0.0f;
        
        int levels = (int)Math.pow(2, bitDepth);
        float step = 2.0f / levels;
        
        for (int i = 0; i < input.length; i++) {
            if (i % sampleRateDivider == 0) {
                float sample = input[i];                
                int quantized = (int)((sample + 1.0f) / step);
                lastSample = (quantized * step) - 1.0f;                
                lastSample = Math.max(-1.0f, Math.min(lastSample, 1.0f));
            }            
            output[i] = input[i] + lastSample;
        }
        
        return output;
    }
}

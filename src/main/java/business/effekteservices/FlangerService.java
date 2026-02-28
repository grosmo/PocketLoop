package business.effekteservices;

import ddf.minim.UGen;
import ddf.minim.ugens.Flanger;

public class FlangerService extends EffectServiceBase {
    Flanger flanger;
    @Override
    public UGen getEffect(){
        return flanger;
    }
    
    float flangerRate = 0.5f;
    public float getFlangerRate(){
        return flangerRate;
    }
    public void setFlangerRate(float rate){
        this.flangerRate = rate;
        if(flanger != null){
            flanger.rate.setLastValue(rate);
        }
    }
    
    float flangerDepth = 0.5f;
    public float getFlangerDepth(){
        return flangerDepth;
    }
    public void setFlangerDepth(float depth){
        this.flangerDepth = depth;
        if(flanger != null){
            flanger.depth.setLastValue(depth);
        }
    }
    
    
    float flangerDelay = 5.0f;
    public float getFlangerDelay(){
        return flangerDelay;
    }
    public void setFlangerDelay(float delay){
        this.flangerDelay = delay;
        if(flanger != null){
            flanger.delay.setLastValue(delay);
        }
    }

    public FlangerService(){
        // flanger = new Flanger(5.0f, 0.5f, 5.0f, 0.5f, 0.5f, 0.5f);
        flanger = new Flanger(flangerDelay, flangerDepth, flangerRate, 0.5f, 0.5f, 0.5f);
    }

    
    

}

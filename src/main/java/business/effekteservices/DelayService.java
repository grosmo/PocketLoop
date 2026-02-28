package business.effekteservices;

import ddf.minim.UGen;
import ddf.minim.ugens.Delay;

public class DelayService extends EffectServiceBase {
    private static final float MAX_DELAY_TIME = 2.0f;
    private static final float MAX_FEEDBACK = 0.95f;

    private Delay delay;
    private float delayTime = 0.3f;
    private float feedback = 0.5f;

    public DelayService() {
        delay = new Delay(MAX_DELAY_TIME, 0.5f);
        delay.setDelTime(delayTime);
        delay.setDelAmp(feedback);
    }

    @Override
    public UGen getEffect() {
        return delay;
    }
    
    public float getDelayTime() {
        return delayTime;
    }
    
    public void setDelayTime(float time) {
        this.delayTime = Math.max(0.0f, Math.min(time, MAX_DELAY_TIME));
        if (delay != null)
            delay.setDelTime(this.delayTime);
    }
    
    public float getFeedback() {
        return feedback;
    }
    
    public void setFeedback(float feedback) {
        this.feedback = Math.max(0.0f, Math.min(feedback, MAX_FEEDBACK));
        if (delay != null)
            delay.setDelAmp(this.feedback);
    }
}

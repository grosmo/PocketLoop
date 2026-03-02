package business.effekteservices;

import ddf.minim.UGen;

public abstract class EffectServiceBase {
    private boolean isEnabled;
    public boolean isEnabled() {
        return isEnabled;
    }
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public abstract UGen getEffect();
}

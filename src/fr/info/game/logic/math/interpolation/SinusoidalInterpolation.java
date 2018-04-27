package fr.info.game.logic.math.interpolation;

import fr.info.game.logic.math.DynamicValueUpdater;

/**
 * Interpolation of the form amplitude * sin(omega * t + phi)
 * Since progress is included in [0 ; 1] we take t = 2*PI*progress to have a complete period
 */
public class SinusoidalInterpolation extends DynamicValueUpdater<Float> {

    private final float amplitude;
    /**
     * The frequency factor of the sinusoid
     */
    private final float omega;
    /**
     * The phase offset of the sinusoid
     */
    private final float phi;

    public SinusoidalInterpolation(int duration, float amplitude, float omega, float phi) {
        super(duration);
        this.amplitude = amplitude;
        this.omega = omega;
        this.phi = phi;
    }

    @Override
    protected Float getValue(float progress) {
        return (float) (this.amplitude * Math.sin(this.omega * Math.PI * 2 * progress + this.phi));
    }
}

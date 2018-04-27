package fr.info.game.logic.math.interpolation;

import fr.info.game.logic.math.DynamicValueUpdater;

public class QuadraticInterpolation extends DynamicValueUpdater<Float> {

    private final float startValue;
    private final float endValue;

    public QuadraticInterpolation(int duration, float startValue, float endValue) {
        super(duration);
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    protected Float getValue(float progress) {
        return this.startValue * (1 - progress * progress) + this.endValue * (progress * progress);
    }
}
